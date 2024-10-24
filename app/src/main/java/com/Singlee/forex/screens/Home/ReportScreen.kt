package com.Singlee.forex.screens.Home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.R
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.screens.Home.ViewModels.SignalVideoModel
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.hintColor
import com.Singlee.forex.ui.theme.red
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen(navController: NavHostController, signalViewModel: SignalVideoModel) {
    LaunchedEffect(Unit) {
        signalViewModel.getActiveSignals(false)
    }

    var loading by remember { mutableStateOf(false) }
    val signalState by signalViewModel.signals.collectAsState(Response.Empty)
    var signals by remember { mutableStateOf(emptyList<SignalData>()) }
    var randerui by remember { mutableStateOf(false) }

    LaunchedEffect(signalState) {
        when (signalState) {
            is Response.Loading -> {
                loading = true
            }
            is Response.Error -> {
                loading = false
                Log.d("error", (signalState as Response.Error).message)
            }
            is Response.Success -> {
                randerui = true
                loading = false
                signals = (signalState as Response.Success<List<SignalData>>).data
            }
            Response.Empty -> {}
        }
    }

    Box (Modifier.fillMaxSize()) {
        if (randerui) {
            Column(
                Modifier
                    .padding(horizontal = 20.dp, vertical = 25.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                TodayprofiteLayut(signals.filter { it.hit_tp }.sumOf { it.pips.toDouble() })

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    tradesBox(
                        modifier = Modifier.weight(1f),
                        name = "Win",
                        R.drawable.win_icon,
                        signals.count { it.hit_tp },
                        false
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    tradesBox(
                        modifier = Modifier.weight(1f),
                        name = "Lose",
                        R.drawable.lose_icon,
                        signals.count { !it.hit_tp },
                        false
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    tradesBox(
                        modifier = Modifier.weight(1f),
                        name = "Win rate",
                        R.drawable.win_rate,
                        if (signals.isNotEmpty()) (signals.count { it.hit_tp } / signals.size) * 100 else 0,
                        true)
                    Spacer(modifier = Modifier.width(20.dp))
                    tradesBox(
                        modifier = Modifier.weight(1f),
                        name = "Total Trades",
                        R.drawable.all_trades_icon,
                        signals.count(),
                        false
                    )
                }

                // Group signals by date
                val groupedSignals = groupSignalsByDate(signals)

                Column {
                    groupedSignals.forEach { (dateLabel, signalsForDate) ->
                        // Display the date label only once
                        Text(
                            text = dateLabel,
                            style = MaterialTheme.typography.displayMedium,
                            color = hintColor,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )

                        // Display signals for this date
                        signalsForDate.forEach { signal ->
                            tradesDetail(signal)
                        }
                    }
                }
            }
        }
        if (loading) {
            CustomLoadingAnimation()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun groupSignalsByDate(signals: List<SignalData>): Map<String, List<SignalData>> {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return signals.groupBy { signal ->
        val signalDate = signal.timestamp?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
        when (signalDate) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> signalDate?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) ?: "Unknown Date"
        }
    }
}

@Composable
fun tradesDetail(data: SignalData) {
    val pipsValue = if (data.hit_tp) {
        if (data.pips.toFloat() < 0) data.pips.toFloat() * -1 else data.pips.toFloat()
    } else {
        if (data.pips.toFloat() > 0) data.pips.toFloat() * -1 else data.pips.toFloat()
    }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.buydot),
                modifier = Modifier.size(7.dp),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Text(
                text = data.cate,
                style = MaterialTheme.typography.displayMedium,
                color = hintColor,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
            Text(
                text = " ${data.type} ${data.entry}",
                style = MaterialTheme.typography.displayMedium,
                color = if (data.type == "Buy") blue else red,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Text(
                    text = Constant.TimestampToString(data.timestamp ?: com.google.firebase.Timestamp.now()),
                    style = MaterialTheme.typography.displayMedium,
                    color = hintColor,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = data.entry,
                style = MaterialTheme.typography.displayMedium,
                color = hintColor,
                modifier = Modifier.padding(end = 5.dp)
            )
            Image(
                painter = painterResource(R.drawable.back_icon),
                modifier = Modifier
                    .size(20.dp)
                    .rotate(180f),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Text(
                text = data.closing,
                style = MaterialTheme.typography.displayMedium,
                color = hintColor,
                modifier = Modifier.padding(start = 5.dp)
            )
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Text(
                    text = pipsValue.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    color = if (data.type == "Buy") blue else red,
                )
            }
        }

        Box(
            Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(extra_light)
        )
    }
}

@Composable
fun TodayprofiteLayut(data: Double) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .background(
                blue,
                RoundedCornerShape(20.dp)
            )
            .padding(10.dp)
            .height(50.dp)
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Weakly Profit",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        }
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$data.00",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
                Text(
                    text = "PIPS",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun tradesBox(modifier: Modifier, name: String, icon: Int, count: Int, is_winRate: Boolean) {
    Box(modifier = modifier
        .background(extra_light, RoundedCornerShape(20.dp))
        .padding(15.dp)
        .height(60.dp)) {
        Column(modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    Modifier.size(31.dp)
                )
                Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (is_winRate) "$count%" else count.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 28.sp,
                        color = Color.White,
                    )
                }
            }
        }

        Box(contentAlignment = Alignment.BottomStart, modifier = Modifier.fillMaxHeight()) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
        }
    }
}
