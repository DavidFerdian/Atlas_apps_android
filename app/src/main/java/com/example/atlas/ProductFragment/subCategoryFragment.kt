package com.example.atlas.ProductFragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.atlas.R
import com.example.atlas.Util.DeviceDimensionUtility
import com.example.atlas.databinding.FragmentFirstBinding
import com.example.atlas.databinding.FragmentSubCategoryBinding
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.CarouselType
import org.imaginativeworld.whynotimagecarousel.ImageCarousel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [subCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class subCategoryFragment : Fragment() {

    private var _binding: FragmentSubCategoryBinding? = null
    lateinit var homeContext: Context
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sampleProductCard.setOnClickListener{
            findNavController().navigate(R.id.action_subcategory_to_productDetailFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}