package com.example.atlas.LoginAndRegister

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.atlas.NavigationDirections
import com.example.atlas.databinding.FragmentRegisterPage1Binding
import kotlinx.android.synthetic.main.activity_main.*
import com.example.atlas.R
import kotlinx.android.synthetic.main.fragment_register_page1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterPage1 : Fragment() {

    lateinit var binding: FragmentRegisterPage1Binding


    lateinit var fullName: String
    lateinit var phoneNumber: String
    lateinit var address: String
    lateinit var postalCode: String
    var selectedCity: String = ""

    lateinit var registerContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register_page1, container, false
        )
        binding.registerPart1NextButton.setOnClickListener{
            validateIdentityInput()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        registerNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int, p2: Int, p3: Int
            ) {
                fullName=p0.toString()
                // check inputted text that it is a valid email address or not
                if (!fullName.contains("0")&&!fullName.contains("1")&&!fullName.contains("2")&&
                    !fullName.contains("3")&&!fullName.contains("4")&&!fullName.contains("5")&&
                    !fullName.contains("6")&&!fullName.contains("7")&&!fullName.contains("8")&&
                    !fullName.contains("9")) {
                    binding.registerNameInputLayout.error = null
                } else{
                    binding.registerNameInputLayout.error = "Anda Tidak mengisi Nama dengan Benar"
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
// extension function to validate edit text inputted email address

    //showTermsAndConditionDialog()

    fun validateIdentityInput() {


        binding.registerNameInputLayout.error = ""
        binding.registerphoneInputLayout.error = ""

        fullName = binding.registerNameEditText.text.toString().trim()
        phoneNumber = binding.registerphoneEditText.text.toString().trim()
        address = binding.registeraddressNameEditText.text.toString().trim()
        postalCode = binding.postalCodeEditText.text.toString().trim()

        when {

            fullName.isEmpty() -> {
                binding.registerphoneInputLayout.error = "Isi Nama Anda !"
                return
            }
            phoneNumber.isEmpty() -> {
                binding.registerphoneInputLayout.error = "Isi No. Telepon anda !"
                return
            }
            phoneNumber.length < 10 -> {
                binding.registerphoneInputLayout.error = "No. Telepon min. 10 digit!"
                return
            }
            address.isEmpty() -> {
                binding.registeraddressNameInputLayout.error = "Isi Alamat anda"
                return
            }
            address.length<=150 ->{
                binding.registerphoneInputLayout.error = "No. Telepon min. 10 digit!"
                return
            }
//            selectedCity == "" -> {
//                Toast.makeText(
//                    activity as AppCompatActivity,
//                    "Pilih Kota Anda",
//                    Toast.LENGTH_LONG
//                ).show()
//                return
//            }
//            postalCode.isEmpty() -> {
//                binding.postalCodeInputLayout.error = "Isi Kode Pos anda !"
//                return
//            }
//            postalCode.length<5 -> {
//                binding.postalCodeInputLayout.error = "Isi Kode Pos anda !"
//                return
//            }
        }
        navigateToNextPage()

        //If no error appeared, then navigate to next page
    }

    private fun navigateToNextPage() {

        findNavController().navigate(
            NavigationDirections.toRegisterFragmentPart2(
                fullName,
                phoneNumber,
                "",
                "",
                address,
                selectedCity,
                postalCode
            )
        )

    }

}