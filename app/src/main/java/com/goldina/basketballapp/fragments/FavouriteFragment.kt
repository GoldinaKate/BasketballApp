package com.goldina.basketballapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldina.basketballapp.adapters.FixtureAdapter
import com.goldina.basketballapp.databinding.FragmentFavouriteBinding
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.viewmodel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), FixtureAdapter.OnIconFavClickListener {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var fixtureAdapter: FixtureAdapter
    private val viewModel: FavouriteViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateList()
        viewModel.listFavMatches.observe(viewLifecycleOwner){
            setupRV(it)
        }

    }

    private fun setupRV(matches: List<Match>) {
        fixtureAdapter = FixtureAdapter(null,this)
        binding.rvFixture.apply {
            layoutManager= LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter=fixtureAdapter
            fixtureAdapter.matches=matches
        }
    }

    override fun onIconFavClick(eventKey: Int) {
        viewModel.deleteMatch(eventKey)
    }


}