package com.example.meetapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetapp.PresenterDescriptionActivity
import com.example.meetapp.data.models.presenter.Presenter
import com.example.meetapp.data.repositories.PresentersRepository
import com.example.meetapp.data.services.DataEventService
import com.example.meetapp.databinding.FragmentPresentersBinding
import com.example.meetapp.ui.adapters.recyclerviews.PresentersListAdapter
import com.example.meetapp.ui.viewmodels.PresenterViewModel
import com.example.meetapp.ui.viewmodels.PresenterViewModelFactory

class PresentersFragment: Fragment() {
    private val adapter = PresentersListAdapter{presenter ->
        //openPresenter(presenter)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentPresentersBinding
    private lateinit var presenterViewModel: PresenterViewModel
    private val dataEventService = DataEventService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPresentersBinding.inflate(inflater)

        this.recyclerView = binding.recyclerViewPresenters
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view?.context)

        presenterViewModel =  ViewModelProvider(this, PresenterViewModelFactory(PresentersRepository(dataEventService))).get(
                PresenterViewModel::class.java
        )

        return binding.root
    }

    companion object{
        fun getInstance(): PresentersFragment{
            return PresentersFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenterViewModel.allPresentersList.observe(viewLifecycleOwner, Observer { presenters ->
            binding.loadingView.visibility = View.INVISIBLE
            updatePresentersList(presenters)
        })

        presenterViewModel.errorMessages.observe(viewLifecycleOwner, Observer {
            //Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
            Log.i("ERRO : " , it)
            updatePresentersList(listOf())
        })

        presenterViewModel.allPresentersList.observe(viewLifecycleOwner) { presenters ->
            presenters?.let { adapter.submitList(it) }
        }

    }

    override fun onResume() {
        super.onResume()

        presenterViewModel.getAllPresenters()
    }

    private fun updatePresentersList(presenters: List<Presenter>) {

        adapter.setPresentersList(presenters)

    }

    private fun openPresenter(presenter: Presenter) {
        val id = presenter.id
        presenterViewModel.allPresentersList.observe(this) { presenters ->
            for (i in presenters) {
                if (i.id == id) {
                    startActivity(Intent(view?.context, PresenterDescriptionActivity::class.java)
                        .putExtra("nome", i.nome)
                        .putExtra("empresa", i.empresa)
                        .putExtra("image", i.imagem)
                        .putExtra("description", i.descricao)
                        .putExtra("schedules", i.atividades.toString())
                    )
                }
            }
        }


    }

}