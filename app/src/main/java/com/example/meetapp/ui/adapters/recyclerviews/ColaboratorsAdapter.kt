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
import coil.load
import com.example.meetapp.R
import com.example.meetapp.data.models.colaborator.Colaborator
import com.example.meetapp.data.models.presenter.Presenter

class ColaboratorsAdapter: ListAdapter<Colaborator, ColaboratorsAdapter.ColaboratorViewHolder>(ColaboratorComparator()) {

    private var colaboratorsList = mutableListOf<Colaborator>()

    fun setColaboratorsList(presenters: List<Colaborator>) {
        this.colaboratorsList = presenters.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColaboratorViewHolder {
        return ColaboratorViewHolder.create(parent)
    }

    override fun getItemCount(): Int = colaboratorsList.size

    override fun onBindViewHolder(holder: ColaboratorViewHolder, position: Int) {
        val current = colaboratorsList[position]
        holder.bind(current)
    }

    class ColaboratorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val colaboratorImage: ImageView = itemView.findViewById(R.id.colaborator_image)
        private val colaboratorClass: TextView = itemView.findViewById(R.id.colaborator_class)

        @SuppressLint("SetTextI18n")
        fun bind(colaborator: Colaborator){
            colaboratorClass.text = colaborator.description.replace("\"", "")
            colaboratorImage.load(colaborator.imagem) {
                crossfade(true)
                placeholder(R.drawable.placeholder2)
            }
        }

        companion object{
            fun create(parent: ViewGroup):  ColaboratorViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.colaborator_item_list, parent, false)
                return ColaboratorViewHolder(view)
            }
        }
    }

    class ColaboratorComparator : DiffUtil.ItemCallback<Colaborator>(){
        override fun areItemsTheSame(oldItem: Colaborator, newItem: Colaborator): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Colaborator, newItem: Colaborator): Boolean {
            return oldItem.id == newItem.id && oldItem.ordem == newItem.ordem
        }

    }


}