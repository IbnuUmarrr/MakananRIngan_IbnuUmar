package com.Ibnuumar.makananringan_ibnuumar.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.Ibnuumar.makananringan_ibnuumar.R
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks


class SnacksListAdapter(
    private val onItemClickListener: (Snacks) -> Unit
): ListAdapter<Snacks, SnacksListAdapter.SnacksViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): SnacksViewHolder{
        return SnacksViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: SnacksViewHolder,position: Int){
        val snacks = getItem(position)
        holder.bind(snacks)
        holder.itemView.setOnClickListener{
            onItemClickListener(snacks)
        }
    }
    class SnacksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val nameTextView : TextView = itemView.findViewById(R.id.nameTextView)
        private val descTextView : TextView = itemView.findViewById(R.id.descTextView)
        private val flavorTextView : TextView = itemView.findViewById(R.id.flavorTextView)
        fun bind(snacks: Snacks?) {
            nameTextView.text = snacks?.name
            descTextView.text = snacks?.desc
            flavorTextView.text = snacks?.flavor


        }

        companion object{
            fun create(parent: ViewGroup): SnacksListAdapter.SnacksViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_snacks, parent, false)
                return SnacksViewHolder(view)
            }
        }
    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Snacks>(){
            override fun areItemsTheSame(oldItem: Snacks, newItem: Snacks): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Snacks, newItem: Snacks): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
    private fun SnacksListAdapter.SnacksViewHolder.bind(snacks: Snacks?) {

}




