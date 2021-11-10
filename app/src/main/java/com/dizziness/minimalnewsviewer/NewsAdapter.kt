package com.dizziness.minimalnewsviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener:ClickListener): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val title :TextView = itemView.findViewById(R.id.newsTitle)
        val author :TextView = itemView.findViewById(R.id.newsAuthor)
        val image :ImageView = itemView.findViewById(R.id.newsPreview)



    }
    private val dataList: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_entry_layout,parent,false)
        val vh = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onNewsItemClick(dataList[vh.adapterPosition])
        }
        return vh
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews= dataList[position]
        holder.title.text = currentNews.title
        holder.author.text ="- ${currentNews.author}"
        Glide.with(holder.itemView.context).load(currentNews.img).into(holder.image)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun refreshNews(newsArray: ArrayList<News>){
        dataList.clear();
        dataList.addAll(newsArray)
        notifyDataSetChanged()
    }
}
interface ClickListener{
    fun onNewsItemClick(item: News)
}