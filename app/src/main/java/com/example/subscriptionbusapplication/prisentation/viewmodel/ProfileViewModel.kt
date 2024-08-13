package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.ClientState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    private val userManagement: UserManagement,
    private val sessionManagement: SessionManagement,
    @Assisted val clientState: ClientState
) : ViewModel() {


    @AssistedFactory
    interface Factory {
        fun create(clientState: ClientState): ProfileViewModel
    }

    fun disconnect() {
    }
}