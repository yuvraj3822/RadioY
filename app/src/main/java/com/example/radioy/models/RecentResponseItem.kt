package com.example.radioapp.ui
/**
 * This is the POJO class mainly to handle the response
 */
data class RecentResponseItem(
    val album: String,
    val artist: String,
    val image_url: String,
    val link_url: String,
    val name: String,
    val played_at: String,
    val preview_url: String,
    val sid: String
)