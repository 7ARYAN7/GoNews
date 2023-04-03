package com.example.gonews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonews.databinding.FragmentHomeBinding
import com.example.newsaqq.adapter.NewsAdapter
import com.example.newsaqq.api.RetrofitClient
import com.example.newsaqq.model.News
import com.example.newsaqq.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: NewsAdapter
    private lateinit var newsList: RecyclerView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        newsList = binding.newsList
        getNews()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getNews() {
        val country = "in" // replace with your desired country code
        val page = 2 // replace with your desired page number

        RetrofitClient.newsApi.getHeadlines(country, page).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                Log.d(Constants.TAG,response.body().toString())
                if (news!=null) {
                    Log.d(Constants.TAG,news.articles.toString())
                    adapter = NewsAdapter(this@HomeFragment.requireContext(),news.articles)
                    newsList.adapter = adapter
                    newsList.layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
                } else {
                    Log.d(Constants.TAG,"Empty Check endpoints")
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(Constants.TAG,"Error in Fetching News",t)
            }
        })
    }
}