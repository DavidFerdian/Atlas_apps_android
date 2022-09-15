package com.example.atlas.RecyclerViewAdapter

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.atlas.BottomSheet.BottomsheetCategories
import com.example.atlas.DataClass.DataItem
import com.example.atlas.R
import kotlinx.android.synthetic.main.item_categories.view.*


class SubCategoriesAdapter(
    val context: Context,
    private val bottomsheetCategories: BottomsheetCategories,
    private var categoriesList: List<DataItem?>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class CategoriesHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        private val CategoriesImage: ImageView = itemView.CategoriesIcon
        private val categoriesName: TextView = itemView.tv_category_name

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            category: DataItem
        ) {

            //This is for showing the information in each recyclerview item
            val imageBytes =
                Base64.decode(category.categoryImg!!.substringAfter(","), Base64.DEFAULT)
            Log.i("muncul apa", category.categoryImg.substringAfter(",").toString())
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            CategoriesImage.setImageBitmap(decodedImage)
            categoriesName.text = category.categoryMainMenu
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoriesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_categories, parent, false)
        )

    }

    override fun getItemCount(): Int {
            return categoriesList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = categoriesList[position]
        val viewHolder = holder as CategoriesHolder
        viewHolder.bind(category!!)
    }

}
