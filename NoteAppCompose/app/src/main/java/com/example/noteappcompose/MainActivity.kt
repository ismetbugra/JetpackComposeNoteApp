package com.example.noteappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.noteappcompose.data.viewmodels.NoteViewModel
import com.example.noteappcompose.ui.navigation.BottomNavItem
import com.example.noteappcompose.ui.navigation.Destinations
import com.example.noteappcompose.ui.navigation.NavGraph
import com.example.noteappcompose.ui.navigation.items
import com.example.noteappcompose.ui.screens.NoteListScreen
import com.example.noteappcompose.ui.theme.NoteAppComposeTheme
import com.example.noteappcompose.ui.theme.colorBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppComposeTheme {
                val scrollBehavior=TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                var navController= rememberNavController()
                var navList=items.list
                var bottomNavSelectedIndex by rememberSaveable {
                    mutableStateOf(0)
                }


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    /*topBar = {
                        MediumTopAppBar(title = {
                            Text(text = navList[bottomNavSelectedIndex].title)
                        })
                    },*/
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.White,
                        ) {
                            navList.forEachIndexed { index, bottomNavItem ->
                                NavigationBarItem(
                                    selected = index == bottomNavSelectedIndex,
                                    onClick = {
                                        bottomNavSelectedIndex = index
                                        navController.navigate(bottomNavItem.route)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == bottomNavSelectedIndex) {
                                                bottomNavItem.selectedIcon
                                            } else {
                                                bottomNavItem.unselectedIcon
                                            }, contentDescription = ""
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(indicatorColor = colorBlue)
                                )
                            }
                        }
                    },
                    /*floatingActionButton = {
                        if(bottomNavSelectedIndex==0){

                            FloatingActionButton(onClick = {
                                navController.navigate(Destinations.AddScreen)
                            }) {

                                Icon(imageVector = Icons.Default.Add, contentDescription = "")
                            }
                        }

                    },
                    floatingActionButtonPosition = FabPosition.End*/
                ) { innerPadding ->

                   NavGraph(navController =navController , paddingValues =innerPadding )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteAppComposeTheme {

    }
}