package com.example.climbingapp
///
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climbingapp.ui.theme.ClimbingAppTheme
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClimbingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Objects(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Objects(modifier: Modifier = Modifier) {
    var Score by rememberSaveable { mutableStateOf(0) }
    var Hold by rememberSaveable { mutableStateOf(0) }
    var IsGermanEnabled by rememberSaveable { mutableStateOf(false) }
    var IsClimbingEnabled by rememberSaveable { mutableStateOf(true) }
    var IsFallingEnabled by rememberSaveable { mutableStateOf(false) }
    var MaxHoldAlert by rememberSaveable { mutableStateOf(false) }
    var ScoreColourInt by rememberSaveable { mutableStateOf(Color.Transparent.toArgb()) }
    var ScoreColour by remember { mutableStateOf(Color(ScoreColourInt)) }
    val AppConfiguration = LocalConfiguration.current
    val LanguageBtnText = if (IsGermanEnabled) "Englisch" else "German"
    val ScoreBtnText = if (IsGermanEnabled) "Punkte" else "Score"
    val HoldBtnText = if (IsGermanEnabled) "Griff" else "Hold"
    val ClimbBtnText = if (IsGermanEnabled) "Klettern" else "Climb"
    val FallBtnText = if (IsGermanEnabled) "Sturz" else "Fall"
    val ResetBtnText = if (IsGermanEnabled) "Zurücksetzen" else "Reset"
    val IsLandscape = AppConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (MaxHoldAlert) {
        AlertDialog(
            onDismissRequest = { MaxHoldAlert = false },
            confirmButton = {
                Button(onClick = { MaxHoldAlert = false }) {
                    Text("OK")
                }
            },
            title = { Text("ClimbingApp") },
            text = { Text("Maximum hold reached!") }
        )
    }

    if (IsLandscape) {
        Box(modifier = modifier
            .fillMaxSize(),
            contentAlignment = Alignment.TopEnd) {
            Row(
                modifier = modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .padding(2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = modifier
                    .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Button(
                        onClick = {
                            Hold++
                            if (Hold == 9) {
                                IsClimbingEnabled = false
                                MaxHoldAlert = true
                            }
                            if (Hold >= 7) {
                                Score += 3
                                ScoreColourInt = Color.Red.toArgb()
                                ScoreColour = Color(ScoreColourInt)
                            }
                            if (Hold >= 4 && Hold <= 6) {
                                Score += 2
                                ScoreColourInt = Color.Green.toArgb()
                                ScoreColour = Color(ScoreColourInt)
                            }
                            if (Hold <= 3) {
                                Score++
                                ScoreColourInt = Color.Blue.toArgb()
                                ScoreColour = Color(ScoreColourInt)
                            }
                            if (Score > 0) {
                                IsFallingEnabled = true
                            }
                            if (Score > 18 || Score < 0) {
                                Score = 0
                            }
                            if (Hold > 9 || Hold < 0) {
                                Hold = 0
                            }
                        },
                        modifier = Modifier
                            .padding(2.dp),
                        enabled = IsClimbingEnabled
                    ) {
                        Text(
                            modifier = modifier,
                            fontSize = 23.sp,
                            text = ClimbBtnText
                        )
                    }
                    Button(
                        onClick = {
                            Hold = 0
                            ScoreColourInt = Color.Transparent.toArgb()
                            ScoreColour = Color(ScoreColourInt)
                            IsClimbingEnabled = false
                            if (Score < 9) {
                                if (Score > 3) {
                                    Score -= 3
                                } else {
                                    Score = 0
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(2.dp),
                        enabled = IsFallingEnabled
                    ) {
                        Text(
                            modifier = modifier,
                            fontSize = 23.sp,
                            text = FallBtnText
                        )
                    }
                }
                Column(
                    modifier = modifier
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            Score = 0
                            Hold = 0
                            IsClimbingEnabled = true
                            IsFallingEnabled = false
                            ScoreColourInt = Color.Transparent.toArgb()
                            ScoreColour = Color(ScoreColourInt)
                        },
                        modifier = Modifier
                            .padding(2.dp)
                    ) {
                        Text(
                            modifier = modifier,
                            fontSize = 23.sp,
                            text = ResetBtnText
                        )
                    }
                    Button(
                        onClick = {
                            if(IsGermanEnabled == true)
                            {
                                IsGermanEnabled = false
                            } else {
                                IsGermanEnabled = true
                            }
                        },
                        modifier = Modifier
                            .padding(2.dp)
                    ) {
                        Text(
                            modifier = modifier,
                            fontSize = 23.sp,
                            text = LanguageBtnText
                        )
                    }
                }
            }

        }
        Box(modifier = modifier
            .fillMaxSize(),
            contentAlignment = Alignment.TopStart) {
            Column(
                modifier = modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = modifier
                        .background(ScoreColour)
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = ScoreBtnText
                    )
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = ":  " + Score.toString(),
                    )
                }
                Row(
                    modifier = modifier
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = HoldBtnText
                    )
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = ":  " + Hold.toString(),
                    )
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    Hold++
                    if (Hold == 9) {
                        IsClimbingEnabled = false
                        MaxHoldAlert = true
                    }
                    if (Hold >= 7) {
                        Score += 3
                        ScoreColourInt = Color.Red.toArgb()
                        ScoreColour = Color(ScoreColourInt)
                    }
                    if (Hold >= 4 && Hold <= 6) {
                        Score += 2
                        ScoreColourInt = Color.Green.toArgb()
                        ScoreColour = Color(ScoreColourInt)
                    }
                    if (Hold <= 3) {
                        Score++
                        ScoreColourInt = Color.Blue.toArgb()
                        ScoreColour = Color(ScoreColourInt)
                    }
                    if (Score > 0) {
                        IsFallingEnabled = true
                    }
                    if (Score > 18 || Score < 0) {
                        Score = 0
                    }
                    if (Hold > 9 || Hold < 0) {
                        Hold = 0
                    }
                },
                modifier = Modifier
                    .padding(8.dp),
                enabled = IsClimbingEnabled
            ) {
                Text(
                    modifier = modifier,
                    fontSize = 23.sp,
                    text = ClimbBtnText
                )
            }
            Button(
                onClick = {
                    Hold = 0
                    ScoreColourInt = Color.Transparent.toArgb()
                    ScoreColour = Color(ScoreColourInt)
                    IsClimbingEnabled = false
                    if (Score < 9) {
                        if (Score > 3) {
                            Score -= 3
                        } else {
                            Score = 0
                        }
                    }
                },
                modifier = Modifier
                    .padding(8.dp),
                enabled = IsFallingEnabled
            ) {
                Text(
                    modifier = modifier,
                    fontSize = 23.sp,
                    text = FallBtnText
                )
            }
            Button(
                onClick = {
                    Score = 0
                    Hold = 0
                    IsClimbingEnabled = true
                    IsFallingEnabled = false
                    ScoreColourInt = Color.Transparent.toArgb()
                    ScoreColour = Color(ScoreColourInt)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    modifier = modifier,
                    fontSize = 23.sp,
                    text = ResetBtnText
                )
            }
            Button(
                onClick = {
                    if(IsGermanEnabled == true)
                    {
                        IsGermanEnabled = false
                    } else {
                        IsGermanEnabled = true
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    modifier = modifier,
                    fontSize = 23.sp,
                    text = LanguageBtnText
                )
            }
            Row {
                Column(
                    modifier = modifier
                        .background(ScoreColour)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = ScoreBtnText
                    )
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = Score.toString(),
                    )
                }
                Column(
                    modifier = modifier
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = HoldBtnText
                    )
                    Text(
                        modifier = modifier,
                        fontSize = 23.sp,
                        text = Hold.toString(),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ObjectsPreview() {
    ClimbingAppTheme {
        Objects()
    }
}