package com.google.ar.core.examples.java.common.helpers

import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.ar.core.examples.java.helloar.R

class SnackbarHelper {
    private var messageSnackbar: Snackbar? = null
    val isShowing: Boolean
        get() = messageSnackbar != null

    fun showMessage(activity: Activity, message: String) {
        show(activity, message,  /*finishOnDismiss=*/false)
    }

    fun showError(activity: Activity, errorMessage: String) {
        show(activity, errorMessage,  /*finishOnDismiss=*/true)
    }

    fun hide(activity: Activity) {
        activity.runOnUiThread {
            if (messageSnackbar != null) {
                messageSnackbar!!.dismiss()
            }
            messageSnackbar = null
        }
    }

    private fun show(activity: Activity, message: String, finishOnDismiss: Boolean) {
        activity.runOnUiThread {
            messageSnackbar = Snackbar.make(
                activity.findViewById(R.id.content),
                message,
                Snackbar.LENGTH_INDEFINITE
            )
            messageSnackbar!!.view.setBackgroundColor(BACKGROUND_COLOR)
            if (finishOnDismiss) {
                messageSnackbar!!.setAction(
                    "Dismiss"
                ) { messageSnackbar!!.dismiss() }
                messageSnackbar!!.addCallback(
                    object : BaseCallback<Snackbar?>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            activity.finish()
                        }
                    })
            }
            messageSnackbar!!.show()
        }
    }

    companion object {
        private const val BACKGROUND_COLOR = -0x40cdcdce
    }
}