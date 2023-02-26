package com.gph.tst.giphytestapp.domain.repository

import com.gph.tst.giphytestapp.common.Resource
import com.gph.tst.giphytestapp.domain.entity.UserProfile

interface ProfileRepository {

    suspend fun getUserProfile(): Resource<UserProfile, Exception>

    suspend fun saveUserProfile(profile: UserProfile): Resource<Unit, Exception>
}