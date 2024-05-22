package com.example.testtask

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Адаптер для списка изображений
class ImageAdapter : ListAdapter<ImageSearchBackend.Image, ImageAdapter.ImageViewHolder>(
    ImageDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)

        // Загрузка изображения с помощью библиотеки Glide
        Glide.with(holder.itemView)
            .load(image.thumbnailUrl)
            .into(holder.imageView)

        holder.sourceTextView.text = image.sourceUrl

        // Установка обработчика нажатия для изображения
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, FullScreenImageActivity::class.java)
            intent.putParcelableArrayListExtra("imageData", ArrayList(currentList))
            intent.putExtra("currentImageIndex", position)
            holder.itemView.context.startActivity(intent) // Запускаем новую Activity
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                /*if (lastVisibleItemPosition >= totalItemCount - 1) {
                    // Загрузка дополнительных изображений
                }*/
            }
        })
    }

    // Внутренний класс для хранения элементов списка
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val sourceTextView: TextView = view.findViewById(R.id.sourceTextView)
    }
}