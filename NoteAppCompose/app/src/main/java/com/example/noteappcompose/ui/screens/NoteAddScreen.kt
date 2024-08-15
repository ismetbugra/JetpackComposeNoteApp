package com.example.noteappcompose.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteappcompose.R
import com.example.noteappcompose.data.viewmodels.NoteViewModel
import com.example.noteappcompose.utils.Tools
import java.text.SimpleDateFormat
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddScreen(navController: NavController,paddingValues: PaddingValues,
                  viewModel :NoteViewModel= hiltViewModel()){

    var context=LocalContext.current



    var noteTitle by remember {
        mutableStateOf("")
    }

    var noteDetail by remember {
        mutableStateOf("")
    }

    // ---------- date picker
    var state = rememberDatePickerState()

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var dateResult by remember {
        mutableStateOf("")
    }

    // ------------------- time picker

    var showTimePicker by remember {
        mutableStateOf(false)
    }

    var timeState = rememberTimePickerState(is24Hour = true)


    // time formatı için
    var formatter = remember {
        SimpleDateFormat("HH:mm",Locale.getDefault())
    }

    // şu anki tarihi saaati aldık
    val cal = Calendar.getInstance()

    var timeResult by remember {
        mutableStateOf(formatter.format(cal.time))
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                ) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly) {

                    OutlinedTextField(value = noteTitle, onValueChange = {
                        noteTitle=it
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = {
                            Text(text = "Note Title")
                        },
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            focusedLabelColor = Color.Black))

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(value = noteDetail, onValueChange = {
                        noteDetail=it
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(400.dp),
                        label = {
                            Text(text = "Note Detail")
                        },
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            focusedLabelColor = Color.Black))
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(),
                        //horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        IconButton(onClick = { showDatePicker=!showDatePicker }) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Note date:", fontSize = 15.sp, )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = dateResult, fontSize = 15.sp, )


                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                        //horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { showTimePicker=!showTimePicker }) {
                            Icon(painter = painterResource(id = R.drawable.time_icon), contentDescription ="" )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Note time:", fontSize = 15.sp, )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = timeResult, fontSize = 15.sp, )


                    }

                    //Spacer(modifier = Modifier.height(16.dp))

                    // date picker dialog -- material3 de var
                    if (showDatePicker){
                        val confirmEnabled by derivedStateOf { state.selectedDateMillis != null }

                        DatePickerDialog(
                            onDismissRequest = { showDatePicker=false },
                            confirmButton = {
                                Button(onClick = {
                                    showDatePicker=false
                                    var date =""
                                    if (state.selectedDateMillis!=null){
                                        date=Tools.convertLongToTime(state.selectedDateMillis!!)
                                    }

                                    dateResult= date.toString()

                                },
                                    enabled = confirmEnabled,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green,
                                        contentColor = Color.White)
                                ) {
                                    Text(text = "Confirm")
                                }
                            },
                            colors = DatePickerDefaults.colors()) {
                            DatePicker(state = state)

                        }
                    }

                    // custom timepicker dialog olusturuldu
                    if (showTimePicker){

                        //custom dialog olustuduk
                        Dialog(onDismissRequest = { showTimePicker=false }) {
                            Card() {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = "Select Time", fontSize = 25.sp, fontWeight = FontWeight.Bold)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // time picker burada -- time seçilir
                                    TimePicker(state = timeState)

                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically) {
                                        TextButton(onClick = { showTimePicker=false }) {
                                            Text(text = "Cancel")
                                        }
                                        TextButton(onClick = {
                                            cal.set(Calendar.HOUR_OF_DAY,timeState.hour)
                                            cal.set(Calendar.MINUTE,timeState.minute)
                                            cal.isLenient=false
                                            timeResult=formatter.format(cal.time)
                                            showTimePicker=false

                                        }) {
                                            Text(text = "Confirm")
                                        }
                                    }
                                }
                            }
                        }

                    }

                    
                    Button(onClick = {
                        if(!noteTitle.isNullOrEmpty() && !noteDetail.isNullOrEmpty() && !timeResult.isNullOrEmpty() && !dateResult.isNullOrEmpty()){
                            val insertNote= com.example.noteappcompose.data.entities.Note(0,noteTitle,noteDetail,0,dateResult,timeResult)

                            viewModel.insertNote(insertNote)
                            viewModel.getAllNotes()

                        }
                        else{
                            Toast.makeText(context,"Enter necessery fields",Toast.LENGTH_LONG).show()
                        }

                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        , modifier = Modifier.padding(16.dp)) {
                        Text(text = "Add Note")
                    }
                }
            }

    }



}