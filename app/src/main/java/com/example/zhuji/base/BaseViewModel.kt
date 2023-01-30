package com.example.zhuji.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import com.example.zhuji.utils.DialogUtil
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.catch

open class BaseViewModel : ViewModel(), LifecycleObserver {

    //ViewModel被清除,取消协程内的工作
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /**
     * 网络请求
     * 过滤请求结果，其他全抛异常
     * @param requestBlock 请求体
     * @param response 成功回调 响应
     * @param error 失败回调
     * @param loadMoreError 加载更多失败
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     * @param isLoadMore 是否加载更多
     * @param isStatueLayout 是否有加载替换的布局
     */
    fun <T> launchFlow(
        requestBlock: suspend CoroutineScope.() -> BaseResult<T>,
        response:(T?) -> Unit,
        error: (String) -> Unit = {},
        loadMoreError: () -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        isStatueLayout: Boolean = false,
        isLoadMore: Boolean = false
    ){
        //请求之前操作
        if (prepare(isShowDialog, isStatueLayout)) {
            if (isLoadMore) {
                loadMoreError()
            }
            end(isShowDialog, complete)
            return
        }
        viewModelScope.launch {
            flow {
                emit(requestBlock())
            }    //网络请求
                .flowOn(Dispatchers.IO)  //指定网络请求的线程
                .catch { t: Throwable ->   //异常捕获处理
                    Log.e("RequestTAG","catch:${t.message}")
                    catch(error, t, isLoadMore, loadMoreError, isStatueLayout)
                    end(isShowDialog, complete)
                }
                //数据请求返回处理  emit(block()) 返回的数据
                .collect {
                    handlerCode(it, isStatueLayout, response, error)
                    end(isShowDialog, complete)
                }
        }
    }

    /**
     * 请求之前操作
     * 判断是否有网络等操作
     */
    private fun prepare(
        isShowDialog: Boolean,
        isStatueLayout: Boolean
    ): Boolean {
        if (isShowDialog) {
            DialogUtil.showLoadingDialog("加载中")
        }
        //是否显示加载界面  准备
        if (isStatueLayout) {
//            viewStateEvent.statueShowLoading.call()
        }
        //网络监测
//        if (!NetWorkUtil.isNetworkConnected(MyApplication.getBaseContext())) {
////            viewStateEvent.msgEvent.postValue(ResultCodeEnum.NETWORK_ERROR.getMessage())
//            //关闭页面loading
//            if (isStatueLayout) {
////                viewStateEvent.statueError.call()
//            }
//            return true
//        }
        return false
    }

    /**
     * 响应处理
     */
    private fun <T> handlerCode(
        it: BaseResult<T>,
        isStatueLayout: Boolean,
        resp: (T?) -> Unit,
        error: (String) -> Unit
    ) {
        when (it.errorCode) {     //网络响应解析
            ResultCodeEnum.SUCCESS.getCode() -> {
                if (isStatueLayout) {
//                    viewStateEvent.statueSuccess.call()
                } //数据加载完成 界面切换
                resp(it.data)  //数据加载完成交由业务层处理
            }
            else -> {
                if (isStatueLayout) {
//                    viewStateEvent.statueError.call()
                }
                error(it.errorMsg ?: "")
//                viewStateEvent.msgEvent.postValue(it.msg)
            }

        }
    }

    /**
     * 异常处理
     */
    private fun catch(
        error: (String) -> Unit,
        t: Throwable,
        isLoadMore: Boolean,
        loadMoreError: () -> Unit,
        isStatueLayout: Boolean
    ) {
        error(t.message ?: "")   //有基类自行处理,业务层也可以自行处理
        if (isLoadMore) {   //isLoadMore 为true 表示数据已经加载第二页数据 则不需要显示statueError
            loadMoreError()  //交由业务层自行处理
        } else {
            if (isStatueLayout) {
//                viewStateEvent.statueError.call()   //显示数据加载失败界面
            }
//            viewStateEvent.msgEvent.postValue(t.message ?: "")
        }
    }

    /**
     * 最终执行
     *
     * 不管成功失败都会被执行
     *
     * 关闭弹窗
     */
    private fun end(isShowDialog: Boolean, complete: () -> Unit) {
        if (isShowDialog) {
            DialogUtil.closeLoadingDialog()
        }
        complete()
    }


    //页面事件
    inner class ViewStateEvent(){
        val showDialog by lazy { ViewModelEvent<String>() } // 显示加载框
        val dismissDialog by lazy { ViewModelEvent<Void>() } //关闭加载框
        val msgEvent by lazy { ViewModelEvent<String>() }    //发送消息
        val statueShowLoading by lazy { ViewModelEvent<Void>() } // 显示加载布局
        val statueSuccess by lazy { ViewModelEvent<Void>() }    //加载完成
        val statueError by lazy { ViewModelEvent<Void>() }     //加载错误
    }

}

/**
 * 事件分发
 */
class ViewModelEvent <T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }

}