package com.example.randomscanapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomscanapp.data.ItemInfo
import java.text.SimpleDateFormat
import java.util.*


class InfoRecyclerAdapter
    (context: Context?, data: List<ItemInfo>?) :
    RecyclerView.Adapter<InfoRecyclerAdapter.ViewHolder>() {

    private var mData: List<ItemInfo>? = data
    private var mInflater: LayoutInflater? = null
    private var mClickListener: ItemClickListener? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater?.inflate(R.layout.single_info_item, parent, false)!!
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemInfo = mData!![position]
        val barcode = itemInfo.barcode.value
        val color = itemInfo.color.value
        holder.barcodeAndColorTextView.text = "text = $barcode, textColor = $color"
        holder.typeTextView.text = itemInfo.type.value
        holder.dateAndTimeTextView.text = retrieveDateAndTime(itemInfo.generationTimestamp)
    }

    private fun retrieveDateAndTime(timestamp: Long): String {
        return SimpleDateFormat("dd.MM.yyyy (HH:mm:ss)", Locale.getDefault()).format(timestamp)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var barcodeAndColorTextView: TextView =
            itemView.findViewById(R.id.barcode_n_color_text_view)
        var typeTextView: TextView = itemView.findViewById(R.id.type_text_view)
        var dateAndTimeTextView: TextView = itemView.findViewById(R.id.date_and_time_text_view)

        override fun onClick(view: View?) {
            val colorResId = mData?.get(adapterPosition)?.color?.colorResId
            mClickListener?.onItemClick(colorResId)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): ItemInfo? {
        return mData?.get(id)
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(colorResId: Int?)
    }
}