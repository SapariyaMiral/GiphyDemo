package com.giphyapp.utils

import android.content.Context
import android.content.DialogInterface
import com.giphyapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object DialogUtils {


    fun showMaterialAlertDialog(
        context: Context,
        title: String? = null,
        message: String = context.getString(R.string.lbl_something_went_wrong),
        positiveText: String = context.getString(R.string.btn_text_yes),
        negativeText: String = context.getString(R.string.btn_text_no),
        callback: DialogInterface.OnClickListener? = null,
        isCancelable: Boolean = false
    ) {

        val materialAlertDialog = MaterialAlertDialogBuilder(context)

        if (title != null) {
            materialAlertDialog.setTitle(title)
        }

        materialAlertDialog.setMessage(message)
        materialAlertDialog.setCancelable(isCancelable)

        materialAlertDialog.setNegativeButton(negativeText) { dialog, which ->
            // Respond to neutral button press
            callback?.onClick(dialog, DialogInterface.BUTTON_NEGATIVE)
        }

        materialAlertDialog.setPositiveButton(positiveText) { dialog, which ->
            // Respond to positive button press
            callback?.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
        }
        materialAlertDialog.show()
    }
}