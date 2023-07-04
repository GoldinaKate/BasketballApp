package com.goldina.basketballapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.goldina.basketballapp.R
import com.goldina.basketballapp.databinding.ItemFixtureBinding
import com.goldina.basketballapp.models.response.Match

class FixtureAdapter(
    private val context:Context,
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
            tvFixtureCountryName.text=currentMatch.country_name
            tvFixtureLeague.text=currentMatch.league_name
            tvFixtureStatus.text = currentMatch.event_status
            imgFixtureHomeTeam.load(currentMatch.event_home_team_logo) {
                crossfade(true)
                crossfade(1000)
                error(R.drawable.no_photo)
            }
            imgFixtureAwayTeam.load(currentMatch.event_away_team_logo) {
                crossfade(true)
                crossfade(1000)
                error(R.drawable.no_photo)
            }
            tvFixtureDate.text= context
                .getString(R.string.date_match,
                    currentMatch.event_date, currentMatch.event_time)
            tvFixtureHomeTeamName.text = currentMatch.event_home_team
            tvFixtureAwayTeamName.text = currentMatch.event_away_team
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