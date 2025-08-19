package com.altankoc.bankapp.ui.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altankoc.bankapp.R
import com.altankoc.bankapp.data.model.BankDataItem
import com.altankoc.bankapp.ui.theme.*
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(
    bankDataJson: String
) {
    val context = LocalContext.current

    val gson = Gson()
    val bankJson = URLDecoder.decode(bankDataJson, StandardCharsets.UTF_8.toString())
    val bankData = gson.fromJson(bankJson, BankDataItem::class.java)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        OffWhite,
                        LightBlue.copy(alpha = 0.3f),
                        OffWhite
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        startMapIntent(context, bankData)
                    },
                    containerColor = DarkBlue,
                    contentColor = OffWhite,
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                BankDetailCard(bankData)
            }
        }
    }
}

@Composable
fun BankDetailCard(bankData: BankDataItem = BankDataItem()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Surface,
                            OffWhite.copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                DarkBlue,
                                MediumBlue
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.bank_code),
                        style = TextStyle(
                            color = LightBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Text(
                        text = "${bankData.bankCode}",
                        style = TextStyle(
                            color = OffWhite,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.postal_code),
                        style = TextStyle(
                            color = LightBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Text(
                        text = "${bankData.bankPostalCode}",
                        style = TextStyle(
                            color = OffWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BankCardDetail(
                    Icons.Default.LocationCity,
                    "${bankData.bankCity} / ${bankData.bankDistrict}"
                )

                BankCardDetail(
                    Icons.Default.Store,
                    "${bankData.bankAddressName}"
                )

                BankCardDetail(
                    Icons.Default.LocationOn,
                    "${bankData.bankAddress}"
                )

                BankCardDetail(
                    Icons.Default.Public,
                    "${bankData.bankCoordinate}"
                )

                BankCardDetail(
                    Icons.Default.Atm,
                    "${bankData.bankAtm}"
                )
            }
        }
    }
}

@Composable
fun BankCardDetail(
    bankItemVector: ImageVector,
    bankItemText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        OffWhite.copy(alpha = 0.8f),
                        LightBlue.copy(alpha = 0.3f)
                    )
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MediumBlue,
                            DarkBlue
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = bankItemVector,
                modifier = Modifier.size(24.dp),
                tint = OffWhite,
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = bankItemText,
            style = TextStyle(
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

private fun startMapIntent(
    context: Context,
    bankData: BankDataItem
) {
    val gmmIntentUri = Uri.parse("geo:o,0?q=${bankData.bankAddress}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}