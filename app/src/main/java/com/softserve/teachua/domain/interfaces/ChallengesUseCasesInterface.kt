package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.ChallengeModel

interface ChallengesUseCasesInterface {

    suspend fun getChallenges(): Resource<List<ChallengeModel>>

    suspend fun getChallenge(id: Int): Resource<ChallengeModel>

}