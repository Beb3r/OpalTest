package com.gb.opaltest.features.gems.presentation.mappers

import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_B1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_C1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_D1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_E1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_F1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_G1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_H1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_J1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_K1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_L1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_M1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_N1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_O1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_P1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_Q1
import com.gb.opaltest.features.gems.presentation.models.GemUiModel
import com.gb.opaltest.core.design.R.drawable as drawables

fun GemDomainModel.toGemUiModel(): GemUiModel {
    val drawableResId = when (this.id) {
        GEM_ID_B1 -> drawables.gem_b1
        GEM_ID_C1 -> drawables.gem_c1
        GEM_ID_D1 -> drawables.gem_d1
        GEM_ID_E1 -> drawables.gem_e1
        GEM_ID_F1 -> drawables.gem_f1
        GEM_ID_G1 -> drawables.gem_g1
        GEM_ID_H1 -> drawables.gem_h1
        GEM_ID_J1 -> drawables.gem_j1
        GEM_ID_K1 -> drawables.gem_k1
        GEM_ID_L1 -> drawables.gem_l1
        GEM_ID_M1 -> drawables.gem_m1
        GEM_ID_N1 -> drawables.gem_n1
        GEM_ID_O1 -> drawables.gem_o1
        GEM_ID_P1 -> drawables.gem_p1
        GEM_ID_Q1 -> drawables.gem_q1
        else -> drawables.gem_a1

    }
    return GemUiModel(
        id = this.id,
        drawableResId = drawableResId,
    )
}