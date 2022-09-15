package com.example.atlas.Payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.atlas.BottomSheet.BottomsheetMenuPembayaran
import com.example.atlas.BottomSheet.BottomsheetPengiriman
import com.example.atlas.R
import com.example.atlas.databinding.FragmentCheckOutBinding
import com.example.atlas.databinding.FragmentFirstBinding

class CheckOutFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentCheckOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLanjutBayar.setOnClickListener {
        findNavController().navigate(R.id.action_checkoutFragment_to_FirstFragment)
        }
        binding.showEkspedisiList.setOnClickListener{
            setupListEkspedisiBottomSheet()
        }
        binding.ivFragmentPaymentBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.showPaymentListButton.setOnClickListener{
            setupListMenuPembayaranBottomSheet()
        }
    }
    private fun setupListEkspedisiBottomSheet() {

        val listTokoBottomSheet = BottomsheetPengiriman(
        )

        listTokoBottomSheet.show(
            (activity as AppCompatActivity).supportFragmentManager,
            "PaymentMethodListTag"
        )
    }

    private fun setupListMenuPembayaranBottomSheet() {

        val listTokoBottomSheet = BottomsheetMenuPembayaran(
        )

        listTokoBottomSheet.show(
            (activity as AppCompatActivity).supportFragmentManager,
            "PaymentMethodListTag"
        )
    }
}