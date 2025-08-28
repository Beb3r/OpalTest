package com.gb.opaltest.core.translations

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface TextUiModel {
    @Immutable
    data class Plain(val text: String) : TextUiModel

    @Immutable
    data class StringRes(@androidx.annotation.StringRes val resId: Int, val formatArgs: Array<Any> = arrayOf()) :
        TextUiModel {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as TextUiModel.StringRes

            if (resId != other.resId) return false
            if (!formatArgs.contentEquals(other.formatArgs)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + formatArgs.contentHashCode()
            return result
        }
    }

    @Immutable
    data class PluralRes(
        @PluralsRes val resId: Int,
        val quantity: Int,
        val formatArgs: Array<Any> = arrayOf()
    ) : TextUiModel {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PluralRes

            if (resId != other.resId) return false
            if (quantity != other.quantity) return false
            if (!formatArgs.contentEquals(other.formatArgs)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + quantity
            result = 31 * result + formatArgs.contentHashCode()
            return result
        }
    }
}

fun TextUiModel.toValue(
    context: Context
): String =
    when (this) {
        is TextUiModel.Plain -> this.text
        is TextUiModel.StringRes -> {
            if (this.formatArgs.isEmpty()) {
                context.getString(this.resId)
            } else {
                context.getString(this.resId, *this.formatArgs)
            }
        }

        is TextUiModel.PluralRes -> {
            if (this.formatArgs.isEmpty()) {
                context.resources.getQuantityString(this.resId, this.quantity)
            } else {
                context.resources.getQuantityString(this.resId, this.quantity, *this.formatArgs)
            }
        }
    }
