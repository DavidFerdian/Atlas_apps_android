package com.example.atlas.LoginAndRegister

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.atlas.NavigationDirections
import com.example.atlas.R
import com.example.atlas.Util.LoginStatusPreference
import com.example.atlas.Util.ProgressDialogUtility
import com.example.atlas.databinding.FragmentLoginBinding
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponse
import com.id.centuryememberproject.util.SnackBarUtility
import retrofit2.Call

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    lateinit var loggedStatusPreference: LoginStatusPreference


    lateinit var loginContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )
        navController = this.findNavController()


        loggedStatusPreference =
            LoginStatusPreference(
                loginContext
            )
        binding.nextButton.setOnClickListener {
            validateLogin()
        }
        return binding.root
    }

    fun navigateToLoginpage2() {
        findNavController().navigate(
            NavigationDirections.actionLoginfragment1ToLoginFragment2(
                binding.loginUsernameEditText.text.toString().trim()
            )
        )
    }

    fun validateLogin() {

        ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)

        val inputtedUsername = binding.loginUsernameEditText.text.toString().trim()

        if (inputtedUsername.isEmpty()) {
            SnackBarUtility.renderSnackBar(binding.baseLayout, "Mohon mengisi username / No HP")

            ProgressDialogUtility.dismissProgressDialog()

        } else {
            ProgressDialogUtility.dismissProgressDialog()
            binding.nextButton.isEnabled = false
            navigateToLoginpage2()
        }
    }
}