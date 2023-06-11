package com.dev.currencyconverter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.currencyconverter.tables.History
import com.dev.currencyconverter.tables.Rates
import com.dev.currencyconverter.ui.theme.CurrencyConverterTheme
import com.dev.currencyconverter.ui.ui.theme.LightGreen
import com.dev.currencyconverter.viewmodels.HistoryViewModel
import com.dev.currencyconverter.viewmodels.RatesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val ratesViewModel: RatesViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()
    private val mainList: ArrayList<Rates> by lazy { ArrayList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!getBooleanFromSharedPreferences(this)) {
            ratesViewModel.addRates(Rates(0,"USD","EUR",0.93254f))
            ratesViewModel.addRates(Rates(0,"USD","GBP",0.80318f))
            ratesViewModel.addRates(Rates(0,"USD","JPY",140.09f))
            ratesViewModel.addRates(Rates(0,"USD","AUD",1.5124f))

            ratesViewModel.addRates(Rates(0,"EUR","USD",1.0723f))
            ratesViewModel.addRates(Rates(0,"EUR","JPY",150.23f))
            ratesViewModel.addRates(Rates(0,"EUR","GBP",0.86128f))
            ratesViewModel.addRates(Rates(0,"EUR","AUD",1.6218f))

            ratesViewModel.addRates(Rates(0,"GBP","JPY",174.42f))
            ratesViewModel.addRates(Rates(0,"GBP","USD",1.40f))
            ratesViewModel.addRates(Rates(0,"GBP","EUR",1.1611f))
            ratesViewModel.addRates(Rates(0,"GBP","AUD",1.8830f))


            ratesViewModel.addRates(Rates(0,"JPY","GBP",0.0057332f))
            ratesViewModel.addRates(Rates(0,"JPY","USD",0.0071382f))
            ratesViewModel.addRates(Rates(0,"JPY","EUR",0.0066566f))
            ratesViewModel.addRates(Rates(0,"JPY","AUD",0.010796f))

            ratesViewModel.addRates(Rates(0,"AUD","GBP",0.53106f))
            ratesViewModel.addRates(Rates(0,"AUD","USD",0.66119f))
            ratesViewModel.addRates(Rates(0,"AUD","EUR",0.61659f))
            ratesViewModel.addRates(Rates(0,"AUD","JPY",92.628f))


            saveBooleanToSharedPreferences(this, true)
        }

        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val dataList1 = remember {
                        mutableStateListOf<String>()
                    }

                    val dataList2 = remember {
                        mutableStateListOf<String>()
                    }

                    ratesViewModel.ratesList.observe(this) {
                        if (it != null) {
                            dataList1.clear()
                            dataList2.clear()
                            mainList.clear()
                            mainList.addAll(it)
                            for (i in it) {
                                if (!dataList1.contains(i.currency1)) {
                                    dataList1.add(i.currency1)
                                }

                                if (!dataList2.contains(i.currency2)) {
                                    dataList2.add(i.currency2)
                                }
                            }
                        }
                    }

                    MyMainScreen(dataList1, dataList2)
                }
            }
        }
    }

    fun saveBooleanToSharedPreferences(context: Context, value: Boolean) {
        val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("open", value)
        editor.apply()
    }

    fun getBooleanFromSharedPreferences(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(
            "open",
            false
        ) // false is the default value if key doesn't exist
    }

    @Composable
    fun MyMainScreen(list1: List<String>, list2: List<String>) {

        val amount = remember { mutableStateOf("") }
        val selectedItem1 = remember { mutableStateOf("") }
        val selectedItem2 = remember { mutableStateOf("") }
        val convertedAmount = remember { mutableStateOf(0.0f) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.DarkGray
                )
        ) {

            val (topText, amountLayout,
                convertLayout, bottomBox) = createRefs()

            Text(
                text = "Currency Converter",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .constrainAs(topText) {
                        top.linkTo(parent.top, 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center,
                color = LightGreen
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .constrainAs(amountLayout) {
                        top.linkTo(topText.bottom, margin = 60.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = amount.value,
                        onValueChange = {
                            amount.value = it
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .padding(start = 20.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(7.dp)
                            ),
                        shape = RoundedCornerShape(10.dp),
                        placeholder = {
                            Text(
                                text = "Amount",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            color = Color.White
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Spinner(
                        list = list1,
                        selectedItem = selectedItem1.value,
                        onItemSelected = { selectedItem1.value = it }
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "to",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Spinner(
                        list = list2,
                        selectedItem = selectedItem2.value,
                        onItemSelected = { selectedItem2.value = it }
                    )
                }
            }


            // This is the layout that contains the convert button and text of conversion
            Box(
                modifier = Modifier
                    .constrainAs(convertLayout) {
                        top.linkTo(amountLayout.bottom, margin = 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end, 20.dp)
                    }
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .weight(1f)
                            .height(50.dp)
                            .background(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = convertedAmount.value.toString(),
                            style = TextStyle(
                                fontSize = 20.sp,
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }


                    Button(
                        onClick = {

                            // Checking the fields if not empty
                            if (amount.value.isNotEmpty() &&
                                selectedItem1.value.isNotEmpty() &&
                                selectedItem2.value.isNotEmpty()
                            ) {
                                // Getting the list of currencies with first and second currency
                                val filteredRates = mainList.filter { rate ->
                                    rate.currency1 == selectedItem1.value &&
                                            rate.currency2 == selectedItem2.value
                                }

                                // If list is not null
                                if (filteredRates.isNotEmpty()) {

                                    // Parse the value from string field
                                    val inputAmount = Integer.parseInt(amount.value)

                                    // Convert the value into desired currency
                                    convertedAmount.value =
                                        inputAmount * filteredRates[0].totalConversion

                                    // Store currency into the history
                                    historyViewModel.addHistory(
                                        History(
                                            // pass id only zero because its auto incremental
                                            // otherwise it will show error
                                            0,
                                            selectedItem1.value,
                                            selectedItem2.value,
                                            inputAmount.toFloat(),
                                            convertedAmount.value
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "This combination does not exist",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Select Currencies",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(140.dp)
                            .padding(horizontal = 7.dp),
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
                                text = "Convert",
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

            // This is the start of the bottom green box
            Box(
                modifier = Modifier
                    .constrainAs(bottomBox) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .background(
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                        color = LightGreen
                    )
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Button(
                        onClick = {
                            val i = Intent(
                                this@MainActivity,
                                HistoryActivity::class.java
                            )
                            startActivity(i)
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .width(180.dp)
                            .padding(horizontal = 7.dp),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(15.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    shape = RoundedCornerShape(15.dp),
                                    color = Color.White
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(24.dp)
                                        .width(24.dp),
                                    tint = Color.Black
                                )

                                Text(
                                    text = "History",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily.Serif
                                    ),
                                    color = Color.Black
                                )

                            }

                        }

                    }


                    Spacer(modifier = Modifier.height(15.dp))


                    Button(
                        onClick = {
                            val i = Intent(
                                this@MainActivity,
                                ConversionRatesActivity::class.java
                            )
                            startActivity(i)
                        },
                        modifier = Modifier
                            .height(100.dp)
                            .width(180.dp)
                            .padding(horizontal = 7.dp),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(15.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    shape = RoundedCornerShape(15.dp),
                                    color = Color.White
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Icon(
                                    imageVector = Icons.Default.AutoGraph,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(24.dp)
                                        .width(24.dp),
                                    tint = Color.Black
                                )

                                Text(
                                    text = "Current Exchange Rates",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily.Serif
                                    ),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )

                            }

                        }

                    }


                }

            }

        }

    }

    @Composable
    fun Spinner(
        list: List<String>,
        selectedItem: String,
        onItemSelected: (sItem: String) -> Unit
    ) {
        val expended = rememberSaveable {
            mutableStateOf(false)
        }

        OutlinedButton(
            onClick = { expended.value = true },
            modifier = Modifier
                .width(90.dp)
                .height(60.dp)
        ) {

            Text(
                text = selectedItem,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.Black
                ),
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                tint = Color.Black
            )
        }

        DropdownMenu(expanded = expended.value,
            onDismissRequest = {
                expended.value = false
            }) {

            list.forEach {
                DropdownMenuItem(onClick = {
                    expended.value = false
                    onItemSelected(it)
                }) {
                    Text(text = it)
                }
            }

        }

    }
}

