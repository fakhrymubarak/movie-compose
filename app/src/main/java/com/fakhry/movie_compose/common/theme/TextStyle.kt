package com.fakhry.movie_compose.common.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


object SansSerif {
    private val sansSerif = FontFamily.SansSerif

    object Sp14 {
        private val size = 14.sp

        val Regular =
            TextStyle(fontFamily = sansSerif, fontSize = size, fontWeight = FontWeight.W400)
        val Bold = TextStyle(fontFamily = sansSerif, fontSize = size, fontWeight = FontWeight.W500)
    }

    object Sp16 {
        private val size = 16.sp

        val Bold = TextStyle(fontFamily = sansSerif, fontSize = size, fontWeight = FontWeight.W500)
    }
}