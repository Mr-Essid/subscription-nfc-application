package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import java.util.Locale

@Composable
fun SubscriptionDetailsCard(
    subscriptionDetails: SubscriptionDetails,
    modifier: Modifier = Modifier,
    onclick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = appPrimaryColor.copy(0.2f)),
        onClick = onclick
    ) {
        Column(
            Modifier
                .width(180.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.label), contentDescription = "icon")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${subscriptionDetails.months} Month Subscription".capitalize(Locale.ROOT),
                    style = h4
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "icon"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Zone ${subscriptionDetails.zoneName}".capitalize(Locale.ROOT),
                    style = h4
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dinartn),
                    contentDescription = "icon"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${subscriptionDetails.price}dt", style = h4)
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                    contentDescription = "icon"
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun SubscriptionDetailsCardPrev() {

    val subscriptionDetails = SubscriptionDetails(
        labelFrench = "label",
        label = "1 month subscription",
        months = 1,
        price = 50.4,
        zoneName = "charguia",
        id = 1
    )

    SubscriptionDetailsCard(subscriptionDetails = subscriptionDetails) {
        
    }
    Column(Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
    }
}