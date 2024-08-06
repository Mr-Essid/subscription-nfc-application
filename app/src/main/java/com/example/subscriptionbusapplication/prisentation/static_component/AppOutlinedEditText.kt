package com.example.subscriptionbusapplication.prisentation.static_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subscriptionbusapplication.R
import com.example.subscriptionbusapplication.prisentation.ui.theme.appPrimaryColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.appSurfaceColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.errorColor
import com.example.subscriptionbusapplication.prisentation.ui.theme.h4


@Composable
fun AppOutlinedEditText(
    modifier: Modifier = Modifier,
    value: String, onChange: (value: String) -> Unit,
    placeholder: String,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions? = null
) {

    var focused by remember {
        mutableStateOf(false)
    }




    OutlinedTextField(
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
        value = value,
        onValueChange = onChange,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = appPrimaryColor.copy(alpha = 0.5f),
                fontSize = 18.sp
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = appPrimaryColor,
            unfocusedBorderColor = appPrimaryColor.copy(alpha = 0.7f),
            unfocusedTextColor = appPrimaryColor,
            focusedTextColor = appPrimaryColor,
            errorTextColor = errorColor,
            errorPlaceholderColor = Color.Red
        ),
    )
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            style = h4.copy(color = errorColor),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AppOutlinedPassword(
    modifier: Modifier = Modifier,
    value: String, onChange: (value: String) -> Unit,
    placeholder: String,
    errorMessage: String? = null
) {

    var isShowPassword by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        modifier = modifier

            .fillMaxWidth(),
        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = appPrimaryColor.copy(alpha = 0.5f),
                fontSize = 18.sp
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = appPrimaryColor,
            unfocusedBorderColor = appPrimaryColor.copy(alpha = 0.7f),
            unfocusedTextColor = appPrimaryColor,
            focusedTextColor = appPrimaryColor,
            errorTextColor = errorColor,
            errorPlaceholderColor = Color.Red
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = if (isShowPassword) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                contentDescription = "visibility of system",
                modifier.clickable { isShowPassword = !isShowPassword },
                tint = appPrimaryColor
            )
        },
        isError = errorMessage != null
    )
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            style = h4.copy(color = errorColor),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun AppOutlinedEditTextPV() {

    var string by remember {
        mutableStateOf("")
    }

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        AppOutlinedEditText(value = string, onChange = { string = it }, placeholder = "Email")
        AppOutlinedPassword(
            value = string,
            onChange = { string = it },
            placeholder = "password",
            errorMessage = "This is Error"
        )

    }

}