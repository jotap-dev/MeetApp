package com.example.meetapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetapp.data.models.colaborator.Colaborator
import com.example.meetapp.data.repositories.PresentersRepository
import com.example.meetapp.data.services.DataEventService
import com.example.meetapp.databinding.FragmentColaboratorsBinding
import com.example.meetapp.ui.adapters.recyclerviews.ColaboratorsAdapter
import com.example.meetapp.ui.viewmodels.ColaboratorViewModel
import com.example.meetapp.ui.viewmodels.ColaboratorViewModelFactory

class ColaboratorsFragment: Fragment() {

    private lateinit var adapter: ColaboratorsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentColaboratorsBinding
    private lateinit var colaboratorViewModel: ColaboratorViewModel
    private val dataEventService = DataEventService.getInstance()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentColaboratorsBinding.inflate(inflater)

            this.adapter = ColaboratorsAdapter()
            this.recyclerView = binding.recyclerViewColaborators
            recyclerView.adapter = this.adapter
            recyclerView.layoutManager = GridLayoutManager(view?.context, 2)

            colaboratorViewModel =  ViewModelProvider(this, ColaboratorViewModelFactory(
                PresentersRepository(dataEventService)
            )
            ).get(
                ColaboratorViewModel::class.java
            )

            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colaboratorViewModel.allColaboratorList.observe(viewLifecycleOwner, Observer { colaborators ->
            binding.loadingView.visibility = View.INVISIBLE
            updateColaboratorsList(colaborators)
        })

        colaboratorViewModel.errorMessages.observe(viewLifecycleOwner, Observer {
            //Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
            Log.i("ERRO : " , it)
            updateColaboratorsList(listOf())
        })

        colaboratorViewModel.allColaboratorList.observe(viewLifecycleOwner) { colaborators ->
            colaborators?.let { adapter.submitList(it) }
        }

    }

    override fun onResume() {
        super.onResume()

        colaboratorViewModel.getAllColaborators()
    }

    private fun updateColaboratorsList(colaborators: List<Colaborator>) {
        adapter.setColaboratorsList(colaborators)
    }

    companion object{
        fun getInstance(): ColaboratorsFragment{
            return ColaboratorsFragment()
        }

    }

}