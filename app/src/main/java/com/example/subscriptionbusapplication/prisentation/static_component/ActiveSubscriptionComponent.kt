package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionX
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ActiveSubscription(
    subscriptionX: SubscriptionX,
    modifier: Modifier = Modifier
) {

    OutlinedCard(
        onClick = { /*TODO*/ },
    ) {

        Column(
            Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mapicon),
                        contentDescription = "map icon",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = subscriptionX.subscriptionDetails.zoneName.capitalize(Locale.ROOT),
                        style = h3
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "map icon",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${
                            subscriptionX.to.toEpochDay().minus(LocalDate.now().toEpochDay())
                        } Day", style = h3
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.monthsiconv2),
                        contentDescription = "map icon",
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${subscriptionX.subscriptionDetails.months} Mth", style = h3)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "from ${subscriptionX.from.format(DateTimeFormatter.ofPattern("dd MMM yy"))}",
                style = h4.copy(color = appPrimaryColor.copy(alpha = 0.7f))
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ActiveSubscriptionPrev() {
    val subscriptionX = SubscriptionX(
        from = LocalDate.now(),
        to = LocalDate.now().plusDays(20),
        id = 10,
        subscriptionDetails = SubscriptionDetails(
            id = 2,
            labelFrench = "simple label",
            label = "label",
            months = 3,
            price = 20.0,
            zoneName = "charguia"
        )
    )

    ActiveSubscription(subscriptionX = subscriptionX)

}