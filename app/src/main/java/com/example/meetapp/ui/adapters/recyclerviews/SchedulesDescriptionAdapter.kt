package com.example.meetapp.ui.adapters.recyclerviews

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.meetapp.R
import com.example.meetapp.data.models.schedule.Schedule

class SchedulesDescriptionAdapter : ListAdapter<Schedule, SchedulesDescriptionAdapter.ScheduleDescriptionViewHolder>(ScheduleComparator()) {

    private var list = mutableListOf<Schedule>()

    fun setData(itemList: List<Schedule>){
        this.list = itemList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDescriptionViewHolder{
        return ScheduleDescriptionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ScheduleDescriptionViewHolder, position: Int) {
        val current = list[position]
        holder.bind(current)
    }
    override fun getItemCount(): Int = list.size

    class ScheduleDescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val scheduleName: TextView = itemView.findViewById(R.id.schedule_name)
        private val scheduleLocate: TextView = itemView.findViewById(R.id.schedule_locate)
        private val scheduleDescription: TextView = itemView.findViewById(R.id.schedule_description)
        private val scheduleCustomBadge: LinearLayout = itemView.findViewById(R.id.schedule_custom_badge)
        private val scheduleType: TextView = itemView.findViewById(R.id.schedule_type)
        private val scheduleDate: TextView = itemView.findViewById(R.id.schedule_date)
        private val endAt: TextView = itemView.findViewById(R.id.end_at)
        private val startAt: TextView = itemView.findViewById(R.id.start_at)
        fun bind(schedule: Schedule){
            scheduleName.text = schedule.atividadeNome
            scheduleDate.text = ""
            scheduleLocate.text = schedule.local
            scheduleDescription.text = schedule.descricao
            scheduleType.text = schedule.atividadeBloco
            scheduleCustomBadge.setBackgroundColor(Color.parseColor(schedule.cor))
            endAt.text = ""
            startAt.text = ""
        }

        companion object{
            fun create(parent: ViewGroup): ScheduleDescriptionViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.schedule_item_list, parent, false)
                return ScheduleDescriptionViewHolder(view)
            }
        }

    }

}