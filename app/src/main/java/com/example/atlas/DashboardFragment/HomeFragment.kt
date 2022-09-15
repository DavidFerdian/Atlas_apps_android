package com.example.atlas.DashboardFragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.atlas.APIRequestHandling.APIRequestClient
import com.example.atlas.BottomSheet.BottomsheetCategories
import com.example.atlas.DataClass.CategoriesListResponse
import com.example.atlas.DataClass.DataItem
import com.example.atlas.R
import com.example.atlas.RecyclerViewAdapter.CategoriesAdapter
import com.example.atlas.Util.DeviceDimensionUtility
import com.example.atlas.databinding.FragmentFirstBinding
import com.google.android.material.navigation.NavigationView
import com.id.centuryememberproject.util.SnackBarUtility
import kotlinx.android.synthetic.main.activity_main.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.CarouselType
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment(){

    private var _binding: FragmentFirstBinding? = null
    private lateinit var CategoriesService: Call<CategoriesListResponse>
    lateinit var homeContext: Context
    // This property is only valid between onCreateView and
    // onDestroyView.

    val binding get() = _binding!!
    private val sampleImages = intArrayOf(
        R.drawable.banner5,
        R.drawable.banner6,
        R.drawable.banner8,
        R.drawable.banner9,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        setupCarousel()
        getCategoriesList()

        binding.ivNavigationDrawerTrigger.setOnClickListener {
            findNavController().navigate(R.id.action_Homefragment_to_MenuFragment)
        }

        return binding.root

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sampleProductCard.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_productDetailFragment)
        }
    }
    private fun setupCarousel() {
        val carousel: ImageCarousel = binding.carouselView
        val list = mutableListOf<CarouselItem>()

        for (i in sampleImages.indices) {
            list.add(
                CarouselItem(
                    imageDrawable = sampleImages[i]
                )
            )
        }

        carousel.apply {
            scaleOnScroll = true
            showTopShadow = false
            showBottomShadow = false
            imageScaleType = ImageView.ScaleType.FIT_CENTER
            layoutParams = setCarouselDimension()
            showNavigationButtons = false
            carouselType = CarouselType.SHOWCASE
            autoPlay = false
            carouselBackground =
                ColorDrawable(ContextCompat.getColor(homeContext, R.color.white))
            addData(list)
        }
    }

    private fun setCarouselDimension(): RelativeLayout.LayoutParams {

        val screenHeight = DeviceDimensionUtility.getScreenHeight(homeContext, activity)
        val screenWidth = DeviceDimensionUtility.getScreenWidth(homeContext, activity)
        return RelativeLayout.LayoutParams(screenWidth, (0.24 * screenHeight).toInt())
    }
    fun setupCategoriesBottomSheet() {

        val CategoriesBottomSheet = BottomsheetCategories(
        )

        CategoriesBottomSheet.show(
            (activity as AppCompatActivity).supportFragmentManager,
            "PaymentMethodListTag"
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCategoriesList() {
        binding.pbCategories.visibility = View.VISIBLE
        binding.rvCategoriesList.visibility = View.GONE
        binding.rvButtonLainnya.visibility = View.GONE
        CategoriesService =
            APIRequestClient.CategoriesService()
                .getCategoriesList()


        CategoriesService.enqueue(object : Callback<CategoriesListResponse> {
            override fun onFailure(call: Call<CategoriesListResponse>, t: Throwable) {
                if (!call.isCanceled) {
                    SnackBarUtility.renderSnackBar(
                        binding.baseLayout,
                        homeContext.getString(R.string.api_request_failed_message)
                    )
                }
            }

            override fun onResponse(
                call: Call<CategoriesListResponse>,
                response: Response<CategoriesListResponse>
            ) {
                binding.pbCategories.visibility = View.GONE

                Log.i("munculapa",response.toString() )
                if (response.code() == 200 && response.body()?.data != null) {
                    binding.rvCategoriesList.visibility = View.VISIBLE
                    binding.rvButtonLainnya.visibility = View.VISIBLE
                    with(binding.rvCategoriesList) {
                        layoutManager =
                            GridLayoutManager(
                                (homeContext),
                                1,
                                GridLayoutManager.HORIZONTAL,
                                false
                            )
                        adapter = setupCategoriesList(response.body()?.data)
                    }

                    binding.baseLayout.visibility = View.VISIBLE
                    binding.rvCategoriesList.recycledViewPool.setMaxRecycledViews(0, 0)
                } else {
                    binding.rvCategoriesList.visibility = View.GONE
                    binding.rvButtonLainnya.visibility = View.GONE
                }

            }
        })
    }
    private fun setupCategoriesList(categories: List<DataItem?>?): CategoriesAdapter? {
        return categories?.let {
            CategoriesAdapter(
                homeContext,
                this@HomeFragment,
                it,
            )
        }
    }

}