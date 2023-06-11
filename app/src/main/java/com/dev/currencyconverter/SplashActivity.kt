package com.dev.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.currencyconverter.ui.theme.CurrencyConverterTheme
import com.dev.currencyconverter.ui.ui.theme.LightGreen
import com.dev.currencyconverter.ui.ui.theme.LightTransparent

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                MySplashScreen()
            }
        }
    }

    @Composable
    fun MySplashScreen() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(R.drawable.splash_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                // Place your UI components using ConstraintLayout's syntax
                val guideline = createGuidelineFromTop(0.35f)
                val (text1, text2, btn) = createRefs()

                Text(
                    text = "Hello !",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .constrainAs(text1) {
                            top.linkTo(guideline)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = LightTransparent
                        ),
                    textAlign = TextAlign.Center,

                    )

                Text(
                    text = "Embrace Financial Freedom:\n" +
                            "Bridge Border to Currency Conversion",
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier
                        .constrainAs(text2) {
                            top.linkTo(text1.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = LightTransparent
                        ),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        val i = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    },
                    modifier = Modifier
                        .constrainAs(btn) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, 40.dp)
                        }
                        .height(50.dp)
                        .width(250.dp),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(15.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                shape = RoundedCornerShape(15.dp),
                                color = LightGreen
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Get Started",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Serif
                            ),
                            color = Color.Black
                        )

                    }

                }


            }
        }
    }
}
