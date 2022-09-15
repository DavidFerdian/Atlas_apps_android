package com.example.atlas.RecyclerViewAdapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.atlas.DataClass.DataItemoutlet
import com.example.atlas.R
import kotlinx.android.synthetic.main.item_outlet_list.view.*


class OutletListAdapter(
    val context: Context,
    private var outletList: List<DataItemoutlet?>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class CategoriesHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        private val outletName: TextView = itemView.tv_outlet_name
        private val OutletAddress: TextView = itemView.tv_address
        private val outletTime: TextView = itemView.tv_time_start
        private val outletPhone: TextView = itemView.tv_phone_number
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            outlet: DataItemoutlet
        ) {

            //This is for showing the information in each recyclerview item
            outletName.text = outlet.outName
            OutletAddress.text = outlet.outAddress
            outletTime.text = outlet.outTimeopen + " - " + outlet.outTimeclose
            outletPhone.text = outlet.outPhone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoriesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_outlet_list, parent, false)
        )

    }

    override fun getItemCount(): Int {
            return outletList.size


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = outletList[position]
        val viewHolder = holder as CategoriesHolder
        viewHolder.bind(category!!)
    }

}
