package com.catbit.uikitty_sample.ui.page_indicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catbit.uikitty_sample.databinding.FragmentSamplePageBinding

class SamplePageFragment(
    private val imageResId: Int,
    private val message: String
) : Fragment() {

    private lateinit var binding: FragmentSamplePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSamplePageBinding.inflate(inflater)

        with(binding) {
            ivImage.setImageResource(imageResId)
            tvMessage.text = message
        }

        return binding.root
    }
}