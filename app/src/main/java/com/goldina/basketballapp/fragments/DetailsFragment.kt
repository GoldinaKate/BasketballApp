package com.goldina.basketballapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.goldina.basketballapp.R
import com.goldina.basketballapp.databinding.FragmentDetailsBinding
import com.goldina.basketballapp.databinding.LayoutQuarterBinding
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.models.response.Quarter
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
        binding.layoutScore.apply {
            val scores = currentMatch.scores
            setScoreQuarter(scores.firstQuarter[0])
            tvScoreFirst.text = setScoreQuarter(scores.firstQuarter[0])
            setDataQuarter(layoutFirstQuarter,scores.firstQuarter[0])

            setScoreQuarter(scores.secondQuarter[0])
            tvScoreSecond.text = setScoreQuarter(scores.secondQuarter[0])
            setDataQuarter(layoutSecondQuarter,scores.secondQuarter[0])

            setScoreQuarter(scores.thirdQuarter[0])
            tvScoreThird.text = setScoreQuarter(scores.thirdQuarter[0])
            setDataQuarter(layoutThirdQuarter,scores.thirdQuarter[0])

            setScoreQuarter(scores.fourthQuarter[0])
            tvScoreFourth.text = setScoreQuarter(scores.fourthQuarter[0])
            setDataQuarter(layoutFourthQuarter,scores.fourthQuarter[0])
        }
        binding.headerContent.apply {
            tgFavourite.visibility = View.VISIBLE
            checkStateOfMatch()
            tgFavourite.setOnCheckedChangeListener { _, isChecked ->
                changeData(isChecked)
            }
            tvFixtureCountryName.text=currentMatch.country_name
            tvFixtureLeague.text=currentMatch.league_name
            tvFixtureStatus.text = currentMatch.event_status
            val noLogoImg = getDrawable(requireContext(),R.drawable.no_photo)
            imgFixtureHomeTeam.load(currentMatch.event_home_team_logo) {
                crossfade(true)
                crossfade(1000)
                placeholder(noLogoImg)
            }
            imgFixtureAwayTeam.load(currentMatch.event_away_team_logo) {
                crossfade(true)
                crossfade(1000)
                placeholder(noLogoImg)
            }
            tvFixtureDate.text= getString(
                    R.string.date_match,
                    currentMatch.event_date, currentMatch.event_time)
            tvFixtureHomeTeamName.text = currentMatch.event_home_team
            tvFixtureAwayTeamName.text = currentMatch.event_away_team
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

    private fun setScoreQuarter(quarter: Quarter):String {
        return getString(R.string.score_match,quarter.score_home,quarter.score_away)
    }

    private fun setDataQuarter(layoutQuarter: LayoutQuarterBinding, quarter: Quarter) {
        layoutQuarter.pbHome.progress=quarter.score_home
        layoutQuarter.pbAway.progress=quarter.score_away
        val max = quarter.score_home+quarter.score_away
        layoutQuarter.pbAway.max=max
        layoutQuarter.pbHome.max=max
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