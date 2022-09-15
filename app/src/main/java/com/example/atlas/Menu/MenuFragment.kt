package com.example.atlas.Menu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.atlas.NavigationDirections
import com.example.atlas.R
import com.example.atlas.Util.LoginStatusPreference
import com.example.atlas.Util.SharedPreferenceUtility
import com.example.atlas.databinding.FragmentCheckOutBinding
import com.example.atlas.databinding.FragmentMenuBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.id.centuryememberproject.util.SnackBarUtility

class MenuFragment : Fragment() {

    private lateinit var menucontext: Context
    lateinit var username: String
    lateinit var memberId: String
    var isUserLoggedIn = false
    private lateinit var binding: FragmentMenuBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        menucontext = context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loggedStatusPreference =
            LoginStatusPreference(
                menucontext
            )

        val loggedStatus = loggedStatusPreference.getUserLoggedStatus()
        memberId = loggedStatus.loggedInMemberId.toString()
        username = loggedStatus.loggedInUserName.toString()
        isUserLoggedIn = loggedStatus.isLoggedIn!!


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_menu, container, false
        )
        binding.userName.text = username
        binding.memberID.text = memberId
        binding.backToPreviousPageButton.setOnClickListener {
            activity?.onBackPressed()
            findNavController().navigate(R.id.action_MenuFragment_to_HomeFragment)

        }
        binding.logoutbutton.setOnClickListener{
            Log.i("test","bisa kok")
            showSignOutConfirmationDialog()
        }
        if(!isUserLoggedIn){
            binding.profileLayout.visibility= View.INVISIBLE
            binding.LoginRegisterLayout.visibility = View.VISIBLE
            binding.logoutbuttonLayout.visibility = View.INVISIBLE
        }else{
            binding.LoginRegisterLayout.visibility = View.INVISIBLE
            binding.profileLayout.visibility= View.VISIBLE
            binding.logoutbuttonLayout.visibility = View.VISIBLE
        }
        binding.registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_Menu_to_registerpart1)
        }
        binding.loginbutton.setOnClickListener{
            navigateToLogin()
        }
        binding.profileLayout.setOnClickListener {
            navigatetoprofile()
        }

        return binding.root
    }
    private fun showSignOutConfirmationDialog() {
        MaterialAlertDialogBuilder(menucontext)
            .setTitle("Konfirmasi")
            .setMessage(getString(R.string.log_out_confirmation_message))
            .setNegativeButton(resources.getString(R.string.confirmationNegativeResponse)) { _, _ ->
                //Do Nothing
            }
            .setPositiveButton(resources.getString(R.string.confirmationPositiveResponse)) { _, _ ->
                navigateToDashboard()
                SnackBarUtility.renderSnackBar(binding.rootLayout, "Berhasil Keluar")
            }
            .show()
    }

    private fun navigateToDashboard() {

        SharedPreferenceUtility.resetDashboardBottomNavPosition(menucontext)
        SharedPreferenceUtility.clearLoggedUser(menucontext)


        findNavController().navigate(
            NavigationDirections.actionRegisterPage2ToFirstFragment()
        )
    }
    private fun navigateToLogin() {

        SharedPreferenceUtility.resetDashboardBottomNavPosition(menucontext)
        SharedPreferenceUtility.clearLoggedUser(menucontext)


        findNavController().navigate(
            NavigationDirections.actionFirstFragmentToLoginpart1()
        )
    }
    private fun navigatetoprofile() {
        findNavController().navigate(
            NavigationDirections.toprofileFragment()
        )
    }
}