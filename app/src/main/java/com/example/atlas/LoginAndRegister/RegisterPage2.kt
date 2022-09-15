package com.example.atlas.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.atlas.APIRequestHandling.APIRequestClient
import com.example.atlas.DataClass.*
import com.example.atlas.NavigationDirections
import com.example.atlas.R
import com.example.atlas.Util.LoginStatusPreference
import com.example.atlas.Util.ProgressDialogUtility
import com.example.atlas.databinding.FragmentRegisterPage2Binding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponse
import com.id.centuryememberproject.util.SnackBarUtility
import kotlinx.android.synthetic.main.fragment_register_page2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import kotlin.CharSequence as CharSequence1


class RegisterPage2 : Fragment() {
    lateinit var binding: FragmentRegisterPage2Binding
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +  "(?=.*[0-9])" +         //angka
                "(?=.*[a-zA-Z])" +      //huruf
                ".{7,}" +  //minimal 7 character
                "$"
    )
    lateinit var fullName: String
    lateinit var selectedGender: String
    lateinit var selectedBirthday: String
    lateinit var selectedCity: String
    lateinit var phoneNumber: String
    lateinit var postalCode: String
    lateinit var address: String
    lateinit var username: String
    lateinit var password: String
    lateinit var email: String
    lateinit var confirmPassword: String
    lateinit var registerContext: Context
    lateinit var registrationService: Call<RegisterMemberResponse>
    lateinit var loginService: Call<LoginResponse>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments?.let {
            RegisterPage2Args.fromBundle(
                it
            )

        }

        if (args != null) {
            fullName = args.fullName
            phoneNumber = args.phoneNumber
            selectedBirthday = args.selectedBirthday
            selectedGender = args.selectedGender
            selectedCity = args.selectedCity
            postalCode=args.postalCode
            address = args.address
        }
    }

    fun CharSequence1?.isValidPassword():Boolean{
        return !isNullOrEmpty() && PASSWORD_PATTERN.matcher(this).matches()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register_page2, container, false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.finishRegistrationButton.setOnClickListener {
            ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)
            validateInformationInput()
        }

        binding.backToPrevRegistrationPageButton.setOnClickListener {
            this@RegisterPage2.parentFragmentManager.popBackStack()
        }

        binding.progressBar.visibility = View.GONE
        // Text Watcher Password
        registerNewPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence1?,
                p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence1?,
                p1: Int, p2: Int, p3: Int
            ) {

                // check inputted text that it is a valid email address or not
                if (p0.isValidPassword()) {
                    binding.registerNewPasswordInputLayout.error = null
                } else{
                    binding.registerNewPasswordInputLayout.error = "Password harus kombinasi angka dan huruf dengan min. 7 Karakter"
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        //Text Watcher Confirm Password
        registerConfirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence1?,
                p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence1?,
                p1: Int, p2: Int, p3: Int
            ) {
                pickInformationFromTextField()
                confirmPassword = p0.toString()
                // check inputted text that it is a valid email address or not
                if (confirmPassword==password) {
                    binding.registerConfirmPasswordInputLayout.error = null
                } else{
                    binding.registerConfirmPasswordInputLayout.error = "Confirm Password Belum Sesuai"
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        registerEmailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence1?,
                p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence1?,
                p1: Int, p2: Int, p3: Int
            ) {
                pickInformationFromTextField()
                email = p0.toString()
                // check inputted text that it is a valid email address or not
                if (email.isNotEmpty() && email.contains("@") && !email.endsWith("@")
                    && !email.endsWith(".") &&!email.contains(",") && email.contains(".") ) {
                    binding.registerEmailInputLayout.error = null
                }else{
                    binding.registerEmailInputLayout.error = "Format email tidak sesuai !"
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    private fun validateInformationInput() {

        pickInformationFromTextField()
        ProgressDialogUtility.dismissProgressDialog()

        binding.registerEmailInputLayout.error = ""
        binding.registerUserNameInputLayout.error = ""
        binding.registerNewPasswordInputLayout.error = ""
        binding.registerConfirmPasswordInputLayout.error = ""

        when {
            email.isEmpty() -> {
                binding.registerEmailInputLayout.error = "Mohon isi Email anda"
                return
            }
            !email.contains("@") || email.endsWith("@") || email.endsWith(".") -> {
                binding.registerEmailInputLayout.error = "Format email tidak sesuai"
                return
            }
            username.isEmpty() -> {
                binding.registerUserNameInputLayout.error = "Mohon isi Username anda"
                return
            }
            password.isEmpty() -> {
                binding.registerNewPasswordInputLayout.error = "Mohon isi Password anda"
                return
            }

            !PASSWORD_PATTERN.matcher(password).matches() -> {
                binding.registerNewPasswordInputLayout.error = getString(R.string.password_validation_text)
                return
            }

            confirmPassword != password -> {
                binding.registerConfirmPasswordInputLayout.error = "Password tidak sama"
                return
            }
            else -> {
                ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)
                showRegistrationConfirmationDialog()
            }
        }

    }


    private fun pickInformationFromTextField() {
        email = binding.registerEmailEditText.text.toString()
        username = binding.registerUserNameEditText.text.toString()
        password = binding.registerNewPasswordEditText.text.toString()
        confirmPassword = binding.registerConfirmPasswordEditText.text.toString()
    }


    private fun navigateToOTPFragment() {
        findNavController().navigate(
            NavigationDirections.toRegisterOTPFragment(
                fullName,
                phoneNumber,
                selectedBirthday,
                selectedGender,
                email,
                username,
                password,
                address,
                "",
                selectedCity,
                "",
                postalCode
            )
        )
    }


    private fun showRegistrationConfirmationDialog() {
        ProgressDialogUtility.dismissProgressDialog()
        MaterialAlertDialogBuilder((activity as AppCompatActivity))
            .setTitle("Konfirmasi")
            .setMessage("Anda yakin akan mendaftar dengan data yang anda masukkan ?")
            .setNegativeButton(resources.getString(R.string.confirmationNegativeResponse)) { _, _ ->
                //Do Nothing
            }
            .setPositiveButton(resources.getString(R.string.confirmationPositiveResponse)) { _, _ ->

                //showTermsAndConditionDialog()
                registerMember()
            }
            .show()
    }


    private fun registerMember() {

        ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)

        val insertedInformation =
            RegisterRequest(
                selectedCity,
                "district",
                username,
                postalCode,
                "province",
                fullName,
                password,
                phoneNumber,
                email,
                address,
                "V01"
            )

        Log.i("apa_aja", insertedInformation.toString())

        registrationService = APIRequestClient.MemberService(requireContext())
            .RegisterMember(insertedInformation)

        registrationService.enqueue(object :
            Callback<RegisterMemberResponse> {
            override fun onFailure(call: Call<RegisterMemberResponse>, t: Throwable) {
                ProgressDialogUtility.dismissProgressDialog()
                binding?.registerPart2RootLayout?.let {
                    SnackBarUtility.renderSnackBar(
                        it,
                        getString(R.string.api_request_failed_message) + " (${t.message})"
                    )
                }
                Log.i("errorapa",t.toString())
            }

            override fun onResponse(
                call: Call<RegisterMemberResponse>,
                response: Response<RegisterMemberResponse>
            ) {
                ProgressDialogUtility.dismissProgressDialog()
                if (response.code() == 200) {
                    binding?.registerPart2RootLayout?.let {
                        SnackBarUtility.renderSnackBar(
                            it, "Anda berhasil registrasi"
                        )
                    }
                    Log.i("masukya",response.body().toString())
                    logUserIn()
                } else if (response.code() == 400) {

                    try {
                        //Retrofit does not convert response if response code is 400
                        val adapter: TypeAdapter<DefaultResponse> =
                            Gson().getAdapter(DefaultResponse::class.java)

                        val responseBody = adapter.fromJson(response.errorBody()?.string())

                        binding?.registerPart2RootLayout?.let {
                            SnackBarUtility.renderSnackBar(
                                it,
                                "${responseBody.error?.msg?.substringAfter('-')}"
                            )
                        }
                    } catch (error: Exception) {
                        binding?.registerPart2RootLayout?.let {
                            SnackBarUtility.renderSnackBar(
                                it,
                                getString(R.string.api_request_failed_message)
                            )
                            Log.i("errorapa",response.toString())
                        }
                    }
                } else {
                    binding?.registerPart2RootLayout?.let {
                        SnackBarUtility.renderSnackBar(
                            it,
                            getString(R.string.api_request_failed_message) + " (registration) ${response.code()}"
                        )
                    }
                    Log.i("errorapa",response.toString())
                }

            }
        })
    }

    private fun logUserIn() {

        ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)

        val postedInformation =
            LoginRequestBody(
                username,
                password
            )

        executeLoginService(postedInformation)
    }


    private fun executeLoginService(postedInformation: LoginRequestBody) {
        loginService = APIRequestClient.MemberService(activity as AppCompatActivity)
            .loginUser(postedInformation)

        loginService.enqueue(object :
            Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                ProgressDialogUtility.dismissProgressDialog()

                binding?.registerPart2RootLayout?.let {
                    SnackBarUtility.renderSnackBar(
                        it,
                        getString(R.string.api_request_failed_message)
                    )
                }
                Log.i("errorapa",t.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.code() == 200) {

                    Toast.makeText(
                        activity as AppCompatActivity,
                        "Selamat Datang, ${postedInformation.ememberUsername} !",
                        Toast.LENGTH_LONG
                    ).show()

                    val username = response.body()!!.data?.ememberUsername
                    val memberId = response.body()!!.data?.ememberId
                    val loginToken = response.headers()["authorization"]

                    if (memberId != null && loginToken != null) {
                        setupLoggedUser(memberId, username!!, loginToken)


                        ProgressDialogUtility.dismissProgressDialog()
                        navigateToDashboard()
                    } else {
                        ProgressDialogUtility.dismissProgressDialog()
                        binding?.registerPart2RootLayout?.let {
                            SnackBarUtility.renderSnackBar(
                                it,
                                getString(R.string.api_request_failed_message)
                            )
                        }
                        Log.i("errorapa",response.toString())
                    }

                } else {

                    ProgressDialogUtility.dismissProgressDialog()

                    binding?.registerPart2RootLayout?.let {
                        SnackBarUtility.renderSnackBar(
                            it,
                            getString(R.string.api_request_failed_message) + " (Login) ${response.code()}"
                        )
                    }
                    Log.i("errorapa",response.toString())
                    Log.i("loginService", "${response}")
                    Log.i("loginService", "${response.code()}")
                    Log.i("loginService", "${response.body()}")
                    Log.i("loginService", "${response.errorBody()}")
                    Log.i("loginService", "${response.errorBody()}")
                    Log.i("loginService", "${postedInformation}")
                }
            }
        })
    }

    private fun setupLoggedUser(memberId: String, username: String, loginToken: String) {
        LoginStatusPreference(
            activity as AppCompatActivity
        ).setAsLoggedIn(
            LoggedUser(
                true,
                memberId,
                username,
                loginToken
            )
        )
    }

    fun navigateToDashboard() {

        findNavController().addOnDestinationChangedListener { nc: NavController, _: NavDestination, _ ->
            nc.graph.startDestination = R.id.bottomNavigationFragment
        }

        val options = NavOptions.Builder()
            .setPopUpTo(R.id.registerOTP, true)
            .build()

        findNavController().navigate(
            NavigationDirections.actionRegisterPage2ToFirstFragment(
            ), options
        )
    }
}