package com.dev.currencyconverter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.currencyconverter.tables.History
import com.dev.currencyconverter.tables.Rates
import com.dev.currencyconverter.ui.theme.CurrencyConverterTheme
import com.dev.currencyconverter.ui.ui.theme.LightGreen
import com.dev.currencyconverter.viewmodels.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : ComponentActivity() {

    // Getting the viewModel instance from hilt
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val list = remember { mutableStateListOf<History>() }

                    // Getting history list from db using viewModel
                    viewModel.historyList.observe(this) {
                        if (it != null) {
                            list.clear()
                            list.addAll(it)
                        }
                    }

                    // Setting up the views
                    HistoryRecyclerViewColumn(history = list, c = this)

                }
            }
        }
    }

    // Recyclerview to show the list items
    @Composable
    fun HistoryRecyclerViewColumn(history: List<History>, c: Context) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {

                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val (backBtn, superTitle) = createRefs()

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .constrainAs(backBtn) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start, margin = 20.dp)
                                bottom.linkTo(parent.bottom)
                            }
                            .clickable {
                                finish()
                            },
                        tint = Color.Black
                    )

                    Text(
                        modifier = Modifier.constrainAs(superTitle) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        text = "History",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                }
            }



            Spacer(modifier = Modifier.size(2.dp))

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(history) {
                        HistoryItemCard(item = it, c)
                    }
                }

            }
        }
    }

    // Card for the recyclerview
    @Composable
    fun HistoryItemCard(item: History, cardContext: Context) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.Transparent
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = item.currency1,
                    minLines = 1,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = item.totalAmount.toString(),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )


                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = "to",
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = item.currency2,
                    minLines = 1,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = item.totalConversion.toString(),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.size(10.dp))

            }

        }
    }


}


