package com.example.atlas.BottomSheet

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.atlas.APIRequestHandling.APIRequestClient
import com.example.atlas.DataClass.CategoriesListResponse
import com.example.atlas.DataClass.DataItem
import com.example.atlas.R
import com.example.atlas.RecyclerViewAdapter.SubCategoriesAdapter
import com.example.atlas.databinding.FragmentBottomsheetCategoriesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.id.centuryememberproject.util.SnackBarUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomsheetCategories : BottomSheetDialogFragment() {

    lateinit var bottomsheetContext: Context
    var binding: FragmentBottomsheetCategoriesBinding? = null
    private lateinit var CategoriesService: Call<CategoriesListResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_bottomsheet_categories, container, false
            )
        }
        getCategoriesList()
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomsheetContext = context
    }

    private fun getCategoriesList() {
        binding?.pbCategories?.visibility = View.VISIBLE
        binding?.rvCategoriesList?.visibility = View.GONE

        CategoriesService =
            APIRequestClient.CategoriesService()
                .getCategoriesList()


        CategoriesService.enqueue(object : Callback<CategoriesListResponse> {
            override fun onFailure(call: Call<CategoriesListResponse>, t: Throwable) {
                if (!call.isCanceled) {
                    SnackBarUtility.renderSnackBar(
                        binding!!.baseLayout,
                        bottomsheetContext.getString(R.string.api_request_failed_message)
                    )
                }
            }

            override fun onResponse(
                call: Call<CategoriesListResponse>,
                response: Response<CategoriesListResponse>
            ) {
                binding!!.pbCategories.visibility = View.GONE
                Log.i("munculapa", response.toString())
                if (response.code() == 200 && response.body()?.data != null) {
                    binding!!.rvCategoriesList.visibility = View.VISIBLE
                    with(binding!!.rvCategoriesList) {
                        layoutManager =
                            GridLayoutManager(
                                (bottomsheetContext),
                                5
                            )
                        adapter = setupCategoriesList(response.body()?.data)
                    }

                    binding!!.baseLayout.visibility = View.VISIBLE
                    binding!!.rvCategoriesList.recycledViewPool.setMaxRecycledViews(0, 0)
                } else {
                    binding!!.rvCategoriesList.visibility = View.GONE
                }

            }
        })
    }

    private fun setupCategoriesList(categories: List<DataItem?>?): SubCategoriesAdapter? {
        return categories?.let {
            SubCategoriesAdapter(
                bottomsheetContext,
                this@BottomsheetCategories,
                it,
            )
        }
    }
}