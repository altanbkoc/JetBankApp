package com.altankoc.bankapp.ui.view.home

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.altankoc.bankapp.R
import com.altankoc.bankapp.data.model.BankDataItem
import com.altankoc.bankapp.ui.component.ErrorImage
import com.altankoc.bankapp.ui.component.LoadingAnimation
import com.altankoc.bankapp.ui.component.NoDataImage
import com.altankoc.bankapp.ui.navigation.AppScreen
import com.altankoc.bankapp.ui.theme.DarkBlue
import com.altankoc.bankapp.ui.theme.MediumBlue
import com.altankoc.bankapp.ui.theme.LightBlue
import com.altankoc.bankapp.ui.theme.OffWhite
import com.altankoc.bankapp.ui.viewmodel.HomeViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale


@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val gson = Gson()

    BackHandler {
        (context as? Activity)?.finish()
    }

    val state = viewModel.homeState.value
    val filteredBankData = viewModel.filteredBankList.value
    val searchQuery = remember { mutableStateOf("") }

    val currentNavController = rememberUpdatedState(navController)


    Scaffold(
        containerColor = OffWhite,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(DarkBlue, MediumBlue)
                        )
                    )
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    AsyncImage(
                        model = R.drawable.turkish_flag,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                            .clickable{
                                changeLanguage(context,currentNavController,"tr")
                            },
                        contentScale = ContentScale.Crop,
                        contentDescription = "Turkish"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    AsyncImage(
                        model = R.drawable.english_flag,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                            .clickable{
                                changeLanguage(context,currentNavController,"en")
                            },
                        contentScale = ContentScale.Crop,
                        contentDescription = "English"
                    )
                }

                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { newValue ->
                        searchQuery.value = newValue
                        viewModel.filteredBankList(searchQuery.value)
                    },
                    label = {
                        Text(
                            stringResource(R.string.search_by_city),
                            color = Color.Gray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White.copy(alpha = 0.9f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = LightBlue,
                        cursorColor = DarkBlue,
                        focusedTextColor = DarkBlue,
                        unfocusedTextColor = MediumBlue
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    singleLine = true
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = OffWhite
        ) {
            if(state.isLoading){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    LoadingAnimation()
                }
            } else{
                if(!state.errorMessage.isNullOrEmpty()){
                    ErrorImage()
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                if(filteredBankData.isEmpty()){
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(!state.isLoading){
                                    NoDataImage()
                                }
                            }
                        }
                    }
                } else {
                    items(filteredBankData){ bankItem ->
                        BankDataCard(bankItem) {
                            val bankDataJson = gson.toJson(bankItem)
                            val encodedBankDataJson = URLEncoder.encode(bankDataJson,
                                StandardCharsets.UTF_8.toString()
                            )
                            navController.navigate("${AppScreen.DETAIL_SCREEN.name}/${encodedBankDataJson}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BankDataCard(
    bankItem: BankDataItem = BankDataItem(),
    onClickCard: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DarkBlue.copy(alpha = 0.1f)
            )
            .clickable{ onClickCard.invoke() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(DarkBlue, MediumBlue, LightBlue)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(LightBlue.copy(alpha = 0.2f), MediumBlue.copy(alpha = 0.1f))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalance,
                            modifier = Modifier.size(28.dp),
                            tint = DarkBlue,
                            contentDescription = ""
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = if(bankItem.bankBranch.isNullOrEmpty()){
                                "${bankItem.bankDistrict} / ${bankItem.bankCity}"
                            } else {
                                "${bankItem.bankBranch}"
                            },
                            style = TextStyle(
                                color = DarkBlue,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    LightBlue.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(LightBlue, CircleShape)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "${bankItem.bankAddress}",
                        style = TextStyle(
                            color = MediumBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            lineHeight = 20.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

private fun changeLanguage(
    context: Context,
    currentNavController: State<NavController>,
    language: String
){
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config,context.resources.displayMetrics)

    val currentDestination = currentNavController.value.currentDestination?.id
    currentNavController.value.popBackStack()
    currentDestination?.let {
        currentNavController.value.navigate(it)
    }
}