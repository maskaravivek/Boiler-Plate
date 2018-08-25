package com.maskaravivek.boilerplate.utils

import android.content.Context
import android.support.annotation.StringRes
import android.util.TypedValue
import android.widget.Toast

class ViewUtils {
    companion object {
        fun showLongToast(context: Context?, message: String) {
            if (context == null) {
                return
            }
            ExecutorUtils.uiExecutor().execute { Toast.makeText(context, message, Toast.LENGTH_LONG).show() }
        }

        fun showLongToast(context: Context?, @StringRes stringResId: Int) {
            if (context == null) {
                return
            }
            ExecutorUtils.uiExecutor().execute { Toast.makeText(context, context.getString(stringResId), Toast.LENGTH_LONG).show() }
        }

        fun showShortToast(context: Context?, message: String) {
            if (context == null) {
                return
            }
            ExecutorUtils.uiExecutor().execute { Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
        }

        fun showShortToast(context: Context?, @StringRes stringResId: Int) {
            if (context == null) {
                return
            }
            ExecutorUtils.uiExecutor().execute { Toast.makeText(context, context.getString(stringResId), Toast.LENGTH_SHORT).show() }
        }

        fun dpToPx(context: Context, dp: Int): Float {
            val displayMetrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)
        }

        fun spToPx(context: Context, sp: Int): Float {
            val displayMetrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), displayMetrics)
        }
    }
}