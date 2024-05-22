package com.example.testtask

import androidx.recyclerview.widget.DiffUtil

// Класс для сравнения элементов списка изображений
object ImageDiffCallback : DiffUtil.ItemCallback<ImageSearchBackend.Image>() {

    override fun areItemsTheSame(oldItem: ImageSearchBackend.Image, newItem: ImageSearchBackend.Image): Boolean {
        return oldItem.position == newItem.position
    }

    override fun areContentsTheSame(oldItem: ImageSearchBackend.Image, newItem: ImageSearchBackend.Image): Boolean {
        return oldItem == newItem
    }
}