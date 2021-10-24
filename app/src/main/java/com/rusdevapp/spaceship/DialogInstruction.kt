package com.rusdevapp.spaceship

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class DialogInstruction: AppCompatDialogFragment(), View.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View =requireActivity().layoutInflater
                        .inflate(R.layout.dialog_instruction,null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        view.findViewById<Button>(R.id.btnCloseDialog).setOnClickListener(this)
        return builder.create()
    }

    override fun onClick(v: View) {
        dialog?.dismiss()
    }
}