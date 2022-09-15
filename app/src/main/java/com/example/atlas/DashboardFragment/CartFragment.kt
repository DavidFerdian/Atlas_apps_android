package com.example.atlas.DashboardFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.atlas.R
import com.example.atlas.databinding.FragmentCartBinding
import com.example.atlas.databinding.FragmentFirstBinding

class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val binding get() = _binding!!
    private var _binding: FragmentCartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkOutButton.setOnClickListener{
            findNavController().navigate(R.id.action_CartFragment_to_CheckoutFragment)
        }
        binding.backToPreviousPageButton.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}