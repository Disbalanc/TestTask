package com.example.testtask

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class FullScreenImageActivity : AppCompatActivity() {
    private lateinit var imageList: ArrayList<ImageSearchBackend.Image>
    private var currentImageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_screen_image)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Загружаем изображение в ImageView
        val imageView = findViewById<ImageView>(R.id.image_view)

        // Get the image data list and current image index from the intent
        @Suppress("DEPRECATION")
        imageList = intent.getSerializableExtra("imageData") as ArrayList<ImageSearchBackend.Image>
        currentImageIndex = intent.getIntExtra("currentImageIndex", 0)

        Glide.with(this).load(imageList[currentImageIndex].thumbnailUrl).into(imageView)

        // Инициализируем элементы управления
        val previousButton = findViewById<ImageButton>(R.id.previous_button)
        val nextButton = findViewById<ImageButton>(R.id.next_button)
        val openWebsiteButton = findViewById<Button>(R.id.open_website_button)
        val openBackButton = findViewById<Button>(R.id.open_back_activity_button)

        // Обрабатываем нажатия на кнопки навигации
        previousButton.setOnClickListener {
            if (currentImageIndex > 0) {
                currentImageIndex--
                Glide.with(this).load(imageList[currentImageIndex].thumbnailUrl).into(imageView)

            }
        }

        nextButton.setOnClickListener {
            if (currentImageIndex < imageList.size - 1) {
                currentImageIndex++
                Glide.with(this).load(imageList[currentImageIndex].thumbnailUrl).into(imageView)
            }
        }

        // Обрабатываем нажатие на кнопку открытия исходной страницы
        openWebsiteButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imageList.get(currentImageIndex).googleUrl))
            startActivity(intent)
        }

        // Обрабатываем нажатие на кнопку открытия исходной страницы
        openBackButton.setOnClickListener {
            val intent = Intent(this@FullScreenImageActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}