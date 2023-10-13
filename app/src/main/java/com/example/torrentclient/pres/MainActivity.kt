package com.example.torrentclient.pres

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.torrentclient.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<ListItemModel>
    var mylist: ArrayList<ListItemModel> = arrayListOf()

    private lateinit var vm: MainViewModel

    //Nav Drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

        actionBar = supportActionBar!!


        vm.livelist.observe(this) { data ->
            mylist = data
        }

        adapter = ListItemAdapter(this, mylist)

        listView = findViewById(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as ListItemModel
            //TODO
            selectedItem.getLink()?.let { vm.executeSendLinkUseCase(it, this) }
        }
        setNavigationViewListener()

        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume(){
        super.onResume()
        changeCat(0)
    }

    private fun changeCat(category: Int)
    {
        actionBar.title = vm.titleCategory[category]
        adapter.clear()
        //TODO
        vm.getChangeCategoryUseCase(category)
        adapter.addAll(mylist)
    }
    private fun setNavigationViewListener(){
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.any_category -> changeCat(0)
            R.id.films_foreign -> changeCat(1)
            R.id.music -> changeCat(2)
            R.id.another -> changeCat(3)
            R.id.serials_foreign -> changeCat(4)
            R.id.films_ru -> changeCat(5)
            R.id.television -> changeCat(6)
            R.id.animation -> changeCat(7)
            R.id.games -> changeCat(8)
            R.id.soft -> changeCat(9)
            R.id.anime -> changeCat(10)
            R.id.books -> changeCat(11)
            R.id.science -> changeCat(12)
            R.id.sport -> changeCat(13)
            R.id.work -> changeCat(14)
            R.id.humor -> changeCat(15)
            R.id.serials_ru -> changeCat(16)
            R.id.rel_foreign -> changeCat(17)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
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
                    //TODO
                    vm.getSearchUseCase(query)
                    adapter.addAll(mylist)
                } else {
                    Toast.makeText(this@MainActivity, "Not found", Toast.LENGTH_LONG).show()
                }
                return false
            }
            lateinit var currentText: String
            override fun onQueryTextChange(newText: String): Boolean {
                timer.cancel()
                timer.start()
                currentText = newText

                return false
            }
            var timer: CountDownTimer = object : CountDownTimer(800, 100) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    adapter.clear()
                    //TODO
                    vm.getSearchUseCase(currentText)
                    adapter.addAll(mylist)
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}