package com.example.meetapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.meetapp.R
import com.example.meetapp.data.repositories.PresentersRepository
import com.example.meetapp.data.services.DataEventService
import com.example.meetapp.databinding.FragmentHomePageBinding
import com.example.meetapp.ui.viewmodels.HomePageViewModel
import com.example.meetapp.ui.viewmodels.HomePageViewModelFactory

class HomePageFragment: Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private val dataEventService = DataEventService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomePageBinding.inflate(inflater)

        homePageViewModel =  ViewModelProvider(this, HomePageViewModelFactory(
            PresentersRepository(dataEventService)) ).get( HomePageViewModel::class.java)

        return binding.root
    }

    companion object{
        fun getInstance(): HomePageFragment{
            return HomePageFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePageViewModel.getAllNotifications()

        homePageViewModel.banner.observe(viewLifecycleOwner, Observer { banner ->
            //Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
            Log.i("ERRO : " , banner.toString())
            binding.imageView.load("https://doity.com.br/media/doity/eventos/apps/banners/evento-24043-banner.png") {
                crossfade(true)
                transformations(RoundedCornersTransformation(30f))
                placeholder(R.drawable.placeholder2)
            }
        })


    }

}