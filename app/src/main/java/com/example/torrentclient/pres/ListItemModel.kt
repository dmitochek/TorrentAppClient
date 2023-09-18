package com.example.torrentclient.pres

class ListItemModel(private var date: String?, private var name: String?, private var size: String?, private var file_link: String?) {

    fun getDate(): String? {
        return date
    }
    fun getName(): String? {
        return name
    }
    fun getSize(): String? {
        return size
    }
    fun getLink(): String? {
        return file_link
    }
}
