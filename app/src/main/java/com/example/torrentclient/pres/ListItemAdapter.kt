package com.example.torrentclient.pres

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ListItemAdapter(context: Context, arrayList: ArrayList<ListItemModel>) :
    ArrayAdapter<ListItemModel>(context, 0, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var currentItemView = convertView

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(context).inflate(com.example.torrentclient.R.layout.list_item, parent, false)
        }
        val currentPosition: ListItemModel? = getItem(position)

        val dateView = currentItemView!!.findViewById<TextView>(com.example.torrentclient.R.id.date)
        dateView.setText(currentPosition?.getDate())

        val nameView = currentItemView.findViewById<TextView>(com.example.torrentclient.R.id.name)
        nameView.setText(currentPosition?.getName())

        val sizeView = currentItemView.findViewById<TextView>(com.example.torrentclient.R.id.size)
        sizeView.setText(currentPosition?.getSize())


        return currentItemView
    }

}
