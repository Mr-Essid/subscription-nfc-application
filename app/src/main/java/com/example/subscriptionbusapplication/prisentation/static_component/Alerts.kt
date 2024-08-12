package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AlertConfirmationApp(
    title: String,
    textBody: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {


}


@Preview(showSystemUi = true)
@Composable
private fun AlertDialogPrev() {


    AlertConfirmationApp(
        title = "Transaction Confirmation",
        textBody = "You may went to review your cha",
        onDismiss = {} ) {

    }
}