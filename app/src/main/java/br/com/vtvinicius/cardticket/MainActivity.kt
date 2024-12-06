package br.com.vtvinicius.cardticket


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.vtvinicius.cardticket.ui.theme.CardTicketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val scrollState = rememberScrollState()

            CardTicketTheme {
                Scaffold(
                    containerColor = Color(0xFFECECEF),
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {


                        TicketCard()
                    }
                }
            }
        }
    }
}
//
//@Composable
//fun SimpleScreen() {
//    // Fundo cinza quase branco
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF5F5F5)) // Cor cinza clara
//    ) {
//        // Card de 200x200 com sombra e recortes circulares
//        ElevatedCard(
//            modifier = Modifier
//                .size(200.dp)
//                .align(Alignment.Center),
//            elevation = CardDefaults.elevatedCardElevation(8.dp),
//            shape = CardWithCutoutShape() // Aplicando a forma personalizada
//        ) {
//            // Coluna para organizar as 3 linhas horizontais
//            Column(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                // Linha 1
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth()
//                        .background(Color.LightGray)
//                )
//                // Linha 2
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth()
//                        .background(Color.Gray)
//                )
//                // Linha 3
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth()
//                        .background(Color.DarkGray)
//                )
//            }
//        }
//    }
//}

@Composable
fun TicketCard() {
    val circleRadius = 25.dp
    val cornerRadius = 20.dp
    val middleHeight = circleRadius * 2

    // Armazenamos as medições após a primeira fase
    var measurements by remember { mutableStateOf<Measurements?>(null) }

    if (measurements == null) {
        // FASE 1: Medir primeiro sem shape para descobrir alturas

        SubcomposeLayout { constraints ->
            // Medir Top
            val topMeasurables = subcompose("top") {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Conferência de Tecnologia e Negócios 2024",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.imagem_ticket),
                        contentDescription = "Event",
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Data: 24/10/2024")
                    Text(text = "Local: São Paulo - SP")
                }
            }
            val topPlaceables = topMeasurables.map { it.measure(constraints) }
            val topHeight = topPlaceables.maxOfOrNull { it.height } ?: 0

            // Medir Middle
            val middleHeightPx = middleHeight.roundToPx()
            val middleMeasurables = subcompose("middle") {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..15) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .width(22.dp)
                                    .padding(3.dp),
                                color = Color.LightGray,
                                thickness = 3.dp
                            )
                        }
                    }
                }
            }
            val middlePlaceables = middleMeasurables.map {
                it.measure(
                    constraints.copy(
                        minHeight = middleHeightPx,
                        maxHeight = middleHeightPx
                    )
                )
            }

            // Medir Bottom
            val bottomMeasurables = subcompose("bottom") {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier
                            .size(170.dp),
                        painter = painterResource(id = R.drawable.qr_code_afinz),
                        contentDescription = "QR Code",
                        contentScale = ContentScale.Fit
                    )
                }
            }
            val bottomPlaceables = bottomMeasurables.map { it.measure(constraints) }
            val bottomHeight = bottomPlaceables.maxOfOrNull { it.height } ?: 0

            val totalHeight = topHeight + middleHeightPx + bottomHeight

            // Calcula a razão da posição do recorte
            val circleCenterYRatio = (topHeight + middleHeightPx / 2f) / totalHeight

            measurements = Measurements(
                topHeight = topHeight,
                middleHeightPx = middleHeightPx,
                bottomHeight = bottomHeight,
                totalHeight = totalHeight,
                circleCenterYRatio = circleCenterYRatio
            )

            layout(0,0) {}
        }
    } else {
        // FASE 2: Agora temos circleCenterYRatio, podemos criar o Card com o shape correto
        val circleCenterYRatio = measurements!!.circleCenterYRatio

        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(320.dp),
            shape = CardWithCutoutShape(circleRadius, cornerRadius, circleCenterYRatio),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            // Agora usamos o ProportionalBottomColumn ou qualquer layout desejado
            // Já que o shape está correto, o recorte vai alinhar com o meio
            ProportionalBottomColumn(
                bottomRatio = 0.75f, // Ajuste se quiser diferente
                middleHeight = middleHeight,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Parte Superior
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Conferência de Tecnologia e Negócios 2024",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.imagem_ticket),
                        contentDescription = "Event",
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Data: 24/10/2024")
                    Text(text = "Local: São Paulo - SP")
                }

                // Parte do Meio
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..15) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .width(22.dp)
                                    .padding(3.dp),
                                color = Color.LightGray,
                                thickness = 3.dp
                            )
                        }
                    }
                }

                // Parte Inferior
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(170.dp),
                        painter = painterResource(id = R.drawable.qr_code_afinz),
                        contentDescription = "QR Code",
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

