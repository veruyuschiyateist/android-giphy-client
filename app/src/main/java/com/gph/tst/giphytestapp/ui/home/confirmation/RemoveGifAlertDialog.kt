package com.gph.tst.giphytestapp.ui.home.confirmation

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gph.tst.giphytestapp.R

class RemoveGifAlertDialog(
    private val listener: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.confirmation_question)
                .setPositiveButton(
                    R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.invoke()
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}