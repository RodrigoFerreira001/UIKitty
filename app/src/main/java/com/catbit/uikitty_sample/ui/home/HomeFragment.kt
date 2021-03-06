package com.catbit.uikitty_sample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catbit.uikitty_sample.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        with(binding) {
            btPageIndicator.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPageIndicatorSampleFragment()
                )
            }
        }

        return binding.root
    }
}