package com.example.gonews.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonews.databinding.FragmentHomeBinding
import com.example.newsaqq.adapter.NewsAdapter
import com.example.newsaqq.api.RetrofitClient
import com.example.newsaqq.model.News
import com.example.newsaqq.utils.Constants
import com.example.newsaqq.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: NewsAdapter
    private lateinit var newsList: RecyclerView
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
        val searchView: android.widget.SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getNewsByString(query)
                }
                else
                    getNews()
                searchView.clearFocus() // shut down the keyboard
                searchView.setQuery("", false) // clear the search bar
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, newText.toString())
                return false
            }
        })
        val dropdown: Spinner = binding.dropdown
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // call your function here, passing the selected item as a parameter
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                getNewsByString(selectedCategory)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

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
    private fun getNewsByString(category: String) {
        val country = "in" // replace with your desired country code

        RetrofitClient.newsApi.getHeadlinesByCategory(country, category).enqueue(object : Callback<News> {
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
