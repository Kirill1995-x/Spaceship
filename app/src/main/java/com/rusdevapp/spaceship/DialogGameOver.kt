package com.rusdevapp.spaceship

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class DialogGameOver: AppCompatDialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.title_game_over)
               .setPositiveButton(R.string.button_ok, DialogInterface.OnClickListener
                    {dialog, which -> dialog.dismiss()
                                      activity?.finish()})
        return builder.create()
    }
}