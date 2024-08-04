package com.example.subscriptionbusapplication.prisentation.viewmodel

import android.util.Log
import android.util.Printer
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subscriptionbusapplication.AppResponse
import com.example.subscriptionbusapplication.data.models.ImageResolverModel
import com.example.subscriptionbusapplication.data.repositoryImp.UserManagementRepositoryImp
import com.example.subscriptionbusapplication.domain.shared_usecase.RegisterUseCase
import com.example.subscriptionbusapplication.fromStatusCodeToMessage
import com.example.subscriptionbusapplication.prisentation.ui.DataFlowRapper
import com.example.subscriptionbusapplication.prisentation.ui.states.ImageResolveState
import com.example.subscriptionbusapplication.prisentation.ui.states.RegisterResourcesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpLastStepViewModel @Inject constructor(
    private val userManagementRepositoryImp: UserManagementRepositoryImp,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {


    final val tag = "SignUpLastStepViewModel"
    private val _imageResolveState = mutableStateOf(ImageResolveState())
    val imageResolveState: State<ImageResolveState> = _imageResolveState



    private val _registerState = mutableStateOf(RegisterResourcesState())
    var registerResourcesState: State<RegisterResourcesState> = _registerState

    fun resolveImage(requestBody: MultipartBody.Part) {

        userManagementRepositoryImp.imageResolve(requestBody).onEach { appResponse ->
            when (appResponse) {

                is AppResponse.Loading -> {
                    _imageResolveState.value = ImageResolveState(isLoading = true)
                }

                is AppResponse.Success -> {
                    _imageResolveState.value = ImageResolveState(data = appResponse.data)
                }

                is AppResponse.Error -> {
                    _imageResolveState.value = ImageResolveState(
                        errorMessage = when (appResponse.code) {
                            -1 -> "network error, try again".capitalize(Locale.current)
                            -2 -> "server refuse, try again".capitalize(Locale.current)
                            400 -> "image not clear".capitalize(Locale.current)
                            else -> "unexpected error, try again".capitalize(Locale.current)
                        }
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun register(
        dataFlowRapper: DataFlowRapper,
        multipartBody: MultipartBody.Part
    ) {
        registerUseCase(
            dataFlowRapper,
            multipartBody
        ).onEach { appResponse ->
            when(appResponse) {
                is AppResponse.Loading -> _registerState.value = RegisterResourcesState(isLoading = true)
                is AppResponse.Success -> _registerState.value = RegisterResourcesState(data = appResponse.data)
                is AppResponse.Error -> _registerState.value = RegisterResourcesState(errorMessage = appResponse.message)
            }

        }.launchIn(viewModelScope)
    }

    fun resetState() {
        _imageResolveState.value = ImageResolveState()
    }

}