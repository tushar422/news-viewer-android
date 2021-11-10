package com.dizziness.minimalnewsviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val fruit = intent.getStringExtra("FRUIT")
        val textView= findViewById<TextView>(R.id.fruitResultText)
        textView.text = "You just clicked a $fruit"

    }
}