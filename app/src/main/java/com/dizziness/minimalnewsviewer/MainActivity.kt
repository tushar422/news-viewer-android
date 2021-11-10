package com.dizziness.minimalnewsviewer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.dizziness.minimalnewsviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ClickListener {

    private lateinit var rvAdapter: NewsAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetch()
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        rvAdapter = NewsAdapter(this)
        binding.recyclerView.adapter= rvAdapter
    }
    private fun fetch(){
        val url ="https://newsapi.org/v2/top-headlines?country=in&apiKey=935df08d99404328bcc38a3c4c8795dd"
        val parentJsonReq = object: JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val fetchedNewsArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until fetchedNewsArray.length()){
                    val newsJson = fetchedNewsArray.getJSONObject(i)
                    val currentNews = News(
                        newsJson.getString("title"),
                        if(newsJson.getString("author")=="null"){
                            fetchedNewsArray.getJSONObject(i).getJSONObject("source").getString("name")
                           }
                        else{
                            newsJson.getString("author")
                            },
                        newsJson.getString("url"),
                        newsJson.getString("urlToImage")
                    )
                    newsArray.add(currentNews)

                }
                rvAdapter.refreshNews(newsArray)

            },
            {
                Toast.makeText(this,"Check your internet connection !",Toast.LENGTH_LONG).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }
        ApiAccess.getInstance(this).addToRequestQueue(parentJsonReq)
    }

    override fun onNewsItemClick(item: News) {
        val targetUrl= item.url
        val builder = CustomTabsIntent.Builder()
        val intent= builder.build()
        intent.launchUrl(this, Uri.parse(targetUrl))


    }
}