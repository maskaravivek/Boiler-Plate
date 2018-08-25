package com.maskaravivek.boilerplate.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class ExecutorUtils {
    companion object {
        private val uiExecutor = Executor { command ->
            if (Looper.myLooper() == Looper.getMainLooper()) {
                command.run()
            } else {
                Handler(Looper.getMainLooper()).post(command)
            }
        }

        fun uiExecutor(): Executor {
            return uiExecutor
        }
    }
}
