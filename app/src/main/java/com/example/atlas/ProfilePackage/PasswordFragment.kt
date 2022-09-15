package com.example.atlas.ProfilePackage

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.atlas.R
import com.example.atlas.databinding.FragmentPasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class PasswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentPasswordBinding
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" + "(?=.*[0-9])" +         //angka
                "(?=.*[A-Z])" +      //huruf
                ".{7,}" +  //minimal 7 character
                "$"
    )
    lateinit var newpassword :String
    lateinit var connewpassword :String
    lateinit var passwordContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        passwordContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_password, container, false
            )
        passwordValidation()

        // Inflate the layout for this fragment
        return binding?.root
    }

    private fun passwordValidation() {


        binding.applyChangePasswordButton.setOnClickListener {
            validateNewPasswordInput()
        }
        // Text Watcher Password
        binding.newPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int, p2: Int, p3: Int
            ) {

                // check inputted text that it is a valid email address or not
                if (p0.isValidPassword()) {
                    binding.newPasswordInputLayout.error = null
                } else {
                    binding.newPasswordInputLayout.error =
                        "Password harus kombinasi angka dan huruf dengan min. 7 Karakter"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        //Text Watcher Confirm Password
        binding.confirmNewPasswordEditText.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int, p2: Int, p3: Int
            ) {
                pickInformationFromTextField()

                // check inputted text that it is a valid email address or not
                if (binding.newPasswordEditText.text.toString() == binding.confirmNewPasswordEditText.text.toString()) {
                    binding.confirmNewPasswordInputLayout.error = null
                } else {
                    binding.confirmNewPasswordInputLayout.error =
                        "Confirm Password Belum Sesuai"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    private fun pickInformationFromTextField() {
        newpassword = binding.newPasswordEditText.text.toString().trim()
        connewpassword = binding.confirmNewPasswordEditText.text.toString().trim()
    }


    fun CharSequence?.isValidPassword(): Boolean {
        return !isNullOrEmpty() && PASSWORD_PATTERN.matcher(this).matches()
    }

    private fun validateNewPasswordInput() {
        var noError = true
        binding.currentPasswordInputLayout.error = null
        binding.newPasswordInputLayout.error = null
        binding.confirmNewPasswordInputLayout.error = null


        if (binding.currentPasswordEditText.text.toString().isEmpty()) {
            binding.currentPasswordInputLayout.error = "Masukkan password anda !"
            noError = false
        } else {

            if (binding.newPasswordEditText.text.toString().isEmpty()) {
                binding.newPasswordInputLayout.error =
                    "Masukkan password baru anda"
                noError = false

            } else if (!PASSWORD_PATTERN.matcher(binding.newPasswordEditText.text.toString())
                    .matches()
            ) {
                binding.newPasswordInputLayout.error =
                    getString(R.string.password_validation_text)
                noError = false
            } else if (binding.newPasswordEditText.text.toString() != binding.confirmNewPasswordEditText.text.toString()) {
                binding.confirmNewPasswordInputLayout.error =
                    "Password tidak sama"
                noError = false
            } else if (binding.newPasswordEditText.text.toString() == binding.currentPasswordEditText.text.toString()) {
                binding.newPasswordInputLayout.error =
                    "Password lama dan Password baru tidak boleh sama !"
                noError = false
            }
        }

        if (noError) {
            showChangePasswordConfirmationDialog()
        }
    }


    private fun showChangePasswordConfirmationDialog() {
        MaterialAlertDialogBuilder(passwordContext)
            .setTitle("Konfirmasi")
            .setMessage("Anda yakin dengan perubahan yang dibuat ?")
            .setNegativeButton(resources.getString(R.string.confirmationNegativeResponse)) { _, _ ->
                //Do Nothing
            }
            .setPositiveButton(resources.getString(R.string.confirmationPositiveResponse)) { _, _ ->
            }
            .show()
    }
}