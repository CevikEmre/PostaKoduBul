package com.example.hangi_il_ilce.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hangi_il_ilce.R
import com.example.hangi_il_ilce.constants.ui.CircularIndeterminateProgressBar
import com.example.hangi_il_ilce.models.ZipCode
import com.example.hangi_il_ilce.viewmodel.SearchByZipCodeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun SearchByZipCodeScreen() {
    val viewModel: SearchByZipCodeScreenViewModel = viewModel()
    val zipCodeValue = remember { mutableStateOf("") }
    var filteredList by remember { mutableStateOf<List<ZipCode>>(emptyList()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(zipCodeValue.value) {
        if (zipCodeValue.value.length == 5) {
            if (zipCodeValue.value.take(1).toInt() == 0){
                viewModel.loadZipCodes(zipCodeValue.value.substring(1,2))
                viewModel.filterByPk(zipCodeValue.value)
                filteredList = viewModel.filterByPkList.value
            }
            else
            viewModel.loadZipCodes(zipCodeValue.value.take(2))
            viewModel.filterByPk(zipCodeValue.value)
            filteredList = viewModel.filterByPkList.value
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Posta Koduna Göre Arama",
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
            if (loading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White)
                ) {
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
                /*ProvinceSpinner(
                    label = "İl Seçiniz :            ",
                    choices = provinces,
                    selectedOption = provinces.entries.first().value,
                    onSelected = { selectedProvince ->
                        viewModel.loadZipCodes(selectedProvince.key)
                    }
                )
                Spacer(modifier = Modifier.size(width = 0.dp, height = 18.dp))*/

                    TextField(
                        value = zipCodeValue.value,
                        onValueChange = {
                            if (it.length <= 5){
                                zipCodeValue.value = it
                            }

                        },
                        label = { Text(text = "Posta Kodu Giriniz") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        )
                    )
                    Spacer(modifier = Modifier.size(width = 0.dp, height = 12.dp))
                    Row {
                        Button(
                            onClick = {
                                    if (zipCodeValue.value.length < 5) {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Posta Kodu 5 karakterden oluşmalıdır")
                                        }
                                    } else {
                                        coroutineScope.launch {
                                            viewModel.filterByPk(zipCodeValue.value)
                                            filteredList = viewModel.filterByPkList.value
                                        }

                                    }

                            },
                            enabled = !loading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.appBarColor),
                                contentColor = Color.White
                            )

                        ) {
                            Text(text = "Ara")
                        }
                        Spacer(modifier = Modifier.size(width = 18.dp, height = 0.dp))
                        Button(
                            onClick = { 
                                filteredList = emptyList()
                                zipCodeValue.value = ""
                            },
                            enabled = !loading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.appBarColor),
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Temizle")
                            
                        }
                    }
                    Box(
                        Modifier.verticalScroll(rememberScrollState())){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (f in filteredList){
                                Text(text = f.town)
                            }
                        }
                    }

            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 150.dp)

            ) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    )
}
