package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.ui.theme.h3
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.sp
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.bodyText

@Composable
fun ExpendableContent(
    title: String,
    textBody: String,
    modifier: Modifier = Modifier,
    currentState: Boolean,
    openCloseCallBack : (Boolean) -> Unit
) {

    // the expendable content is basically text expend
    // icon text
    // | text
    val localDensity = LocalDensity.current
    var hight_ by remember {
        mutableStateOf(0.dp)
    }
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(28.dp)
                    .clickable {
                        openCloseCallBack(!currentState)
                    }, contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = if (currentState) R.drawable.baseline_arrow_drop_up_24 else R.drawable.baseline_arrow_drop_down_24 ),
                    contentDescription = "open close text"
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, style = h3, color = appPrimaryColor.copy(alpha = 0.6f))
        }

        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(visible = currentState ) {
            

        Row(modifier = Modifier.padding(bottom = 20.dp)) {
            Box(Modifier.width(28.dp), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(hight_)
                        .fillMaxSize()
                        .background(Color.Black)
                )
            }
            Text(text = textBody,
                modifier = Modifier.onGloballyPositioned { coroutineContext ->
                   hight_ = with(localDensity) {coroutineContext.size.height.toDp()}
                },
                style = bodyText.copy(fontSize = 12.sp)
            )
        }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ExpendableContentPrev() {


    var currentState by
    remember {
        mutableStateOf(true)
    }

    ExpendableContent(
        title = "simple title",
        textBody = "this is text body hope we get this text good for you for multiline text we need to expend this text"
        , modifier = Modifier.fillMaxWidth(),
        currentState = currentState) {
        currentState = it
    }
}