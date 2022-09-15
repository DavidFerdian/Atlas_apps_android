package com.example.atlas.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atlas.R
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
import com.example.atlas.LoginAndRegister.RegisterOTPArgs
import com.example.atlas.NavigationDirections
import com.example.atlas.Util.LoginStatusPreference
import com.example.atlas.Util.ProgressDialogUtility
import com.example.atlas.databinding.FragmentRegisterOtpBinding
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponse
import com.id.centuryememberproject.util.SnackBarUtility
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import kotlinx.android.synthetic.main.edit_otp_phone_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RegisterOTP : Fragment() {

    var binding: FragmentRegisterOtpBinding? = null

    lateinit var fullname: String
    lateinit var phoneNumber: String
    lateinit var birthDay: String
    lateinit var gender: String

    lateinit var email: String
    lateinit var username: String
    lateinit var password: String

    lateinit var address: String
    lateinit var province: String
    lateinit var city: String
    lateinit var district: String
    lateinit var postalCode: String
    var tncVersion: String? = null


    lateinit var registrationService: Call<RegisterMemberResponse>
    lateinit var loginService: Call<LoginResponse>


    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mVerificationId : String

    lateinit var expirationTimer: CountDownTimer

    lateinit var registerOTPContext: Context

    val expiredOTPTime = 30

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerOTPContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments?.let {
            RegisterOTPArgs.fromBundle(
                it
            )
        }

        fullname = args?.fullName.toString()
        phoneNumber = args?.phoneNumber.toString()
        birthDay = args?.selectedBirthday.toString()
        gender = args?.selectedGender.toString()

        email = args?.email.toString()
        username = args?.username.toString()
        password = args?.password.toString()

        address = args?.address.toString()
        province = args?.province.toString()

        city = args?.city.toString()
        district = args?.district.toString()
        postalCode = args?.postalCode.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_register_otp, container, false
            )

        }

        mAuth = FirebaseAuth.getInstance()

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                ProgressDialogUtility.dismissProgressDialog()
                verifyOTP(credential)
                binding?.etOtpPhone?.setText(credential.smsCode)
            }

            override fun onVerificationFailed(exception: FirebaseException) {

                ProgressDialogUtility.dismissProgressDialog()

                if (exception is FirebaseTooManyRequestsException) {
                    Toast.makeText(
                        activity as AppCompatActivity,
                        "Limit sms sudah mencapai batas",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if(exception is FirebaseAuthInvalidCredentialsException){
                    binding?.baseLayout?.let { it1 ->
                        SnackBarUtility.renderSnackBar(
                            it1,
                            "Kode OTP yang anda masukkan salah / telah kadaluarsa"
                        )
                    }
                }else if(exception is FirebaseNetworkException){
                    binding?.baseLayout?.let { it1 ->
                        SnackBarUtility.renderSnackBar(
                            it1,
                            "Gagal mengirim OTP, mohon periksa kembali koneksi anda"
                        )
                    }
                }else {
                    Toast.makeText(
                        registerOTPContext,
                        "Terjadi kesalahan, mohon coba kembali",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onCodeSent(
                verification: String,
                resendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verification, resendingToken)

                ProgressDialogUtility.dismissProgressDialog()

                startTryAgainCountdown()

                mResendingToken = resendingToken
                mVerificationId = verification

                binding?.rlResendOtp?.visibility = View.VISIBLE

                binding?.sendOtpButton?.isEnabled = false
                binding?.resendOtpButton?.isEnabled = false
                binding?.sendOtpButton?.visibility = View.INVISIBLE
                binding?.resendOtpButton?.visibility = View.VISIBLE

                binding?.etOtpPhoneLayout?.isEnabled = true
                binding?.authenticateOtpButton?.isEnabled = true

                binding?.baseLayout?.let {
                    SnackBarUtility.renderSnackBar(
                        it,
                        registerOTPContext.getString(R.string.otp_sent_message)
                    )
                }
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)

                ProgressDialogUtility.dismissProgressDialog()

                binding?.rlResendOtp?.visibility = View.GONE
                binding?.resendOtpButton?.isEnabled = true
                binding?.sendOtpButton?.isEnabled = true
            }


        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.tvInputtedPhone?.text = phoneNumber

        binding?.sendOtpButton?.setOnClickListener {
            ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)
            sendOTP()
        }

        binding?.resendOtpButton?.setOnClickListener {
            ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)
            resendOTP()
        }

        binding?.authenticateOtpButton?.setOnClickListener {
            authenticateByCode()
        }

        binding?.ivEditPhoneButton?.setOnClickListener {
            showEditPhoneDialog()
        }
        binding?.backToPreviousPageButton?.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun showEditPhoneDialog() {
        val editPhoneDialog = MaterialAlertDialogBuilder(activity as AppCompatActivity)
            .setView(R.layout.edit_otp_phone_layout).setTitle("Nomor Handphone anda").show()

        val editPhoneDialogInput = editPhoneDialog.et_otp_edit_phone

        editPhoneDialogInput.setText(phoneNumber)

        editPhoneDialog.btn_save_new_phone.setOnClickListener {

            val newPhone = editPhoneDialogInput.text.toString().trim()

            Log.i("pastedPhone", "new Phone = $newPhone")
            phoneNumber = newPhone
            binding?.tvInputtedPhone?.text = phoneNumber
            editPhoneDialog.dismiss()
        }

        editPhoneDialog.btn_cancel_new_phone.setOnClickListener {
            editPhoneDialog.dismiss()
        }
    }

    private fun startTryAgainCountdown() {

        expirationTimer = object : CountDownTimer((expiredOTPTime * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                binding?.tvOtpTryAgainCountdown?.text =
                    getString(R.string.otp_try_again_countdown, timeLeft)
            }

            override fun onFinish() {
                binding?.rlResendOtp?.visibility = View.GONE
                binding?.resendOtpButton?.isEnabled = true
                binding?.sendOtpButton?.isEnabled = true
            }
        }
        expirationTimer.start()
    }


    private fun sendOTP() {
        if (phoneNumber.isNotEmpty()) {
            val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber.replaceFirst("0", "+62")) // Phone number to verify
                .setTimeout(expiredOTPTime.toLong(), TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity as AppCompatActivity) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } else {
            ProgressDialogUtility.dismissProgressDialog()
            binding?.baseLayout?.let {
                SnackBarUtility.renderSnackBar(
                    it,
                    "Masukkan no. handphone anda"
                )
            }
        }
    }


    private fun resendOTP(){
        if(phoneNumber.isNotEmpty()){
            val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber.replaceFirst("0", "+62")) // Phone number to verify
                .setTimeout(expiredOTPTime.toLong(), TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(mCallbacks) //
                .setForceResendingToken(mResendingToken)// OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }else{
            ProgressDialogUtility.dismissProgressDialog()
            binding?.baseLayout?.let { SnackBarUtility.renderSnackBar(
                it,
                "Masukkan no. handphone anda"
            ) }
        }
    }


    private fun authenticateByCode() {
        val verificationNo = binding?.etOtpPhone?.text.toString().trim()

        if (verificationNo.isNotEmpty()) {
            val credential: PhoneAuthCredential =
                PhoneAuthProvider.getCredential(mVerificationId, verificationNo)

            verifyOTP(credential)
        }
    }

    private fun verifyOTP(credential: PhoneAuthCredential) {

        ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)

        mAuth.signInWithCredential(credential).addOnCompleteListener {

            ProgressDialogUtility.dismissProgressDialog()

            if (it.isSuccessful) {
                registerMember()
                return@addOnCompleteListener
            }

            if (it.exception is FirebaseAuthInvalidCredentialsException) {
                binding?.baseLayout?.let { it1 ->
                    SnackBarUtility.renderSnackBar(
                        it1,
                        "Kode OTP yang anda masukkan salah / telah kadaluarsa"
                    )
                }
            }else if (it.exception is FirebaseNetworkException){
                binding?.baseLayout?.let { it1 ->
                    SnackBarUtility.renderSnackBar(
                        it1,
                        "Gagal verifikasi OTP, mohon periksa kembali koneksi anda"
                    )
                }
            }
        }
    }

    private fun registerMember() {

        ProgressDialogUtility.showProgressDialog(activity as AppCompatActivity)

        val insertedInformation =
            RegisterRequest(
                city,
                district,
                username,
                postalCode,
                province,
                fullname,
                password,
                phoneNumber,
                email,
                address,
                tncVersion
            )


        registrationService = APIRequestClient.MemberService(requireContext())
            .RegisterMember(insertedInformation)

        registrationService.enqueue(object :
            Callback<RegisterMemberResponse> {
            override fun onFailure(call: Call<RegisterMemberResponse>, t: Throwable) {
                ProgressDialogUtility.dismissProgressDialog()
                binding?.baseLayout?.let {
                    SnackBarUtility.renderSnackBar(
                        it,
                        getString(R.string.api_request_failed_message) + " (${t.message})"
                    )
                }
            }

            override fun onResponse(
                call: Call<RegisterMemberResponse>,
                response: Response<RegisterMemberResponse>
            ) {
                ProgressDialogUtility.dismissProgressDialog()
                if (response.code() == 200) {
                    binding?.baseLayout?.let {
                        SnackBarUtility.renderSnackBar(
                            it, "Anda berhasil registrasi"
                        )
                    }
                    logUserIn()
                } else if (response.code() == 400) {

                    try {
                        //Retrofit does not convert response if response code is 400
                        val adapter: TypeAdapter<DefaultResponse> =
                            Gson().getAdapter(DefaultResponse::class.java)

                        val responseBody = adapter.fromJson(response.errorBody()?.string())

                        binding?.baseLayout?.let {
                            SnackBarUtility.renderSnackBar(
                                it,
                                "${responseBody.error?.msg?.substringAfter('-')}"
                            )
                        }
                    } catch (error: Exception) {
                        binding?.baseLayout?.let {
                            SnackBarUtility.renderSnackBar(
                                it,
                                getString(R.string.api_request_failed_message)
                            )
                        }
                    }
                } else {
                    binding?.baseLayout?.let {
                        SnackBarUtility.renderSnackBar(
                            it,
                            getString(R.string.api_request_failed_message) + " (registration) ${response.code()}"
                        )
                    }
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

                binding?.baseLayout?.let {
                    SnackBarUtility.renderSnackBar(
                        it,
                        getString(R.string.api_request_failed_message)
                    )
                }

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
                        binding?.baseLayout?.let {
                            SnackBarUtility.renderSnackBar(
                                it,
                                getString(R.string.api_request_failed_message)
                            )
                        }
                    }

                } else {

                    ProgressDialogUtility.dismissProgressDialog()

                    binding?.baseLayout?.let {
                        SnackBarUtility.renderSnackBar(
                            it,
                            getString(R.string.api_request_failed_message) + " (Login) ${response.code()}"
                        )
                    }

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

    override fun onDestroy() {
        super.onDestroy()
        if (this::expirationTimer.isInitialized) {
            expirationTimer.cancel()
        }
    }
}