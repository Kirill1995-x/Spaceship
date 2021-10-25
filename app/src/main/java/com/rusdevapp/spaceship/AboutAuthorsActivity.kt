package com.rusdevapp.spaceship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rusdevapp.spaceship.Adapter.AuthorsAdapter
import com.rusdevapp.spaceship.Model.AuthorsModel
import com.rusdevapp.spaceship.databinding.ActivityAboutAuthorsBinding

class AboutAuthorsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutAuthorsBinding
    private var arrayList:ArrayList<AuthorsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAuthorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //---
        arrayList.add(AuthorsModel("Crateboy",
                                   "https://crateboy.itch.io/crateboy-2007-2014"))
        arrayList.add(AuthorsModel("MillionthVector",
                                   "https://millionthvector.blogspot.com/"))
        arrayList.add(AuthorsModel("Robert Brooks",
                                   "https://www.gamedeveloperstudio.com/license.php"))
        arrayList.add(AuthorsModel("Fontfabric",
                                   "https://www.fontfabric.com/fonts/gagalin/"))
        binding.rvAboutAuthors.layoutManager=LinearLayoutManager(this)
        binding.rvAboutAuthors.adapter = AuthorsAdapter(arrayList, context = this)

    }
}