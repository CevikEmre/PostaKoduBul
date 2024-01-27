package com.example.hangi_il_ilce.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hangi_il_ilce.R
import com.example.hangi_il_ilce.constants.data.provinces
import com.example.hangi_il_ilce.constants.ui.CircularIndeterminateProgressBar
import com.example.hangi_il_ilce.constants.ui.DistrictSpinner
import com.example.hangi_il_ilce.constants.ui.ProvinceSpinner
import com.example.hangi_il_ilce.constants.ui.TownSpinner
import com.example.hangi_il_ilce.viewmodel.SearchByAdressScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun SearchByAdressScreen(){

    val viewModel: SearchByAdressScreenViewModel = viewModel()

    val districtList by viewModel.districtList.collectAsState()
    val districtZipCodes by viewModel.townList.collectAsState()

    val printedZipCode by viewModel.printedZipCode.observeAsState("")

    val loading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
                 CenterAlignedTopAppBar(
                     title = {
                         Text(
                             text = "Adrese Göre Arama",
                             fontSize = 26.sp,
                         )
                     },
                     colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                         containerColor = colorResource(id = R.color.appBarColor),
                         titleContentColor = Color.White
                     )
                 )
        },
        content = {
            padding-> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(padding)
            ) {
                if (loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)

                    ){
                        CircularIndeterminateProgressBar(isDisplay = loading)
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        Modifier.size(size = 300.dp)
                    )

                    ProvinceSpinner(
                        label = "İl Seçiniz :            ",
                        choices = provinces,
                        selectedOption = provinces.entries.first().value,
                        onSelected = { selectedProvince ->
                            viewModel.clearState()
                            viewModel.loadZipCodes(selectedProvince.key)

                        },
                    )

                    Spacer(modifier = Modifier.size(width = 0.dp, height = 10.dp))
                    if (districtList.isNotEmpty()) {

                        DistrictSpinner(
                            label = "İlçe Seçiniz :        ",
                            choices = districtList,
                            onSelected = { selected ->
                                viewModel.setDistrictByZipCode(selected)
                                viewModel.clearPrintedZipCode()
                            }
                        )
                        Spacer(modifier = Modifier.size(width = 0.dp, height = 10.dp))

                        TownSpinner(
                            label = "Mahalle Seçiniz : ",
                            choices = districtZipCodes,
                            onSelected = { selected ->
                                viewModel.printZipCode(selected)
                            }
                        )
                        Text(
                            text = printedZipCode,
                            fontSize = 28.sp,
                            modifier = Modifier
                                .padding(vertical = 15.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.clearState()
                                viewModel.clearPrintedZipCode()
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = colorResource(id = R.color.appBarColor)
                            ),
                            modifier = Modifier.padding(vertical = 18.dp)
                        ) {

                            Text(
                                text = "Temizle",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    }
                }
            }
        }
    )
}
