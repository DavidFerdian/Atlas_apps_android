package com.example.atlas.ProductFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.atlas.BottomSheet.BottomsheetListToko
import com.example.atlas.R
import com.example.atlas.Util.DateFormatConverter
import com.example.atlas.databinding.FragmentProductDetailBinding
import kotlinx.android.synthetic.main.select_wishlist_date_dialog_layout.view.*
import kotlinx.android.synthetic.main.wishlist_item.view.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    lateinit var homeContext: Context
    private var selectedWishlistDate: String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.directBuyItemButton.setOnClickListener {
            setupListTokoBottomSheet()
        }
        binding.navigateToBasketButton.setOnClickListener {
            findNavController().navigate(R.id.action_productDetail_to_cartFragment)
        }
        binding.backToPreviousPageButton.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.addToWishlistButton.setOnClickListener {
            showWishlistDialog("hai")
    //            if (isUserLoggedIn) {
    //                showWishlistDialog(productDetail)
    //            } else {
    //            WarningDialogUtility.showLoginObligatoryWarning(
    //                homeContext,
    //                this@ProductDetailFragment
    //            )
        }
    }

    private fun setupListTokoBottomSheet() {

        val listTokoBottomSheet = BottomsheetListToko(
        )

        listTokoBottomSheet.show(
            (activity as AppCompatActivity).supportFragmentManager,
            "PaymentMethodListTag"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showWishlistDialog(product: String) {
        val mDialogView2 = LayoutInflater.from((homeContext)).inflate(
            R.layout.wishlist_item, null
        )

        mDialogView2.selectedItem.text = "product?.productName"

        mDialogView2.selectDateButton.setOnClickListener {
            val mDateDialogView = LayoutInflater.from((homeContext)).inflate(
                R.layout.select_wishlist_date_dialog_layout, null
            )

            val mBuilder = AlertDialog.Builder(homeContext)
                .setView(mDateDialogView)
                .setTitle(getString(R.string.selectWishlistDateDialogTitle))


            val mDateAlertDialog = mBuilder.show()

            //Get Calendar object and today's date
            val calendarObject = Calendar.getInstance()
            val year = calendarObject.get(Calendar.YEAR)
            val month = calendarObject.get(Calendar.MONTH)
            val day = calendarObject.get(Calendar.DAY_OF_MONTH)

            //Initially set the current selected date in the date picker as today's date
            var currentSelectedMonth = month
            var currentSelectedYear = year
            var currentSelectedDay = day


            var isAlreadyScrolledAway = false


            mDateDialogView.wishlistDatePicker.init(
                currentSelectedYear,
                currentSelectedMonth,
                currentSelectedDay
            ) { _, selectedYear, selectedMonth, selectedDay ->

                isAlreadyScrolledAway = true

                currentSelectedMonth = selectedMonth + 1
                currentSelectedYear = selectedYear
                currentSelectedDay = selectedDay
            }

            mDateDialogView.selectWishlistDateButton.setOnClickListener {

                if (!isAlreadyScrolledAway) {
                    currentSelectedMonth += 1
                }

                selectedWishlistDate = if (currentSelectedMonth < 10) {
                    if (currentSelectedDay < 10) {
                        "$currentSelectedYear-0${currentSelectedMonth}-0${currentSelectedDay}T00:00:00.000Z"
                    } else {
                        "$currentSelectedYear-0${currentSelectedMonth}-${currentSelectedDay}T00:00:00.000Z"
                    }

                } else {
                    if (currentSelectedDay < 10) {
                        "$currentSelectedYear-${currentSelectedMonth}-0${currentSelectedDay}T00:00:00.000Z"
                    } else {
                        "$currentSelectedYear-${currentSelectedMonth}-${currentSelectedDay}T00:00:00.000Z"
                    }
                }


                mDialogView2.tvSelectedDate.text = DateFormatConverter.convertDateFormat(
                    selectedWishlistDate.substringBefore('T')
                )

                mDateAlertDialog.dismiss()
            }
        }

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder((homeContext))
            .setView(mDialogView2)

        //show dialog
        val mAlertDialog = mBuilder.show()

        mDialogView2.confirmCategoryButton.setOnClickListener {
            if (selectedWishlistDate != "" && mDialogView2.setQtyEditText.text.toString()
                    .isNotEmpty()
            ) {

                val inputtedItemQty: Int = mDialogView2.setQtyEditText.text.toString().toInt()
//
//                val inputtedInformation =
//                    InsertWishlistRequestBody(
//                        product?.productPrice,
//                        product?.productDiscount,
//                        memberId,
//                        product?.productId,
//                        selectedWishlistDate,
//                        product?.productName,
//                        product?.productImage,
//                        inputtedItemQty,
//                        product?.productPriceDiscount
//                    )
//
//                binding?.baseLayout?.let { it1 ->
//                    WishlistUtility.insertNewWishlistItem(
//                        it1,
//                        productDetailContext,
//                        inputtedInformation,
//                        this
//                    )
//                }
//                mAlertDialog.dismiss()
//            } else {
//                Toast.makeText(
//                    productDetailContext,
//                    getString(R.string.wishlist_insertion_warning),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

                mDialogView2.discardCategoryButton.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }
        }
    }
}