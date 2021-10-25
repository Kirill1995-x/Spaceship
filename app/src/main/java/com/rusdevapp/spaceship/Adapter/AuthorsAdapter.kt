package com.rusdevapp.spaceship.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.spaceship.Model.AuthorsModel
import com.rusdevapp.spaceship.R
import com.rusdevapp.spaceship.ViewHolder.AuthorsViewHolder

class AuthorsAdapter (private val arrayList: ArrayList<AuthorsModel>,
                      private val context: Context): RecyclerView.Adapter<AuthorsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_authors, parent, false)
        val viewHolder:AuthorsViewHolder = AuthorsViewHolder(view)
        val tvLink:TextView = view.findViewById(R.id.tvLink)
        tvLink.setOnClickListener(View.OnClickListener {
            val position: Int = viewHolder.adapterPosition
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(arrayList[position].link))
            context.startActivity(intent)
        })
        return viewHolder
    }

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount()=arrayList.size

}