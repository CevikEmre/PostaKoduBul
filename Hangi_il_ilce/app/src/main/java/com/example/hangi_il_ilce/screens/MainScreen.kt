package com.example.hangi_il_ilce.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hangi_il_ilce.R
import com.example.hangi_il_ilce.ui.theme.Hangi_il_ilceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hangi_il_ilceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PageNavigations()
                }
            }
        }
    }
}

@Composable
fun PageNavigations(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainScreen"){
        composable("mainScreen"){
            MainScreen(navController)
        }
        composable("searchByAdressScreen"){
            SearchByAdressScreen()
        }
        composable("searchByZipCodeScreen"){
            SearchByZipCodeScreen()
        }
    }
}

@Composable
fun MainScreen(navController: NavController){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(300.dp))
        Spacer(modifier = Modifier.size(width = 0.dp, height = 18.dp))
        Card(
            modifier = Modifier
                .clickable {
                    navController.navigate("searchByAdressScreen")
                }
                .fillMaxWidth()
                .height(height = 50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight()
            )
            {
                Icon(painter = painterResource(id = R.drawable.search_icon), contentDescription = "" )
                Spacer(modifier = Modifier.size(width = 12.dp, height = 0.dp))
                Text(
                    text = "Adrese Göre Arama",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                )
            }
        }
        Spacer(modifier = Modifier.size(width = 0.dp, height = 14.dp))
        Card(
            modifier = Modifier
                .clickable {
                    navController.navigate("searchByZipCodeScreen")
                }
                .fillMaxWidth()
                .height(height = 50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp).fillMaxHeight()
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(width = 12.dp, height = 0.dp))
                Text(
                    text = "Posta Koduna Göre Arama",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W500,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hangi_il_ilceTheme {
        PageNavigations()
    }
}