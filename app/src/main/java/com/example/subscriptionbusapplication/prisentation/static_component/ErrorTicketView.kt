package com.example.subscriptionbusapplication.prisentation.static_component


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.ui.theme.appInfoColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appOnInfoColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.errorColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2


@Composable
fun ErrorTicketView(modifier: Modifier = Modifier, message: String, onClick: () -> Unit) {

    OutlinedCard(

        onClick = onClick,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp
        )
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_info_outline_24),
                    contentDescription = "Error Has Occur",
                    tint = errorColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Error: $message",
                    style = h2.copy(fontSize = 16.sp),
                    color = Color.Red.copy(alpha = 0.5f)
                )
            }
        }
    }

}


@Composable
fun InfoTicketView(modifier: Modifier = Modifier, message: String, onClick: () -> Unit) {

    OutlinedCard(

        onClick = onClick,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp
        )
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_info_outline_24),
                    contentDescription = "Info",
                    tint = appInfoColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Info: $message",
                    style = h2.copy(fontSize = 16.sp),
                    color = appOnInfoColor
                )
            }
        }
    }

}
@Preview(showSystemUi = true)
@Composable
private fun ErrorTicketViewPrev() {
    var visibilty by remember {
        mutableStateOf(true)
    }





    Column {


        Button(onClick = {
            visibilty = !visibilty
        }) {
            Text(text = if (visibilty) "hide" else "show")
        }
        AnimatedVisibility(
            visible = visibilty,
            enter = slideInHorizontally { -it },
            exit = slideOutHorizontally()
        ) {


            ErrorTicketView(message = "message") {
                visibilty = !visibilty
            }


            InfoTicketView(message = "Helloworld") {
                
            }

        }
    }
}