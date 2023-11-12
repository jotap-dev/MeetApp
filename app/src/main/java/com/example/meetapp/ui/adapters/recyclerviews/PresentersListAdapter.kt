package com.example.meetapp.ui.adapters.recyclerviews

import android.annotation.SuppressLint
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.example.meetapp.R
import com.example.meetapp.data.models.presenter.Presenter

class PresentersListAdapter : ListAdapter<Presenter, PresentersListAdapter.PresenterViewHolder>(PresenterComparator()) {

    private var presentersList = mutableListOf<Presenter>()

    fun setPresentersList(presenters: List<Presenter>) {
        this.presentersList = presenters.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenterViewHolder {
        return  PresenterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PresenterViewHolder, position: Int) {
        val current = presentersList[position]
        holder.bind(current)
    }

    class PresenterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imagemView: ImageView = itemView.findViewById(R.id.presenter_image)
        private val nomeView: TextView = itemView.findViewById(R.id.presenter_name)
        private val empresaView: TextView = itemView.findViewById(R.id.presenter_company)
        private val descricaoView: TextView = itemView.findViewById(R.id.presenter_description)

        @SuppressLint("SetTextI18n")
        fun bind(presenter: Presenter){
            nomeView.text = presenter.nome
            empresaView.text = presenter.empresa
            descricaoView.text = Html.fromHtml(presenter.descricao, Html.FROM_HTML_MODE_LEGACY).trim()
            Log.i("URL :" , presenter.imagem)
            /*Glide.with(itemView.context)
                .load(presenter.imagem)
                .placeholder(R.drawable.placeholder2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imagemView)*/
            imagemView.load("https://doity.com.br/media/doity/palestrantes/21449_palestrante.png") {
                crossfade(true)
                placeholder(R.drawable.placeholder2)
            }
        }

        companion object{
            fun create(parent: ViewGroup):  PresenterViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.presenter_item_list, parent, false)
                return PresenterViewHolder(view)
            }
        }
    }

    class PresenterComparator : DiffUtil.ItemCallback<Presenter>(){
        override fun areItemsTheSame(oldItem: Presenter, newItem: Presenter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Presenter, newItem: Presenter): Boolean {
            return oldItem.id == newItem.id && oldItem.nome == newItem.nome
        }

    }


}

