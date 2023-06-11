package com.dev.currencyconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.currencyconverter.tables.Rates
import com.dev.currencyconverter.ui.theme.CurrencyConverterTheme
import com.dev.currencyconverter.ui.theme.Purple200
import com.dev.currencyconverter.ui.theme.Purple500
import com.dev.currencyconverter.ui.theme.Purple700
import com.dev.currencyconverter.ui.ui.theme.LightGreen
import com.dev.currencyconverter.viewmodels.RatesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCurrencyActivity : ComponentActivity() {

    private val viewModel: RatesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddCurrencyScreen()
                }
            }
        }
    }

    @Composable
    fun AddCurrencyScreen() {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (toolbar, infoText) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
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
                        text = "More About Currencies",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                }
            }

            Text(
                text = getString(R.string.info_text),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                ),
                modifier = Modifier.constrainAs(infoText) {
                    top.linkTo(toolbar.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(horizontal = 15.dp)
            )

        }
    }

}
