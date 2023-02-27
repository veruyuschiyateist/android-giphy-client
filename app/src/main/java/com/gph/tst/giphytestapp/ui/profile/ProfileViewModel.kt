package com.gph.tst.giphytestapp.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gph.tst.giphytestapp.common.Resource
import com.gph.tst.giphytestapp.domain.entity.UserProfile
import com.gph.tst.giphytestapp.domain.repository.AuthRepository
import com.gph.tst.giphytestapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState = _profileUiState.asStateFlow()

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            val resource = profileRepository.getUserProfile()
            if (resource is Resource.Success) {
                _profileUiState.value = ProfileUiState.Loaded(userProfile = resource.success)
            } else if (resource is Resource.Failure) {
                _profileUiState.value = ProfileUiState.Failure("Try again!")
            }
        }
    }

    fun onSave(name: String, surname: String, phone: String) {
        viewModelScope.launch {
            profileRepository.saveUserProfile(
                profile = UserProfile(
                    name = name,
                    surname = surname,
                    phone = phone
                )
            )
        }
        init()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    sealed interface ProfileUiState {
        object Loading : ProfileUiState
        data class Loaded(val userProfile: UserProfile) : ProfileUiState
        data class Failure(val message: String) : ProfileUiState
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}