package com.example.hangi_il_ilce.constants.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangi_il_ilce.R
import com.example.hangi_il_ilce.models.ZipCode

@Composable
fun DistrictSpinner(
    label: String,
    choices: List<ZipCode>,
    onSelected: (selected: ZipCode) -> Unit,
) {
    var selectedItem by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(vertical = 5.dp),
            fontSize = 20.sp,
        )
        Box(
            modifier = Modifier
                .width(180.dp)
                .border(
                    width = 2.dp, color = colorResource(id = R.color.appBarColor)
                )
                .clickable {
                    isExpanded = !isExpanded
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedItem,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = "",
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier.background(color = Color.White),
                ) {
                    choices
                        ?.asSequence() // Convert the list to a sequence
                        ?.distinctBy { it.district } // Keep only unique districts
                        ?.forEach { choose ->
                        DropdownMenuItem(text = { Text(text = choose.district) }, onClick = {
                            isExpanded = false
                            selectedItem = choose.district
                            onSelected(choose)
                        })
                    }
                }
            }
        }
    }
}
