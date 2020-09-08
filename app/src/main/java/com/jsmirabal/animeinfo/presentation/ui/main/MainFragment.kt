package com.jsmirabal.animeinfo.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jsmirabal.animeinfo.R
import com.jsmirabal.animeinfo.databinding.ActivityMainBinding
import com.jsmirabal.animeinfo.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)
}
