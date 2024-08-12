package com.example.subscriptionbusapplication.prisentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.SessionManagement
import com.example.subscriptionbusapplication.data.models.SubscriptionDetails
import com.example.subscriptionbusapplication.data.models.SubscriptionX
import com.example.subscriptionbusapplication.data.repository.UserManagement
import com.example.subscriptionbusapplication.prisentation.ui.states.ClientState
import com.example.subscriptionbusapplication.prisentation.ui.states.CurrentClientLoadState
import com.example.subscriptionbusapplication.prisentation.ui.states.LoadSubscriptionState
import com.example.subscriptionbusapplication.prisentation.ui.states.fromUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionManagement: SessionManagement,
    private val userManagement: UserManagement
) : ViewModel() {

    private val TAG = "DashboardViewModel"

    // user related data
    private val _clientState = mutableStateOf<ClientState?>(null)
    val clientSate: State<ClientState?> = _clientState

    private val _currentListOfSubscriptions = mutableStateListOf<SubscriptionX>()
    val currentListOfSubscriptions: List<SubscriptionX> = _currentListOfSubscriptions


    private val _currentClientLoadState = mutableStateOf(CurrentClientLoadState())
    val currentClientLoadState: State<CurrentClientLoadState> = _currentClientLoadState
    //end of user related data

    // subscriptions related data
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
            _currentClientLoadState.value = CurrentClientLoadState(
                isError = true,
                errorMessage = "token expired, refresh your login"
            )
            return
        }
        userManagement.loadCurrentClient(token).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _currentClientLoadState.value = CurrentClientLoadState(isLoading = true)
                }

                is AppResponse.Success -> {

                    _clientState.value = ClientState.fromUserModel(appResponse.data!!)
                    _currentClientLoadState.value = CurrentClientLoadState()
                    _currentListOfSubscriptions.addAll(appResponse.data.subscriptions)
                }

                is AppResponse.Error -> {
                    _currentClientLoadState.value =
                        CurrentClientLoadState(isError = true, errorMessage = appResponse.message)
                }
            }
        }.launchIn(viewModelScope)

    }


    private fun loadSubscriptions() {
        val token = sessionManagement.getToken()
        if (token.isEmpty()) {
            _currentClientLoadState.value = CurrentClientLoadState(
                isError = true,
                errorMessage = "token expired, refresh your login"
            )
            return
        }
        userManagement.loadSubscriptions(token).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                    _loadSubscriptions.value = LoadSubscriptionState(isLoading = true)
                }

                is AppResponse.Success -> {

                    _loadSubscriptions.value = LoadSubscriptionState()
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

    fun loadSubscriptionXById(subscriptionXId: Int) {
        val token = sessionManagement.getToken()
        if (token.isEmpty()) {
            _currentClientLoadState.value = CurrentClientLoadState(
                isError = true,
                errorMessage = "token expired, refresh your login"
            )
            return
        }
        userManagement.getSubscriptionXDetails(token, subscriptionXId).onEach { appResponse ->
            when (appResponse) {
                is AppResponse.Loading -> {
                }

                is AppResponse.Success -> {
                    assert(appResponse.data != null)
                    _currentListOfSubscriptions.add(appResponse.data!!)
                    Log.d(TAG, "loadSubscriptionXById: ${_currentClientLoadState.value}")
                }

                is AppResponse.Error -> {
                }
            }
        }.launchIn(viewModelScope)

    }


    fun updateCurrentClientWallet(newWalletValue: Double) {
        // there is no case of update current user and the user does not exists
        _clientState.value = clientSate.value!!.copy(wallet = newWalletValue)
    }


    fun eraseState() {
        _currentClientLoadState.value = CurrentClientLoadState()
    }


}