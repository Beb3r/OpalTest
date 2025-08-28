package com.gb.opaltest.features.referral.domain.models

import com.gb.opaltest.core.common.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ReferredUserDomainModel(
    val id: String,
    val name: String,
    @Serializable(with = DateSerializer::class)
    val date: Date,
)
