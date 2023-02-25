package com.example.lazycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.lazycompose.ui.theme.LazyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    LazyColumn{
        items(100) {
            LazyRow {
                items(10) {
                    Video(
                        modifier = Modifier
                            .width(200.dp)
                            .height(120.dp)
                            .padding(16.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {

            }
        }
    }
}

@Composable
private fun Video(
    modifier: Modifier = Modifier,
    url: String = "https://picsum.photos/200/120",
    duration: String = "100",
    onVideoClick: () -> Unit = {},
    shimmerModifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onVideoClick()
        }
    ) {
        VideoImageBox(
            modifier = Modifier.then(shimmerModifier),
            url = url,
            duration = duration
        )
    }
}

@Composable
private fun VideoImageBox(
    modifier: Modifier,
    url: String,
    duration: String,
) {
    Card(
        modifier = modifier
            .aspectRatio(16f / 9)
            .shadow(
                elevation = 12.dp,
                spotColor = Color.Gray,
                shape = RoundedCornerShape(size = 12.dp)
            )
    ) {
        ConstraintLayout {
            val (image, durationBox) = createRefs()

            AsyncImage(
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.constrainAs(durationBox) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = duration,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    modifier = Modifier
                        .size(12.dp)
                        .padding(2.dp),
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LazyComposeTheme {
        Greeting("Android")
    }
}