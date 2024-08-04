package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.ui.theme.appInfoColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appOnInfoColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.errorColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h2
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4
import com.example.subscriptionbusapplication.prisentation.ui.theme.onErrorColor
import java.lang.reflect.Modifier
import java.util.Locale


enum class AlertState {
    ALERT_ERROR,
    ALERT_SUCCESS,
    ALERT_INFO
}

@Composable
fun AppAlert(
    title: String,
    bodyText: String,
    state: AlertState,
    onDismiss: (() -> Unit)? = null,
    onConfirmState: (() -> Unit)? = null
) {

    val contentColor = when (state) {
        AlertState.ALERT_SUCCESS -> appPrimaryColor
        AlertState.ALERT_INFO -> appOnInfoColor
        else -> onErrorColor
    }

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = { /*TODO*/ },
        title = {
            Text(
                text = title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                style = h2
            )
        },
        text = {
            Text(
                text = bodyText, style = h4, textAlign = TextAlign.Center
            )
        },
        icon = {
            Icon(
                painter = painterResource(
                    id = when (state) {
                        AlertState.ALERT_SUCCESS -> R.drawable.baseline_check_circle_outline_24
                        AlertState.ALERT_INFO -> R.drawable.baseline_info_outline_24
                        else -> R.drawable.baseline_close_24
                    }
                ),
                contentDescription = "Alert Success Operation",
                tint = contentColor
            )
        },
        containerColor = when (state) {
            AlertState.ALERT_SUCCESS -> appSurfaceColor
            AlertState.ALERT_INFO -> appInfoColor
            else -> errorColor
        },
        titleContentColor = contentColor

    )

}


@Preview
@Composable
private fun AlertPrev() {

    Box(
        contentAlignment = Alignment.Center,
    ) {
        AppAlert(
            title = "simpleTitle",
            bodyText = "this is simple error body, given condition of error",
            state = AlertState.ALERT_SUCCESS
        )
    }


}