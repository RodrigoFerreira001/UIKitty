package com.catbit.uikitty_sample.ui.page_indicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.catbit.uikitty_sample.R
import com.catbit.uikitty_sample.databinding.FragmentPageIndicatorSampleBinding

class PageIndicatorSampleFragment : Fragment() {

    private lateinit var binding: FragmentPageIndicatorSampleBinding
    private lateinit var pages: List<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageIndicatorSampleBinding.inflate(inflater)

        pages = listOf(
            SamplePageFragment(
                R.drawable.ic_undraw_access_denied_re_awnf,
                "Isso é um teste 1"
            ),
            SamplePageFragment(
                R.drawable.ic_undraw_confirmed_re_sef7,
                "Isso é um teste 2"
            ),
            SamplePageFragment(
                R.drawable.ic_undraw_mobile_apps_re_3wjf,
                "Isso é um teste 3"
            )
        )

        binding.viewPager.adapter = SamplePageAdapter()

        binding.pageIndicator.attachViewPager(binding.viewPager)

        return binding.root
    }

    private inner class SamplePageAdapter : FragmentStateAdapter(childFragmentManager, lifecycle) {

        override fun getItemCount(): Int = pages.size
        override fun createFragment(position: Int): Fragment = pages[position]
    }
}