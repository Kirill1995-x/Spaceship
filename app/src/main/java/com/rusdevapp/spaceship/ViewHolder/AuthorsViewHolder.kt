package com.rusdevapp.spaceship.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.spaceship.Model.AuthorsModel
import com.rusdevapp.spaceship.R

class AuthorsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(model: AuthorsModel)
    {
        var tvName: TextView = itemView.findViewById(R.id.tvAuthors)
        tvName.text=model.name
    }

}