package com.example.torrentclient.pres

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.torrentclient.R


class ListItemAdapter(context: Context, arrayList: ArrayList<ListItemModel>) :
    ArrayAdapter<ListItemModel>(context, 0, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var currentItemView = convertView

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(context).inflate(com.example.torrentclient.R.layout.list_item, parent, false)
        }
        val currentPosition: ListItemModel? = getItem(position)

        val dateView = currentItemView!!.findViewById<TextView>(com.example.torrentclient.R.id.date)
        dateView.text = currentPosition?.getDate()

        val nameView = currentItemView.findViewById<TextView>(com.example.torrentclient.R.id.name)
        nameView.text = currentPosition?.getName()

        val sizeView = currentItemView.findViewById<TextView>(com.example.torrentclient.R.id.size)
        sizeView.text = currentPosition?.getSize()

        val imgView = currentItemView.findViewById<ImageView>(com.example.torrentclient.R.id.speed)

        val seed = currentPosition?.getSeed()
        val lich = currentPosition?.getLich()

        if (seed != null) {
            if (lich == 0 || seed / lich!! >= 0.7 && seed > 5)
                imgView.setImageResource(R.drawable.baseline_speed_24)
            else
                imgView.setImageResource(R.drawable.baseline_watch_later_24)
        }

        return currentItemView
    }

}
