package com.kosciukvictor.currencyconverter.ui.fragment.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap
import com.kosciukvictor.currencyconverter.databinding.FragmentRatesBinding
import com.kosciukvictor.currencyconverter.domain.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RatesListFragment : Fragment() {

    private val viewmodel: MainViewModel by sharedViewModel()
    private lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentRatesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = setupBinding(inflater, container)
        recyclerView = binding.ratesList
        recyclerView.layoutManager = LinearLayoutManager(activity)

        subscribeObservers()
        return binding.root
    }

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRatesBinding {
        val binding = FragmentRatesBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding
    }

    private fun subscribeObservers() {
        viewmodel.rates.observe(viewLifecycleOwner, Observer {
            setupRatesList(it.rates as LinkedTreeMap<String, Double>)
        })
    }

    private fun setupRatesList(ratesTree: LinkedTreeMap<String, Double>) {
        val ratesList: List<Double> = ArrayList<Double>(ratesTree.values)
        val keysList: List<String> = ArrayList<String>(ratesTree.keys)
        recyclerView.adapter = RatesListAdapter(ratesList, keysList)
    }
}

