package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeAppTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableIntStateOf(1) }
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Lemonade",
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white)
                )
            }, colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = colorResource(
                    R.color.teal_700
                )
            )
        )
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(innerPadding), color = MaterialTheme.colorScheme.background

        ) {
            val lemonadeTree = LemonadeStep(
                painterResource(id = R.drawable.lemon_tree),
                stringResource(id = R.string.lemon_tree_content_description),
                stringResource(id = R.string.select_a_lemon),
                2
            )
            val lemon = LemonadeStep(
                painterResource(id = R.drawable.lemon_squeeze),
                stringResource(id = R.string.lemon_content_description),
                stringResource(id = R.string.squeeze_lemon),
                3,
                (1..4).random()
            )

            val lemonadeDrink = LemonadeStep(
                painterResource(id = R.drawable.lemon_drink),
                stringResource(id = R.string.glass_of_lemonade_content_description),
                stringResource(id = R.string.drink_lemonade),
                4
            )

            val emptyGlass = LemonadeStep(
                painterResource(id = R.drawable.lemon_restart),
                stringResource(id = R.string.empty_glass_content_description),
                stringResource(id = R.string.start_again),
                1
            )

            val lemonadeStep = when (currentStep) {
                1 -> lemonadeTree
                2 -> lemon
                3 -> lemonadeDrink
                else -> emptyGlass
            }

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = lemonadeStep.image,
                    contentDescription = lemonadeStep.imageContentDescription,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            lemonadeStep.clickCount++
                            if (lemonadeStep.numberOfClicksRequiredForNextStep == lemonadeStep.clickCount) {
                                currentStep = lemonadeStep.currentStep
                            }
                        }
                        .background(
                            colorResource(R.color.teal_700), shape = RoundedCornerShape(16.dp)
                        ))
                Spacer(modifier = Modifier.height(16.dp))
                Text(lemonadeStep.message, fontSize = 18.sp)
            }

        }
    }
}

private data class LemonadeStep(
    val image: Painter,
    val imageContentDescription: String,
    val message: String,
    val currentStep: Int,
    val numberOfClicksRequiredForNextStep: Int = 1,
    var clickCount: Int = 0
)


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeAppTheme  {
        LemonadeApp()
    }
}