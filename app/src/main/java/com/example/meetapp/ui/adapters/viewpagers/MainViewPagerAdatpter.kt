package com.example.meetapp.ui.adapters.viewpagers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.meetapp.ui.fragments.HomePageFragment
import com.example.meetapp.ui.fragments.PresentersFragment
import com.example.meetapp.ui.fragments.ScheduleFragment

class MainViewPagerAdatpter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val contents = listOf(
            ScheduleFragment.getInstance(),
            HomePageFragment.getInstance(),
            PresentersFragment.getInstance()
        )
        return contents[position]
    }
}