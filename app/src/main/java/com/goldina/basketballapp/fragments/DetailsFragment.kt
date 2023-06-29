package com.goldina.basketballapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goldina.basketballapp.databinding.FragmentDetailsBinding
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.viewmodel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var currentMatch: Match
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentMatch = args.match

        populateUI()
    }

    private fun populateUI() {
        binding.layoutScore.scores = currentMatch.scores
        binding.headerContent.apply {
            tgFavourite.visibility = View.VISIBLE
            checkStateOfMatch()
            tgFavourite.setOnCheckedChangeListener { _, isChecked ->
                changeData(isChecked)
            }
            match = currentMatch
            val score = currentMatch.event_final_result.split(" - ")
            tvFixtureHomeScore.text=score[0]
            tvFixtureAwayScore.text=score[1]
        }
        binding.btnWebView.setOnClickListener {
            val addUrl = "${currentMatch.country_name.lowercase()}/${currentMatch.league_name.lowercase()}"
            val direction = DetailsFragmentDirections
                .actionDetailsFragmentToWebViewFragment(addUrl)
            findNavController().navigate(direction)
        }
    }

    private fun checkStateOfMatch() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.isFavourite(currentMatch.event_key)
            withContext(Main) {
                binding.headerContent.tgFavourite.isChecked = count > 0
            }
        }
    }

    private fun changeData(state: Boolean) {
        if(state){
            viewModel.addMatch(currentMatch)
        }else {
            viewModel.deleteMatch(currentMatch.event_key)
        }
    }

}