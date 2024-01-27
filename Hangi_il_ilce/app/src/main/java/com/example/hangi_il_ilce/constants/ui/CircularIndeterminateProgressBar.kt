package com.example.hangi_il_ilce.constants.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.hangi_il_ilce.R

@Composable
fun CircularIndeterminateProgressBar(
    isDisplay:Boolean
) {
    if (isDisplay){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator(
                color = colorResource(id = R.color.appBarColor)
            )
        }
    }

}