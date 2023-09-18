package com.example.torrentclient.pres

import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.example.torrentclient.R
import com.example.torrentclient.data.repo.TorrentRepoImpl
import com.example.torrentclient.domain.usecase.SearchUseCase

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<ListItemModel>
    lateinit var mylist: ArrayList<ListItemModel>
    private val filmsRepository = TorrentRepoImpl()
    private val searchUseCase = SearchUseCase(TorrentRepo = filmsRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mylist = searchUseCase.execute("").map{ ListItemModel(name = it?.name, date = it?.date, size = it?.size)} as ArrayList<ListItemModel>
        adapter = ListItemAdapter(this, mylist)

        listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchViewItem = menu.findItem(R.id.search_bar)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (mylist.isNotEmpty()) {
                    adapter.clear()
                    mylist = searchUseCase.execute(query).map{ ListItemModel(name = it?.name, date = it?.date, size = it?.size)} as ArrayList<ListItemModel>
                    adapter.addAll(mylist)
                } else {
                    Toast.makeText(this@MainActivity, "Not found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.clear()
                mylist = searchUseCase.execute(newText).map{ ListItemModel(name = it?.name, date = it?.date, size = it?.size)} as ArrayList<ListItemModel>
                adapter.addAll(mylist)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}