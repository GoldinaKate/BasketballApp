package com.goldina.basketballapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldina.basketballapp.R
import com.goldina.basketballapp.adapters.FixtureAdapter
import com.goldina.basketballapp.databinding.FragmentHomeBinding
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.models.response.ResponseFixture
import com.goldina.basketballapp.utils.Constant.API_KEY
import com.goldina.basketballapp.utils.Constant.MET
import com.goldina.basketballapp.utils.DataState
import com.goldina.basketballapp.viewmodel.FixtureViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment(),FixtureAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var fixtureAdapter: FixtureAdapter
    private val viewModel: FixtureViewModel by viewModels()
    private lateinit var snack: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snack = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            "",
            Snackbar.LENGTH_INDEFINITE
        )
        loadData()
    }

    @SuppressLint("SimpleDateFormat")
    private fun loadData() {
        fixtureAdapter = FixtureAdapter(this)
        binding.rvFixture.apply {
            layoutManager=LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter=fixtureAdapter
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH,-1)
        val fromDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.MONTH,2)
        val toDate = dateFormat.format(calendar.time)
        viewModel.getFixture(MET,API_KEY, fromDate,toDate)
        viewModel.fixtureResponse.observe(requireActivity()) { response ->
            run{
                when(response){
                    is DataState.Success<ResponseFixture> -> {
                        displayProgressBar(false)
                        fixtureAdapter.matches = response.data!!.result
                    }
                    is DataState.Exception -> {
                        displayProgressBar(false)
                        displayError(response.exception.message)
                    }

                    is DataState.Loading -> {
                        displayProgressBar(true)
                    }
                    is DataState.Error -> {
                        displayProgressBar(false)
                        displayError(response.error)
                    }
                }
            }
        }
    }

    private fun displayError(message: String?) {
        if (message != null){
            showSnackBar(message)
        }else{
            showSnackBar(getString(R.string.issues_message))
        }
    }

    private fun showSnackBar(message: String) {
        snack.setAction("RETRY") {
            snack.dismiss()
            loadData()
        }
        snack.setText(message)
        snack.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snack.view.setBackgroundColor(ContextCompat.getColor(requireContext(), com.onesignal.R.color.material_blue_grey_800))
        snack.show()
    }

    private fun displayProgressBar(isDisplay: Boolean) {
        if(isDisplay){
            binding.progressFixture.visibility = View.VISIBLE
            binding.rvFixture.visibility = View.INVISIBLE
        }else{
            binding.progressFixture.visibility = View.GONE
            binding.rvFixture.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(match: Match) {
        val direction = HomeFragmentDirections
            .actionHomeFragmentToDetailsFragment(match)
        findNavController().navigate(direction)
    }


}
