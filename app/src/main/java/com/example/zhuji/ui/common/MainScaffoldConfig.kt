package com.example.zhuji.ui.common

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.zhuji.route.RouteKey
import com.example.zhuji.ui.page.CommunityPage
import com.example.zhuji.ui.page.HomePage
import com.example.zhuji.ui.page.JourneyPage
import com.example.zhuji.ui.page.MinePage
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun MainScaffoldConfig(){

//    val navHostController = rememberNavController()
    val navHostController = rememberAnimatedNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()

    var selectIndex by remember{ mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            when(currentDestination?.route){
                RouteKey.HOME.toString() -> BottomTabBar(navHostController = navHostController, selectIndex = selectIndex){
                    selectIndex = it
                }
                RouteKey.COMMUNITY.toString() -> BottomTabBar(navHostController = navHostController, selectIndex = selectIndex){
                    selectIndex = it
                }
                RouteKey.JOURNEY.toString() -> BottomTabBar(navHostController = navHostController, selectIndex = selectIndex){
                    selectIndex = it
                }
                RouteKey.MINE.toString() -> BottomTabBar(navHostController = navHostController, selectIndex = selectIndex){
                    selectIndex = it
                }
            }
        },
        content = {
            AnimatedNavHost(
                navController = navHostController,
                startDestination = RouteKey.HOME.toString(),
                modifier = Modifier.background(MaterialTheme.colors.background),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = {fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = {fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = {fullWidth -> -fullWidth },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = {fullWidth -> fullWidth },
                        animationSpec = tween(500)
                    )
                }
            ) {
                composable(route = RouteKey.HOME.toString()) {
                    HomePage(navHostController)
                }

                composable(route = RouteKey.COMMUNITY.toString()) {
                    CommunityPage(navHostController = navHostController)
                }

                composable(route = RouteKey.JOURNEY.toString()) {
                    JourneyPage(navHostController = navHostController)
                }

                composable(route = RouteKey.MINE.toString()) {
                    MinePage(navHostController = navHostController)
                }
            }

//                composable(route = RouteKey.LOGIN.toString()) {
//                    LoginPage(navHostController = navHostController)
//                }
//
//                composable(route = RouteKey.CHANGETHEMESTATE.toString()) {
//                    ChangeThemeState(navHostController = navHostController)
//                }
//
//                composable(route = RouteKey.DIALOG.toString()) {
//                    DialogPage(navHostController = navHostController)
//                }

                ///传递对象
//                  RouteNavigationUtil.navigationTo(
//                      navHostController,
//                      destinationName = RouteKey.DETAIL.toString(),
//                      args = MineDetailParamsEntity(
//                          imgUrl = currentBanner?.imagePath!!,
//                          title = currentBanner.title,
//                          detailPageState = detailPageState,
//                          cardSizeWidth = cardSize.width,
//                          cardSizeHeight = cardSize.height,
//                          fullSizeWidth = fullSize.width,
//                          fullSizeHeight= fullSize.height,
//                          cardOffsetWidth=cardOffset.x,
//                          cardOffsetHeight=cardOffset.y
//                      )
//                  )
//                  composable(route = RouteKey.DETAIL.toString()+"/{detailData}",
//                      arguments = listOf(navArgument("detailData"){type = NavType.StringType})
//                  ){
//                      val args = it.arguments?.getString("detailData")?.fromJson<MineDetailParamsEntity>()
//                      if (args != null) {
//                          MineDetailsPage(entity = args)
//                      }
//                  }
            //---------------------------------------------FEN---GE--XIAN-----^^____------_____------______----_____
//            NavHost( //替换成有动画的导航
//                modifier = Modifier.background(MaterialTheme.colors.background),
//                navController = navHostController,
//                startDestination = RouteKey.HOME.toString(),
//            ) {
//                composable(route = RouteKey.HOME.toString()) {
//                    HomePage(navHostController)
//                }
//
//                composable(route = RouteKey.PROJECT.toString()) {
//                    ProjectPage(navHostController = navHostController)
//                }
//
//                composable(route = RouteKey.NAVI.toString()) {
//                    NaviPage(navHostController = navHostController)
//                }
//
//                composable(route = RouteKey.MINE.toString()) {
//                    MinePage(navHostController = navHostController)
//                }
//
//                composable(route = RouteKey.LOGIN.toString()) {
//                    LoginPage(navHostController = navHostController)
//                }
//
//                composable(route = RouteKey.CHANGETHEMESTATE.toString()){
//                    ChangeThemeState(navHostController =navHostController)
//                }
//            }
        },
//        snackbarHost = {
//            SnackbarHost(
//                hostState = scaffoldState.snackbarHostState
//            ) { data ->
////                println("actionLabel = ${data.actionLabel}")
////                AppSnackBar(data = data)
//            }
//        }
    )
}