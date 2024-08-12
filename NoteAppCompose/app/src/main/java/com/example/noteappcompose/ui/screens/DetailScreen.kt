package com.example.noteappcompose.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.noteappcompose.data.entities.Note
import com.example.noteappcompose.data.viewmodels.NoteViewModel
import com.example.noteappcompose.ui.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun DetailScreen(
     args:Destinations.Detail,
     paddingValues: PaddingValues,
     navController: NavController,
     viewModel: NoteViewModel = hiltViewModel()
){

     var editedDetail by remember {
          mutableStateOf(args.note_detail)
     }

     var editedTitle by remember {
          mutableStateOf(args.note_title)
     }

     var editedDate by remember {
          mutableStateOf(args.note_date)
     }
     var editedTime by remember {
          mutableStateOf(args.note_time)
     }

     var editedState by remember {
          mutableStateOf(false)
     }

     var favoriteState by remember {
          mutableStateOf(if (args.note_favorites==0) false else true)
     }

     val context = LocalContext.current

     Box(modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)) {
          Column(modifier = Modifier.fillMaxSize()) {
               Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Note Detail", fontSize = 35.sp, fontWeight = FontWeight.W100)

                    Row() {
                         IconButton(onClick = {
                              editedState=!editedState
                         }){
                              Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                         }
                         IconButton(onClick = {

                              //delete işlemi
                              var deletedNote=Note(args.id,"","",0,"","")
                              viewModel.deleteNote(deletedNote)
                              navController.navigate(Destinations.Home){
                                   popUpTo(navController.graph.findStartDestination().id){
                                        inclusive=true
                                   }
                              }

                         }){
                              Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                         }

                         IconButton(onClick = {
                              favoriteState=!favoriteState
                              if (favoriteState){
                                   val selectedNote= Note(args.id,editedTitle,editedDetail,1,editedDate,editedTime)
                                   viewModel.updateNote(selectedNote)
                              }else{
                                   val unselectedNote= Note(args.id,editedTitle,editedDetail,0,editedDate,editedTime)
                                   viewModel.updateNote(unselectedNote)
                              }
                         }) {
                              Icon(imageVector =if (favoriteState){
                                   Icons.Filled.Favorite
                              }else{
                                   Icons.Rounded.FavoriteBorder
                                   }, contentDescription ="")
                         }


                    }

               }

               Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                    Column(modifier = Modifier
                         .fillMaxWidth()
                         .padding(16.dp),
                         verticalArrangement = Arrangement.SpaceEvenly,
                         horizontalAlignment = Alignment.Start) {
                         Text(text = "Note Title", fontSize = 25.sp, fontWeight = FontWeight.W200)

                         Spacer(modifier = Modifier.height(16.dp))

                         TextField(value = editedTitle, onValueChange = {

                              editedTitle=it
                         },
                              enabled = editedState,
                              readOnly = !editedState,
                              colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,
                                   unfocusedContainerColor = Color.White,
                                   disabledContainerColor = Color.White))
                    }

                    Column(modifier = Modifier
                         .fillMaxWidth()
                         .padding(16.dp),
                         verticalArrangement = Arrangement.SpaceEvenly,
                         horizontalAlignment = Alignment.Start) {
                         Text(text = "Note Detail", fontSize = 25.sp, fontWeight = FontWeight.W200)

                         Spacer(modifier = Modifier.height(16.dp))

                         TextField(value = editedDetail, onValueChange = {

                              editedDetail=it
                         },
                              enabled = editedState,
                              readOnly = !editedState,
                              colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,
                                   unfocusedContainerColor = Color.White,
                                   disabledContainerColor = Color.White))
                    }

                    AnimatedVisibility(visible = editedState) {
                         Button(onClick = {
                              var updatedNote=Note(args.id,editedTitle,editedDetail,args.note_favorites,args.note_date,args.note_time)
                              viewModel.updateNote(updatedNote)
                              editedState=!editedState
                              Toast.makeText(context,"Saves were changed",Toast.LENGTH_LONG).show()
                         },
                              colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                              Text(text = "Save updates")
                         }
                    }
               }



          }
     }

}