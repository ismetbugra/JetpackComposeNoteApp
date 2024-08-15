package com.example.noteappcompose.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.NavController
import com.example.noteappcompose.R
import com.example.noteappcompose.data.entities.Note
import com.example.noteappcompose.data.viewmodels.NoteViewModel
import com.example.noteappcompose.ui.navigation.Destinations
import com.example.noteappcompose.ui.theme.colorBlue
import com.example.noteappcompose.ui.theme.colorCream
import com.example.noteappcompose.ui.theme.colorGreen
import com.example.noteappcompose.ui.theme.colorLightBlue
import com.example.noteappcompose.ui.theme.colorPink
import com.example.noteappcompose.ui.theme.colorPurple
import com.example.noteappcompose.ui.theme.colorRed
import com.example.noteappcompose.ui.theme.colorYellow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavController
                   ,paddingValues: PaddingValues){

    val scrollBehavior=TopAppBarDefaults.exitUntilCollapsedScrollBehavior()


    Box(modifier = Modifier
        .fillMaxSize()
        ) {
        Scaffold(modifier = Modifier.padding(paddingValues)
            //.nestedScroll(scrollBehavior.nestedScrollConnection)
            //.fillMaxSize()
            //.padding(paddingValues)

            //containerColor = Color.Red
            ,
            /*topBar = {
                TopAppBar(title = {
                    Text(text = "My Notes", fontWeight = FontWeight.W200)
                })
            },*/
            floatingActionButton = {

                FloatingActionButton(onClick = {
                    navController.navigate(Destinations.AddScreen)
                },
                    containerColor = colorCream,
                    contentColor = Color.Black,
                    elevation = FloatingActionButtonDefaults.elevation(20.dp)){

                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }

            },
            floatingActionButtonPosition = FabPosition.End
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                //.padding(it)
                //.padding(it) // buraya paddding verince bottomnav üstünde boşluk olusuyor
            ) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "My Notes", fontWeight = FontWeight.W200, fontSize = 25.sp)
                    }
                    NotesList(navController = navController)
                }




            }
        }
    }












}

@Composable
fun NotesList(navController: NavController,
              viewModel:NoteViewModel= hiltViewModel()
              ){

    /*LaunchedEffect(key1 = Unit) {
        viewModel.getAllNotes()
    }
    var noteList by remember {
        viewModel.noteList
    }*/

    // sayfaya her dönüş yapıldıugında güncellenecek
    LaunchedEffect(key1 = Unit) {
        viewModel.getAllNotes()
    }

    val list by viewModel.list.collectAsState()

    LazyColumn {
        items(list){
            NoteRow(navController = navController, note = it)
        }
    }
}

@Composable
fun NoteRow(navController: NavController,note: Note){
    // Renklerin listesini oluşturun
    val colorList = listOf(
        colorRed, colorBlue, colorGreen, colorPurple, colorYellow, colorPink, colorLightBlue
    )

    // Renkleri rastgele seçin
    val colorState = remember { mutableStateOf(colorList.random()) }

    // Ekran geri döndüğünde rengi güncellemek için LaunchedEffect kullanın
    LaunchedEffect(key1 = Unit) {
        // Renkleri rastgele seçin
        colorState.value = colorList.random()
    }

    //var colorState = animateColorAsState(targetValue = colorList.random(), label = "")

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable {
            navController.navigate(
                Destinations.Detail(
                    id = note.id,
                    note_title = note.note_title,
                    note_favorites = note.note_favorites,
                    note_detail = note.note_detail,
                    note_date = note.note_date,
                    note_time = note.note_time
                )
            )
        },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp)
        ) {
        Column(modifier = Modifier
            .fillMaxSize()
            //.padding(8.dp)
            .background(colorState.value)
            ,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            
            Text(text = note.note_title, fontSize = 25.sp,
                fontWeight = FontWeight.Bold,modifier = Modifier.padding(8.dp))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(text = note.note_detail, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center) {
                Image(imageVector = Icons.Default.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = note.note_date)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.time_icon), contentDescription = "")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = note.note_time)
            }


        }
    }
}