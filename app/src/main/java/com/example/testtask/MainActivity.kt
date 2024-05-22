package com.example.testtask

import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.Parcelable
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {



    private val imageSearchBackend = ImageSearchBackend()
    private val imageAdapter = ImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = imageAdapter

        // Обрабатываем нажатие на кнопку поиска
        // Обрабатываем отправку запроса поиска
        findViewById<SearchView>(R.id.search_view).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    val imageList = query?.let { imageSearchBackend.searchImages(it) }
                    withContext(Dispatchers.Main) {
                        imageAdapter.submitList(imageList)
                    }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}