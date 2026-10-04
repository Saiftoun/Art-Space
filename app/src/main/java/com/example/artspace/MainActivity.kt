package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtworkWall(imageRes: Int, description: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shadowElevation = 16.dp) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(32.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(title: String, artist: String, year: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFECEBF4))
            .padding(16.dp)
    ) {
        Text(text = title, fontSize = 28.sp, fontWeight = FontWeight.Light)
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(artist) }
                append(" ($year)")
            }
        )
    }
}

// Section 3: display controller
@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onPrevious, modifier = Modifier.width(120.dp)) { Text("Previous") }
        Button(onClick = onNext, modifier = Modifier.width(120.dp)) { Text("Next") }
    }
}

@Composable
fun ArtSpaceApp() {
    var id by rememberSaveable { mutableIntStateOf(1) }

    val imageRes = when (id) {
        1 -> R.drawable.art1
        2 -> R.drawable.art2
        else -> R.drawable.art3
    }
    val title = when (id) {
        1 -> "Still Life of Blue Rose and Other Flowers"
        2 -> "Second Title"
        else -> "Third Title"
    }
    val artist = when (id) {
        1 -> "Owen Scott"
        2 -> "Artist Two"
        else -> "Artist Three"
    }
    val year = when (id) {
        1 -> "2021"
        2 -> "2020"
        else -> "2019"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ArtworkWall(imageRes, title, Modifier.weight(1f))
        ArtworkDescriptor(title, artist, year)
        DisplayController(
            onPrevious = {
                id = when (id) {
                    1 -> 3
                    else -> id - 1
                }
            },
            onNext = {
                id = when (id) {
                    3 -> 1
                    else -> id + 1
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}