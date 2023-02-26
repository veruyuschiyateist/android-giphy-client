package com.gph.tst.giphytestapp.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gph.tst.giphytestapp.common.onResult
import com.gph.tst.giphytestapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val signUpUiState = _signUpUiState.asStateFlow()

    fun onContinue(email: String, password: String) {
        _signUpUiState.value = SignUpUiState.Loading
        viewModelScope.launch {
            authRepository.signUp(email, password)
                .onResult(
                    onSuccess = {
                        _signUpUiState.value = SignUpUiState.Success
                    },
                    onFailure = {
                        _signUpUiState.value = SignUpUiState.Error("Try again!")
                    }
                )
        }
    }

    sealed interface SignUpUiState {
        object Idle : SignUpUiState
        object Loading : SignUpUiState
        data class Error(val message: String) : SignUpUiState

        object Success : SignUpUiState
    }
}