package com.example.torrentclient.pres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.torrentclient.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(imageUrl: ArrayList<String?>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    var sliderList: ArrayList<String?> = imageUrl

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapter.SliderViewHolder {
        val inflate: View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, null)

        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapter.SliderViewHolder?, position: Int) {

        if (viewHolder != null) {
            Picasso.get().load(sliderList.get(position)).into(viewHolder.imageView)
        }
    }

    class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {

        var imageView: ImageView = itemView!!.findViewById(R.id.myimage)
    }
}