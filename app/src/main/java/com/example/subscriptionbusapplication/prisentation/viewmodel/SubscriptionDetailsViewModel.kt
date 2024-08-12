package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.static_component.SubscribeState
import com.example.subscriptionbusapplication.prisentation.ui.states.LoadCurrentSubscriptionState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel(assistedFactory = SubscriptionDetailsViewModel.Factory::class)
class SubscriptionDetailsViewModel @AssistedInject constructor(
    private val userManagement: UserManagement,
    @Assisted private val subscriptionDetailsId: Int,
    @Assisted val currentUserCanSubscribe: Boolean,
    private val sessionManagement: SessionManagement
) : ViewModel() {

    private val _loadCurrentSubscriptionState = mutableStateOf(LoadCurrentSubscriptionState())
    val loadCurrentSubscriptionState: State<LoadCurrentSubscriptionState> =
        _loadCurrentSubscriptionState

    private val _subscribeState = mutableStateOf(SubscribeState())
    val subscribeStat: State<SubscribeState> = _subscribeState


    @AssistedFactory
    interface Factory {
        fun create(
            subscriptionDetailsId: Int,
            currentUserCanSubscribe: Boolean
        ): SubscriptionDetailsViewModel
    }


    init {
        loadSubscription()
    }


    private fun loadSubscription() {

        userManagement.loadSubscription(sessionManagement.getToken(), subscriptionDetailsId)
            .onEach { appResponse ->

                when (appResponse) {
                    is AppResponse.Loading -> {
                        _loadCurrentSubscriptionState.value =
                            LoadCurrentSubscriptionState(isLoading = true)
                    }


                    is AppResponse.Success -> {
                        assert(appResponse.data != null)
                        _loadCurrentSubscriptionState.value =
                            LoadCurrentSubscriptionState(isSuccess = true, data = appResponse.data)
                    }

                    else -> {
                        _loadCurrentSubscriptionState.value = LoadCurrentSubscriptionState(
                            isError = true,
                            error = appResponse.message ?: "sorry, unexpected error just done"
                        )
                    }
                }


            }.launchIn(viewModelScope)
    }

    fun subscribe() {

        userManagement.subscribe(sessionManagement.getToken(), subscriptionDetailsId)
            .onEach { appResponse ->

                when (appResponse) {
                    is AppResponse.Loading -> {
                        _subscribeState.value =
                            SubscribeState(isLoading = true)
                    }

                    is AppResponse.Success -> {
                        assert(appResponse.data != null)
                        _subscribeState.value =
                            SubscribeState(isSuccess = true, data = appResponse.data)
                    }

                    else -> {
                        _subscribeState.value = SubscribeState(
                            isError = true,
                            errorMessage = appResponse.message
                                ?: "sorry, unexpected error just done"
                        )
                    }
                }

            }.launchIn(viewModelScope)
    }


    fun clearState() {
        _subscribeState.value = SubscribeState()
    }


}