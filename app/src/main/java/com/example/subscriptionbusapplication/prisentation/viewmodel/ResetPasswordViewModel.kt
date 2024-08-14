package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.subscriptionbusapplication.data.repository.UserManagement
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val userManagement: UserManagement
) : ViewModel() {


}