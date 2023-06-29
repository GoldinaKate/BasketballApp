package com.goldina.basketballapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goldina.basketballapp.databinding.ItemFixtureBinding
import com.goldina.basketballapp.models.response.Match

class FixtureAdapter(
    private val listenerItem: OnItemClickListener? = null,
    private val listenerIcon: OnIconFavClickListener? = null,
) : RecyclerView.Adapter<FixtureAdapter.RecipeViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(match: Match)
    }
    interface OnIconFavClickListener{
        fun onIconFavClick(eventKey: Int)
    }

    inner class RecipeViewHolder(val binding: ItemFixtureBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var matches: List<Match>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            ItemFixtureBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentMatch = matches[position]

        holder.binding.layoutBasicInfo.apply {
            match = currentMatch
            val score = currentMatch.event_final_result.split(" - ")
            tvFixtureHomeScore.text=score[0]
            tvFixtureAwayScore.text=score[1]

            if(listenerIcon!=null){
                tgFavourite.visibility = View.VISIBLE
                tgFavourite.setOnClickListener{
                    listenerIcon.onIconFavClick(currentMatch.event_key)
                }
            }
        }

        holder.itemView.setOnClickListener {
            listenerItem?.onItemClick(currentMatch)
        }

    }

    override fun getItemCount() = matches.size

}