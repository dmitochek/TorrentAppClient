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
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.torrentclient.R
import com.example.torrentclient.data.repo.ChangeCategoryImpl
import com.example.torrentclient.data.repo.SendLinkImpl
import com.example.torrentclient.data.repo.TorrentRepoImpl
import com.example.torrentclient.domain.usecase.ChangeCategoryUseCase
import com.example.torrentclient.domain.usecase.SearchUseCase
import com.example.torrentclient.domain.usecase.SendLinkUseCase
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<ListItemModel>
    lateinit var mylist: ArrayList<ListItemModel>
    private val filmsRepository = TorrentRepoImpl()
    private val searchUseCase = SearchUseCase(TorrentRepo = filmsRepository)
    private val sendLink = SendLinkImpl()
    private val sendLinkUseCase = SendLinkUseCase(sendLink)
    private val changeCategory = ChangeCategoryImpl()
    private val changeCategoryUseCase = ChangeCategoryUseCase(changeCategory = changeCategory)

    private val titleCategory = arrayOf(
        "Любая категория",
        "Зарубежные фильмы",
        "Музыка",
        "Другое",
        "Зарубежные сериалы",
        "Наши фильмы",
        "Телевизор",
        "Мультипликация",
        "Игры",
        "Софт",
        "Аниме",
        "Книги",
        "Научно-Популярные",
        "Спорт и здоровье",
        "Хозяйство и быт",
        "Юмор",
        "Наши сериалы",
        "Иностранные релизы"
    )

    //Nav Drawer
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar = supportActionBar!!

        mylist = searchUseCase.execute("").map{ ListItemModel(name = it?.name, date = it?.date,
            size = it?.size, file_link = it?.file_link, lichers = it?.lichers, seeders = it?.seeders)} as ArrayList<ListItemModel>
        adapter = ListItemAdapter(this, mylist)

        listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as ListItemModel
            selectedItem.getLink()?.let { sendLinkUseCase.execute(it, this) }
            selectedItem.getLink()?.let { Log.d("TAG", it) }
        }
        setNavigationViewListener()

        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        changeCat(0)

    }
    fun changeCat(category: Int)
    {
        actionBar.title = titleCategory[category]
        adapter.clear()
        mylist = changeCategoryUseCase.execute(category = category).map{ ListItemModel(name = it?.name, date = it?.date,
            size = it?.size, file_link = it?.file_link, lichers = it?.lichers, seeders = it?.seeders)} as ArrayList<ListItemModel>
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
                    mylist = searchUseCase.execute(query).map{ ListItemModel(name = it?.name,
                        date = it?.date, size = it?.size, file_link = it?.file_link, lichers = it?.lichers, seeders = it?.seeders)}
                            as ArrayList<ListItemModel>
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
                    mylist = searchUseCase.execute(currentText).map{ ListItemModel(name = it?.name,
                        date = it?.date, size = it?.size, file_link = it?.file_link, lichers = it?.lichers, seeders = it?.seeders)}
                            as ArrayList<ListItemModel>
                    adapter.addAll(mylist)
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}