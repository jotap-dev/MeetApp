package com.example.meetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.meetapp.data.repositories.PresentersRepository
import com.example.meetapp.databinding.ActivityMainBinding
import com.example.meetapp.ui.adapters.viewpagers.MainViewPagerAdatpter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var mainViewPagerAdatpter: MainViewPagerAdatpter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = binding.viewPager
        mainViewPagerAdatpter = MainViewPagerAdatpter(this)
        viewPager2.adapter = mainViewPagerAdatpter

    }

    override fun onStart() {
        super.onStart()
        //inserindo funções de interação com usuário em onStart() para garantir performance

        //Alterar a página do view pager ao selecionar um item na bottom navigation view
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.event_calendar -> {
                    viewPager2.setCurrentItem(0, true)
                    true
                }

                R.id.home_page -> {
                    viewPager2.setCurrentItem(1, true)
                    true
                }

                R.id.presenters -> {
                    viewPager2.setCurrentItem(2, true)
                    true
                }

                else -> false
            }
        }
        //Alterar o item da bottom navigation view ao alterar a página pelo view pager
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.event_calendar
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.home_page
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.presenters
                }
                super.onPageSelected(position)
            }
        })



    }

    private fun decodeHTMLString(string: String): String{
        return Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}