data class Measurements(
    val topHeight: Int,
    val middleHeightPx: Int,
    val bottomHeight: Int,
    val totalHeight: Int,
    val circleCenterYRatio: Float
)

class CardWithCutoutShape(
    private val circleRadius: Dp,
    private val cornerRadius: Dp = 20.dp,
    private val circleCenterYRatio: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val circleRadiusPx = with(density) { circleRadius.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }

        val rectPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, size.width, size.height),
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            )
        }

        // Calcula a posição do recorte com base na razão
        val circleCenterY = size.height * circleCenterYRatio

        val circleCenterLeft = Offset(x = 0f, y = circleCenterY)
        val circleCenterRight = Offset(x = size.width, y = circleCenterY)

        val circlePathLeft = Path().apply {
            addOval(
                Rect(
                    left = circleCenterLeft.x - circleRadiusPx,
                    top = circleCenterLeft.y - circleRadiusPx,
                    right = circleCenterLeft.x + circleRadiusPx,
                    bottom = circleCenterLeft.y + circleRadiusPx
                )
            )
        }

        val circlePathRight = Path().apply {
            addOval(
                Rect(
                    left = circleCenterRight.x - circleRadiusPx,
                    top = circleCenterRight.y - circleRadiusPx,
                    right = circleCenterRight.x + circleRadiusPx,
                    bottom = circleCenterRight.y + circleRadiusPx
                )
            )
        }

        val combinedCirclesPath = Path.combine(
            operation = PathOperation.Union,
            path1 = circlePathLeft,
            path2 = circlePathRight
        )

        val combinedPath = Path.combine(
            operation = PathOperation.Difference,
            path1 = rectPath,
            path2 = combinedCirclesPath
        )

        return Outline.Generic(combinedPath)
    }
}

@Composable
fun ProportionalBottomColumn(
    bottomRatio: Float,
    middleHeight: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Esperamos 3 filhos: Top(0), Middle(1), Bottom(2)
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        require(measurables.size == 3) {
            "ProportionalBottomColumn requires exactly three children: top, middle, and bottom."
        }

        val maxWidth = constraints.maxWidth

        // Medir Top sem restrições para obter a altura real do conteúdo superior
        val topPlaceable = measurables[0].measure(constraints)
        val topHeight = topPlaceable.height

        // Medir o Middle com altura fixa definida por middleHeight
        val middleHeightPx = middleHeight.roundToPx()
        val middlePlaceable = measurables[1].measure(
            constraints.copy(
                minHeight = middleHeightPx,
                maxHeight = middleHeightPx
            )
        )

        // Calcular a altura da parte inferior com base no topHeight
        val bottomFinalHeight = (topHeight * bottomRatio).toInt()

        // Medir o Bottom com a altura calculada
        val bottomPlaceable = measurables[2].measure(
            constraints.copy(
                minHeight = bottomFinalHeight,
                maxHeight = bottomFinalHeight
            )
        )

        // Altura total = Top + Middle + Bottom
        val totalHeight = topHeight + middlePlaceable.height + bottomPlaceable.height

        layout(maxWidth, totalHeight) {
            var yPosition = 0

            // Posiciona a parte superior
            topPlaceable.place(0, yPosition)
            yPosition += topPlaceable.height

            // Posiciona a parte do meio
            middlePlaceable.place(0, yPosition)
            yPosition += middlePlaceable.height

            // Posiciona a parte inferior
            bottomPlaceable.place(0, yPosition)
        }
    }
}