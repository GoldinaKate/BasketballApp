package com.goldina.basketballapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.goldina.basketballapp.R
import com.goldina.basketballapp.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    private val fragmentScope = CoroutineScope(Dispatchers.Main)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        val animTop = AnimationUtils.loadAnimation(context,R.anim.from_top)
        val animBottom = AnimationUtils.loadAnimation(context,R.anim.from_bottom)
        binding.ivSplashIcon.animation = animTop
        binding.tvSplashName.animation = animBottom
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentScope.launch {
            delay(3000L)
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
    override fun onPause() {
        fragmentScope.cancel()
        super.onPause()
    }
}