package com.example.imccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imccalculator.ui.theme.IMCCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMCCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun IMCScreen(modifier: Modifier = Modifier) {

    val azul = colorResource(id = R.color.cor_app)

    var resultOn by remember {
        mutableStateOf(false) }

    var peso by remember {
        mutableStateOf("")
    }

    var altura by remember {
        mutableStateOf("")
    }

    var imcCalculado: Double by remember {
        mutableStateOf(0.0)
    }

    var classificacaoCor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // -- header --
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(color = azul),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "Logo App",
                modifier = Modifier
                    .size(80.dp)
                    .padding(horizontal = 12.dp)
            )
            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        // -- form --
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            // -- card result --
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F6F6)),
                elevation = CardDefaults.cardElevation(4.dp)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Seus dados",
                        color = azul,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                        OutlinedTextField(
                            modifier = Modifier
                                .width(300.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                                    singleLine = true,
                            value = altura,
                            onValueChange = {novoValor ->
                                altura = novoValor
                            },
                            placeholder = {
                                Text(
                                    text = "Ex: 170cm"
                                )
                            },
                            label = {
                                Text(
                                    text = "Altura"
                                )
                            },
                            shape = RoundedCornerShape(
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                topStart = 10.dp,
                                bottomEnd = 10.dp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = azul,     // Cor ao clicar
                                unfocusedBorderColor = azul,   // Cor padrão sem clicar
//                                disabledBorderColor = Color.Blue,    // Cor se o campo for desabilitado
                                errorBorderColor = Color.Red        // Cor mesmo se houver erro de validação
                            )
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .width(300.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            value = peso,
                            onValueChange = { novoValor ->
                                peso = novoValor
                            },
                            placeholder = {
                                Text(
                                    text = "Ex: 70kg"
                                )
                            },
                            label = {
                                Text(
                                    text = "Peso"
                                )
                            },
                            shape = RoundedCornerShape(
                                topEnd = 10.dp,
                                bottomStart = 10.dp,
                                topStart = 10.dp,
                                bottomEnd = 10.dp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = azul,     // Cor ao clicar
                                unfocusedBorderColor = azul,   // Cor padrão sem clicar
//                                disabledBorderColor = Color.Blue,    // Cor se o campo for desabilitado
                                errorBorderColor = Color.Red        // Cor mesmo se houver erro de validação
                            )
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                imcCalculado = calcularImc(altura, peso)
                                classificacaoCor = cardIMCColor(definirStatusIMC(imcCalculado))
                                resultOn = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = azul
                            )
                        ) {
                            Text(
                                text = "CALCULAR",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                altura = ""
                                peso = ""
                                resultOn = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            )
                        ) {
                            Text(
                                text = "Limpar",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                }
            }

            if ( resultOn ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = CardDefaults.cardColors(containerColor = classificacaoCor),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${imcCalculado.toString().take(4)} ${definirStatusIMC(imcCalculado)}",
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

fun calcularImc (altura : String, peso : String) : Double{


    val alturaMod = altura.toDouble()/100

    val pesoMod = peso.toDouble()

    val imc =  pesoMod / ( alturaMod * alturaMod )

    return imc

}

fun definirStatusIMC (imc : Double) : String{


    return when {
        imc < 18.5 -> "Abaixo do peso"
        imc > 18.5 && imc < 25 -> "Peso ideal"
        imc >= 25 && imc < 30 -> "Levemente acima do peso."
        imc >= 30 && imc < 35 -> "Obesidade grau I"
        imc >= 35 && imc < 40 -> "Obesidade II"
        else -> "Obesidade grau III"
    }

}

fun cardIMCColor ( classificacao: String) : Color{

    val corRespectiva : Color = when (classificacao) {
        "Peso ideal" -> Color.Green
        "Levemente acima do peso." ->Color(0xF6FF7003)
        else -> {
            Color.Red
        }
    }
    return corRespectiva
}