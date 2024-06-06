package com.caresaas.app.features.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.caresaas.app.features.home.ActivitiesFragment
import com.caresaas.app.features.home.MedicationFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MedicationFragment()
            1 -> ActivitiesFragment()
            else -> MedicationFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}