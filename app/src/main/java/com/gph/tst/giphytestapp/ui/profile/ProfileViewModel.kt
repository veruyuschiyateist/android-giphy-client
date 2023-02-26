package com.gph.tst.giphytestapp.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gph.tst.giphytestapp.common.Resource
import com.gph.tst.giphytestapp.domain.entity.UserProfile
import com.gph.tst.giphytestapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val resource = profileRepository.getUserProfile()
            if (resource is Resource.Success) {
                Log.d(TAG, ": ${resource.success}")
            } else if (resource is Resource.Failure) {
                Log.d(TAG, ": ${resource.cause}")
            }
        }
    }

    fun onSave(name: String, email: String) {
        viewModelScope.launch {
            profileRepository.saveUserProfile(
                profile = UserProfile(
                    name = "Name",
                    surname = "Surname",
                    email = "Email",
                    phone = "Phone"
                )
            )
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}