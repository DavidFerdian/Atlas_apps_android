package com.example.atlas.LoginAndRegister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.atlas.APIRequestHandling.APIRequestClient
import com.example.atlas.DataClass.LoggedUser
import com.example.atlas.DataClass.LoginRequestBody
import com.example.atlas.DataClass.LoginResponse
import com.example.atlas.NavigationDirections
import com.example.atlas.R
import com.example.atlas.Util.LoginStatusPreference
import com.example.atlas.Util.ProgressDialogUtility
import com.example.atlas.databinding.FragmentLogin2Binding
import com.id.centuryememberproject.util.SnackBarUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentLogin2 : Fragment() {

    lateinit var binding: FragmentLogin2Binding

    lateinit var loggedStatusPreference: LoginStatusPreference

    private lateinit var loginService: Call<LoginResponse>
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments?.let {
            FragmentLogin2Args.fromBundle(
                it
            )
        }

        if (args != null) {
            username = args.username
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login2, container, false
        )

        loggedStatusPreference =
            LoginStatusPreference(
                activity as AppCompatActivity
            )
        binding.nextButton.setOnClickListener {
            validateLogin()
        }
        return binding.root
    }

    fun validateLogin() {

        ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)

        val inputtedUsername = username
        val inputtedPassword = binding.loginPasswordEditText.text.toString().trim()

        if (inputtedUsername.isEmpty() || inputtedPassword.isEmpty()) {
            SnackBarUtility.renderSnackBar(binding.baseLayout, "Mohon mengisi username dan password")

            ProgressDialogUtility.dismissProgressDialog()

        } else {
            binding.nextButton.isEnabled = false

            val postedInformation =
                LoginRequestBody(
                    inputtedUsername,
                    inputtedPassword
                )

            executeLoginService(postedInformation)
        }
    }
    private fun executeLoginService(postedInformation: LoginRequestBody) {
        loginService = APIRequestClient.MemberService(activity as AppCompatActivity)
            .loginUser(postedInformation)

        loginService.enqueue(object :
            Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                ProgressDialogUtility.dismissProgressDialog()

                if (!call.isCanceled) {
                    SnackBarUtility.renderSnackBar(
                        binding.baseLayout,
                        getString(R.string.api_request_failed_message)
                    )
                  enableLoginButton()
                }
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.code() == 200) {

                    enableLoginButton()

                    val username = response.body()?.data?.ememberUsername
                    val memberId = response.body()?.data?.ememberId
                    val loginToken = response.headers()["authorization"]

                    if (memberId != null && loginToken != null) {
                        ProgressDialogUtility.dismissProgressDialog()

                            this@FragmentLogin2.setupLoggedUser(memberId, username!!, loginToken)
                            navigateToDashboard()

                    } else {
                        ProgressDialogUtility.dismissProgressDialog()
                        SnackBarUtility.renderSnackBar(
                            binding.baseLayout,
                            getString(R.string.api_request_failed_message)
                        )
                    }

                } else {

                    ProgressDialogUtility.dismissProgressDialog()

                    SnackBarUtility.renderSnackBar(
                        binding.baseLayout,
                        "Username atau Password anda tidak sesuai"
                    )
                    enableLoginButton()
                }
            }
        })
    }

    private fun setupLoggedUser(memberId: String, username: String, loginToken: String) {
        loggedStatusPreference.setAsLoggedIn(
            LoggedUser(
                true,
                memberId,
                username,
                loginToken
            )
        )
    }
    private fun enableLoginButton() {
        binding.nextButton.text = getString(R.string.login_button_text)
        binding.nextButton.isEnabled = true
    }
    fun navigateToDashboard() {
        findNavController().navigate(
            NavigationDirections.actionRegisterPage2ToFirstFragment()
        )
    }
}