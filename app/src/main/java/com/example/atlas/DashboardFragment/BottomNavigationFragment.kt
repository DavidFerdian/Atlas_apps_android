package com.example.atlas.DashboardFragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.atlas.*
import com.example.atlas.DataClass.LastSelectedBottomNavigation
import com.example.atlas.Util.LoginStatusPreference
import com.example.atlas.databinding.FragmentBottomNavigationBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


//This fragment is a parent fragment for other 5 fragment (Home, Basket, Order List, Wishlist, and Membership)

class BottomNavigationFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var username: String
    lateinit var memberId: String
    var isUserLoggedIn = false
    var binding: FragmentBottomNavigationBinding? = null
    private lateinit var chatListFragment: Fragment
    private lateinit var orderStatusListFragment: Fragment
    private lateinit var basketFragment: Fragment
    private lateinit var homeFragment: Fragment
    private lateinit var ouletListFragment: Fragment
    private lateinit var currentActiveFragment: Fragment

    private lateinit var dashboardBottomNavigationPreference: DashboardBottomNavigationPreference

    private lateinit var dashboardFragmentManager: FragmentManager

    private lateinit var dashboardContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dashboardContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loggedStatusPreference =
            LoginStatusPreference(
                dashboardContext
            )

        val loggedStatus = loggedStatusPreference.getUserLoggedStatus()
        memberId = loggedStatus.loggedInMemberId.toString()
        username = loggedStatus.loggedInUserName.toString()
        isUserLoggedIn = loggedStatus.isLoggedIn!!

        initializeAllFragments()

        dashboardFragmentManager = requireActivity().supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_bottom_navigation, container, false
            )
        }


        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        Log.i("dashboardLife", "onViewCreated called!")

        //Setup Event Listener for bottom navigation
        requireActivity().navView
            .setNavigationItemSelectedListener(this)

        //Setup a Shared Preference to store/restore last chosen bottom navigation item
        dashboardBottomNavigationPreference =
            DashboardBottomNavigationPreference(
                dashboardContext
            )


        //Setup "Press back button twice to exit" functionality
        setupOnBackPressedCallback()

        //Setup Event Listener for bottom navigation
        binding?.bottomNavigationView?.setOnNavigationItemSelectedListener(onNavigateItemListener)

        //Prevent Fragment from reloaded when current selected menu is clicked
        binding?.bottomNavigationView?.setOnNavigationItemReselectedListener {
            if (!homeFragment.isHidden) {

                val homeScrollView = homeFragment.view?.findViewById<ScrollView>(R.id.baseLayout)

                homeScrollView?.isFocusableInTouchMode = true
                homeScrollView?.smoothScrollTo(0, 0)

            }
        }

        if (!homeFragment.isAdded) {
            currentActiveFragment = homeFragment
            this.loadHomeFragment()
        }

    }


    private fun initializeAllFragments() {
        orderStatusListFragment = OrderStatusListFragment()
        basketFragment = CartFragment()
        homeFragment = HomeFragment()
        ouletListFragment = OutletListFragment()
    }


    private fun loadBasketFragment() {

        if (!basketFragment.isAdded) {
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment)
                .add(R.id.content, basketFragment).commit()
        } else {
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment).show(basketFragment)
                .commit()
        }

    }

    private fun loadOrderStatusFragment() {

        if (!orderStatusListFragment.isAdded)
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment)
                .add(R.id.content, orderStatusListFragment).commit()
        else {
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment).show(orderStatusListFragment)
                .commit()
        }
    }

    private fun loadChatFragment() {
        if (!chatListFragment.isAdded)
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment)
                .add(R.id.content, chatListFragment).commit()
        else {
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment).show(chatListFragment)
                .commit()
        }

    }


    private fun loadHomeFragment() {

        if (!homeFragment.isAdded) {
            Log.i("dashboardLife", "Home newly declared")

            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .add(R.id.content, homeFragment).commit()
        } else {

            Log.i("dashboardLife", "Home not newly declared")

            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment).show(homeFragment)
                .commit()
        }
    }

    private fun loadchatFragment() {

        if (!homeFragment.isAdded) {
            Log.i("dashboardLife", "Home newly declared")

            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .add(R.id.content, homeFragment).commit()
        } else {

            Log.i("dashboardLife", "Home not newly declared")

            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment).show(homeFragment)
                .commit()
        }
    }

    private fun loadOutletFragment() {

        if (!ouletListFragment.isAdded) {
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment)
                .add(R.id.content, ouletListFragment).commit()
        } else {
            dashboardFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                .hide(currentActiveFragment).show(ouletListFragment)
                .commit()
        }

    }

    private val onNavigateItemListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationItemHome -> {
                    //Switch to home Fragment on selected Bottom navigation item
                    this.loadHomeFragment()

                    currentActiveFragment = homeFragment

                    dashboardBottomNavigationPreference.setLastSelectedFragmentInformation(
                        LastSelectedBottomNavigation(
                            "Home"
                        )
                    )

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationItemBasket -> {
                    //Switch to basket Fragment on selected Bottom navigation item

                    this.loadBasketFragment()

                    currentActiveFragment = basketFragment

                    dashboardBottomNavigationPreference.setLastSelectedFragmentInformation(
                        LastSelectedBottomNavigation(
                            "Basket"
                        )
                    )

                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigationItemOrder -> {
                    //Switch to order status Fragment on selected Bottom navigation item
                    this.loadOrderStatusFragment()

                    currentActiveFragment = orderStatusListFragment

                    dashboardBottomNavigationPreference.setLastSelectedFragmentInformation(
                        LastSelectedBottomNavigation(
                            "Order"
                        )
                    )

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigationItemOutlet -> {
                    this.loadOutletFragment()

                    currentActiveFragment = ouletListFragment

                    dashboardBottomNavigationPreference.setLastSelectedFragmentInformation(
                        LastSelectedBottomNavigation(
                            "Order"
                        )
                    )

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    private fun setupOnBackPressedCallback() {
        var isBackButtonPressedOnce = false

        //Phone back button click listener
        (activity)?.onBackPressedDispatcher?.addCallback(this) {
            if (isBackButtonPressedOnce) {
                (activity)?.finish()
            }

            isBackButtonPressedOnce = true
            Toast.makeText(
                (dashboardContext),
                "Please click BACK again to exit",
                Toast.LENGTH_SHORT
            ).show()

            //Reset "selected once" status after 2 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                isBackButtonPressedOnce = false
            }, 2000)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


    //Navigation Drawer items click listener


}