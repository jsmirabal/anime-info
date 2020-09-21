package com.jsmirabal.animeinfo.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsmirabal.animeinfo.databinding.FragmentMainBinding
import com.jsmirabal.animeinfo.presentation.ui.main.feed.MainFeedAdapter
import com.jsmirabal.animeinfo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()
    private val mainAdapter = MainFeedAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.mainRecyclerView.adapter = mainAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMainFeedLiveData().observe(viewLifecycleOwner, Observer {
            mainAdapter.update(it.feedItems)
        })

        viewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            Toast.makeText(this.context, "Error: $it", Toast.LENGTH_LONG).show()
        })

        viewModel.buildMainFeed()
    }
}
