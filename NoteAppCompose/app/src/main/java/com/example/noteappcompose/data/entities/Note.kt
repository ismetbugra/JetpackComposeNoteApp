package com.example.noteappcompose.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var note_title:String,
    var note_detail:String,
    var note_favorites:Int=0,
    var note_date:String,
    var note_time:String
){
}