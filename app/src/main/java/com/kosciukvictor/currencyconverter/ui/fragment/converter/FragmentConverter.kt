package com.kosciukvictor.currencyconverter.ui.fragment.converter

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.gson.internal.LinkedTreeMap
import com.kosciukvictor.currencyconverter.databinding.FragmentConverterBinding
import com.kosciukvictor.currencyconverter.domain.utils.KEY_FROM
import com.kosciukvictor.currencyconverter.domain.utils.KEY_TO
import com.kosciukvictor.currencyconverter.domain.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


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
        viewmodel.rates.observe(viewLifecycleOwner, Observer { apiData ->
            rates = apiData.rates as LinkedTreeMap<String, Double>
            val keysList: List<String> = ArrayList<String>(rates.keys)
            setUpSpinnerList(keysList)
        })

        viewmodel.prefCurr.observe(viewLifecycleOwner, Observer {
            setupSpinnerPosition(it)
        })
    }

    private fun setOnClickListeners() {
        binding.keyRemove.setOnLongClickListener {
            clear()
            true
        }
        binding.keyRefresh.setOnClickListener{
            viewmodel.getApiRates()
        }
    }


    private fun setUpSpinnerList(keyList: List<String>) {

        val mAdapter = ArrayAdapter<String>(
            this.requireContext(),
            R.layout.simple_spinner_item,
            keyList
        )
        mAdapter.setDropDownViewResource(R.layout.simple_list_item_multiple_choice)

        binding.spinnerTo.adapter = mAdapter
        binding.spinnerFrom.adapter = mAdapter

    }

    private fun setupSpinnerPosition(spinnerMap: Map<String, Int>) {
        val spinnerFromPosition = spinnerMap[KEY_FROM]
        spinnerFromPosition?.let {
            binding.spinnerFrom.setSelection(it)
        }

        binding.spinnerFrom.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent.getItemAtPosition(position) != null) {
                    saveSpinnerPosition(position, KEY_FROM)
                    updateSpinnerPosition()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val spinnerToPosition = spinnerMap[KEY_TO]
        spinnerToPosition?.let {
            binding.spinnerTo.setSelection(spinnerToPosition)
        }

        binding.spinnerTo.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent.getItemAtPosition(position) != null) {
                    saveSpinnerPosition(position, KEY_TO)
                    updateSpinnerPosition()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun updateSpinnerPosition() =
        viewmodel.getCurrencyPreferences()

    private fun saveSpinnerPosition(userChoice: Int, key: String) =
        viewmodel.saveCurrencyPreferences(key, userChoice)

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConverterBinding {
        val binding = FragmentConverterBinding.inflate(inflater, container, false)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding
    }


    private fun clear() =
        viewmodel.clear()
}
