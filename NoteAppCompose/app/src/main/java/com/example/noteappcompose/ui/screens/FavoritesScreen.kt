package com.example.noteappcompose.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteappcompose.R
import com.example.noteappcompose.data.viewmodels.NoteViewModel
import com.example.noteappcompose.data.entities.Note
import com.example.noteappcompose.ui.navigation.Destinations
import com.example.noteappcompose.ui.theme.colorBlue
import com.example.noteappcompose.ui.theme.colorGreen
import com.example.noteappcompose.ui.theme.colorLightBlue
import com.example.noteappcompose.ui.theme.colorPink
import com.example.noteappcompose.ui.theme.colorPurple
import com.example.noteappcompose.ui.theme.colorRed
import com.example.noteappcompose.ui.theme.colorYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(paddingValues: PaddingValues,navController: NavController){

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                Text(text = "My Favorites", fontWeight = FontWeight.W200, fontSize = 25.sp)
            }
            
            FavoritesListSection(navController = navController)





        }
    }


}

@Composable
fun FavoritesListSection(navController: NavController,
                         viewModel:NoteViewModel= hiltViewModel()){

    LaunchedEffect(key1 = Unit) {
        viewModel.getFavorites()
    }

    var favoritesList by remember {
        viewModel.favoritesList
    }

    LazyColumn {
        items(favoritesList){
            FavoriteRow(note = it, navController = navController)
        }
    }

}

@Composable
fun FavoriteRow(note:Note,navController: NavController){
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
        elevation =CardDefaults.cardElevation(8.dp),
        ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(colorState.value),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = note.note_title, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = note.note_detail, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = note.note_date)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.time_icon), contentDescription ="")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = note.note_time)
            }
        }

    }
}