package com.example.subscriptionbusapplication.prisentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.CurrentClientSate
import com.example.subscriptionbusapplication.prisentation.ui.states.LoadSubscriptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionManagement: SessionManagement,
    private val userManagement: UserManagement
) : ViewModel() {

    private val _currentClientSate = mutableStateOf(CurrentClientSate())
    val currentClientSate: State<CurrentClientSate> = _currentClientSate

    private val _loadSubscriptions = mutableStateOf(LoadSubscriptionState())
    val loadSubscriptionState: State<LoadSubscriptionState> = _loadSubscriptions

    private val _mapOfZones = mutableStateMapOf<String, MutableList<SubscriptionDetails>>()
    val mapOfZones: Map<String, MutableList<SubscriptionDetails>> = _mapOfZones

    private val _mapOfSubscriptionDetails =
        mutableStateMapOf<String, MutableList<SubscriptionDetails>>()
    val mapOfSubscriptionDetails: Map<String, MutableList<SubscriptionDetails>> =
        _mapOfSubscriptionDetails


    init {


        loadCurrentUser()
        loadSubscriptions()

    }


    private fun loadCurrentUser() {
        val token = sessionManagement.getToken()
        if (token.isEmpty()) {
            _currentClientSate.value = CurrentClientSate()
            return
        }
        userManagement.loadCurrentClient(token).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _currentClientSate.value = CurrentClientSate(isLoading = true)
                }

                is AppResponse.Success -> {
                    _currentClientSate.value =
                        CurrentClientSate(data = appResponse.data!!, isSuccess = true)
                }

                is AppResponse.Error -> {
                    _currentClientSate.value =
                        CurrentClientSate(isError = true, errorMessage = appResponse.message)
                }
            }
        }.launchIn(viewModelScope)

    }


    private fun loadSubscriptions() {
        val token = sessionManagement.getToken()
        if (token.isEmpty()) {
            _currentClientSate.value = CurrentClientSate()
            return
        }
        userManagement.loadSubscriptions(token).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _loadSubscriptions.value = LoadSubscriptionState(isLoading = true)
                }

                is AppResponse.Success -> {
                    assert(appResponse.data != null)
                    appResponse.data!!.forEach { subscriptionDetails ->
                        if (subscriptionDetails.zoneName in _mapOfZones.keys) {
                            _mapOfZones[subscriptionDetails.zoneName]!!.add(subscriptionDetails)
                        } else
                            _mapOfZones[subscriptionDetails.zoneName] =
                                mutableListOf(subscriptionDetails)
                    }

                    appResponse.data.forEach { subscriptionDetails ->

                        val years = subscriptionDetails.months / 12
                        val months = subscriptionDetails.months % 12
                        val key =
                            if (years >= 1) "$years Year${if (months > 0) ", $months Month" else ""}" else "$months Month"

                        if (key in _mapOfSubscriptionDetails.keys) {
                            mapOfSubscriptionDetails[key]!!.add(
                                subscriptionDetails
                            )
                        } else
                            _mapOfSubscriptionDetails[key] =
                                mutableListOf(subscriptionDetails)
                    }
                }

                is AppResponse.Error -> {
                    _loadSubscriptions.value =
                        LoadSubscriptionState(isError = true, errorMessage = appResponse.message)
                }
            }
        }.launchIn(viewModelScope)

    }

    fun eraseState() {
        _currentClientSate.value = CurrentClientSate()
    }


}