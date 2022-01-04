package com.kosciukvictor.currencyconverter.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.internal.LinkedTreeMap
import com.kosciukvictor.currencyconverter.databinding.FragmentConverterBinding
import com.kosciukvictor.currencyconverter.domain.viewmodels.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class FragmentConverter : Fragment() {

    private val viewmodel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentConverterBinding

    lateinit var rates: LinkedTreeMap<String, Double>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = setupBinding(inflater, container)
        setOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() {
    }

    private fun setOnClickListeners() {
    }

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConverterBinding {
        val binding = FragmentConverterBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding
    }
}
