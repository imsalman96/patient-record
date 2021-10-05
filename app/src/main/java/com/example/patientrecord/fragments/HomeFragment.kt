package com.example.patientrecord.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.patientrecord.MainViewModel
import com.example.patientrecord.utils.ItemClickListener
import com.example.patientrecord.adapters.PatentListAdapter
import com.example.patientrecord.data.Patient
import com.example.patientrecord.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var safebinding: FragmentHomeBinding? = null
    private val binding get() = safebinding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        safebinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fbButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPatientCreateFragment()
            findNavController().navigate(action)
        }


        val itemClickListener = object : ItemClickListener<Patient> {
            override fun onClick(item: Patient) {
                item.let {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToPatientDetailsFragment(it)
                    findNavController().navigate(action)
                }

            }
        }


        val patientAdapter = PatentListAdapter(itemClickListener)
        binding.rvPatient.adapter = patientAdapter

        mainViewModel.mPatients.observe(viewLifecycleOwner, {
            patientAdapter.setData(it)
        })


    }

    override fun onResume() {
        super.onResume()
        mainViewModel.loadPatientList()
    }
}