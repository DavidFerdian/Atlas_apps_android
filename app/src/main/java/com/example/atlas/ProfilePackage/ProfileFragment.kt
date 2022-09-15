package com.example.atlas.ProfilePackage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.atlas.*
import com.example.atlas.RecyclerViewAdapter.ProfileTabAdapter
import com.example.atlas.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayout


class ProfileFragment : Fragment() {
    var binding: FragmentProfileBinding? = null
    lateinit var profilePageContext: Context
    lateinit var memberId: String
    private lateinit var loginToken: String
    var numbercheck: Int = 0
    var isUserLoggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile, container, false
            )
        }


        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        profilePageContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.backToPreviousPageButton.setOnClickListener {
            this.parentFragmentManager.popBackStackImmediate()
        }

    }

    //Call onActivity Create method
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding?.tabsMain!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}

        })
        setUpViewPager(binding!!.viewpagerMain)
        binding?.tabsMain!!.setupWithViewPager(binding!!.viewpagerMain)
        binding!!.tabsMain.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_account_circle_24)
        binding!!.tabsMain.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_credit_card_24)
        binding!!.tabsMain.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_lock_24)
        binding!!.tabsMain.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_house_24)
        if(numbercheck==1){
            binding!!.viewpagerMain?.currentItem = 1
        }else if(numbercheck==0){
            binding!!.viewpagerMain?.currentItem = 0
        }
    }
    private fun setUpViewPager(viewPager: ViewPager?) {
        val adapter = ProfileTabAdapter(childFragmentManager)
        adapter.addFragment(ProfileDetailFragment())
        adapter.addFragment(MemberCardFragment())
        adapter.addFragment(PasswordFragment())
        adapter.addFragment(AddressFragment())
        viewPager!!.adapter = adapter
    }

    companion object {
        fun newInstance(isBackButtonVisible: Boolean): ProfileFragment {

            val fragment = ProfileFragment()
            val args = Bundle()

            args.putBoolean("isBackButtonVisible", isBackButtonVisible)

            fragment.arguments = args
            return fragment
        }
    }
}