package com.rusdevapp.spaceship

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class DialogInstruction: AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.title_instruction)
            .setMessage(R.string.message_instruction)
            .setPositiveButton(R.string.button_ok, DialogInterface.OnClickListener
                                { dialog, which ->  dialog.dismiss()})

        return super.onCreateDialog(savedInstanceState)
    }
}