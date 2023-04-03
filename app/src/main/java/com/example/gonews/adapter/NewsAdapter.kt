package com.example.newsaqq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gonews.databinding.ItemLayoutBinding

import com.example.newsaqq.model.Article

class NewsAdapter(val context: Context, val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.newsTitle.text = article.title
        holder.binding.newsDescription.text = article.description
        Glide.with(context).load(article.urlToImage).into(holder.binding.newsImage)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, article.title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}
