package com.example.noteappcompose.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Destinations {
    @Serializable
    object Home:Destinations()

    @Serializable
    object Favorites:Destinations()

    @Serializable
    object AddScreen:Destinations()

    @Serializable
    data class Detail(
        var id:Int,
        var note_title:String,
        var note_detail:String,
        var note_favorites:Int=0,
        var note_date:String,
        var note_time:String
    )
}