package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.data.models.Day
import com.example.subscriptionbusapplication.data.models.Line
import com.example.subscriptionbusapplication.data.models.SubscriptionAllDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionX
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import java.time.LocalDate


@Composable
fun SubscriptionXDetailsView(subscriptionDetailsX: SubscriptionX) {
    Column(
        Modifier
            .fillMaxSize()
            .background(appSurfaceColor)
            .padding(horizontal = 16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Subscription Details",
                style = h2,
                color = appPrimaryColor.copy(alpha = 0.8f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.label),
                contentDescription = "label icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Subscription ${subscriptionDetailsX.subscriptionDetails.months} Months",
                style = h3
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.dinartn),
                contentDescription = "label icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Price ${subscriptionDetailsX.subscriptionDetails.price}dt", style = h3)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "label icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Zone ${subscriptionDetailsX.subscriptionDetails.zoneName}", style = h3)
        }
        Spacer(modifier = Modifier.height(8.dp))


        // those the days
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.time),
                contentDescription = "label icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Rest ${
                    subscriptionDetailsX.to.toEpochDay() - LocalDate.now().toEpochDay()
                } day", style = h3
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.bus),
                contentDescription = "label icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Lines", style = h3)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (subscriptionDetailsX.subscriptionDetails.lines.isNotEmpty())
                subscriptionDetailsX.subscriptionDetails.lines.forEach {
                    LineRepresentation(label = it.label)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            else
                Text(
                    text = "No Lines Available For Now",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
        }
        Spacer(modifier = Modifier.height(8.dp))


        // those the days
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "label icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Days", style = h3)
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            subscriptionDetailsX.subscriptionDetails.days.forEach {
                DayRepresentation(label = it.shortName, enabled = it.isAvailableRightNow == 1)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun SubscriptionXDetailsViewPrev() {

    val lines = listOf(
        Line(id = 1, label = "77"),
        Line(id = 1, label = "44"),
        Line(id = 1, label = "44a"),
        Line(id = 1, label = "77k"),
    )


    val days = listOf(
        Day(
            name = "Monday",
            shortName = "Mon",
            id = 2,
            frenchName = "Laundi",
            frenchShortName = "Lun",
            isAvailableRightNow = 0
        ),
        Day(
            name = "Tuesday",
            shortName = "Tu",
            id = 2,
            frenchName = "Mardi",
            frenchShortName = "Mar",
            isAvailableRightNow = 1
        ),
        Day(
            name = "Wednesday",
            shortName = "Wen",
            id = 2,
            frenchName = "Mercredi",
            frenchShortName = "Mer",
            isAvailableRightNow = 1
        ),
        Day(
            name = "Thursday",
            shortName = "Thu",
            id = 2,
            frenchName = "Jeudi",
            frenchShortName = "Jeu",
            isAvailableRightNow = 1
        ),
        Day(
            name = "Friday",
            shortName = "Fri",
            id = 2,
            frenchName = "Vendredi",
            frenchShortName = "Ven",
            isAvailableRightNow = 1
        ),
        Day(
            name = "Saturday",
            shortName = "Sat",
            id = 2,
            frenchName = "Samedi",
            frenchShortName = "Sam",
            isAvailableRightNow = 1
        ),
        Day(
            name = "Sunday",
            shortName = "Sun",
            id = 2,
            frenchName = "Dimanche",
            frenchShortName = "Dim",
            isAvailableRightNow = 1
        ),
    )

    val subscriptionX = SubscriptionX(
        from = LocalDate.now(),
        to = LocalDate.now().plusMonths(3),
        id = 1,
        subscriptionDetails = SubscriptionAllDetails(
            days = days,
            lines = lines,
            price = 400.0,
            months = 3,
            zoneName = "Charguia",
            label = "subscription 3 Months",
            labelFrench = "abonnnement 3 Moins",
            id = 1
        )
    )


    SubscriptionXDetailsView(subscriptionDetailsX = subscriptionX)

}