package net.trexis.shafikexcersie.commons

import android.app.Dialog
import android.content.Context
import android.view.Window
import net.trexis.shafikexcersie.R

class CustomLoadingDialog(context: Context) {
    private val pd: Dialog

    init {
        pd = Dialog(context)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.setCancelable(false)
        pd.setContentView(R.layout.dialog_loading)
    }

    fun show() {
        try {
            pd.show()
        } catch (e: Exception) {
        }
    }

    fun dismiss() {
        try {
            pd.dismiss()
        } catch (e: Exception) {
        }
    }
}