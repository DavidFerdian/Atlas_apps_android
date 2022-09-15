package com.example.atlas.RecyclerViewAdapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class ProfileTabAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    // sebuah list yang menampung objek Fragment
    private var fragmentList: MutableList<Fragment> = ArrayList()

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Profile"
            1 -> "Member Card"
            2 -> "Password"
            else -> "Daftar Alamat"
        }
    }

    fun addFragment(fragment: Fragment?) {
        fragmentList.add(fragment!!)
    }
}