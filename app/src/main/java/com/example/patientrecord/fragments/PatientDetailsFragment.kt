package com.example.patientrecord.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.patientrecord.MainViewModel
import com.example.patientrecord.R
import com.example.patientrecord.data.Patient
import com.example.patientrecord.databinding.FragmentPatientDetailsBinding

class PatientDetailsFragment : Fragment() {

    private var safeinding: FragmentPatientDetailsBinding? = null
    private val binding get() = safeinding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private var patient: Patient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        safeinding = FragmentPatientDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        patient = requireArguments().getSerializable("patient") as Patient ?: return


        patient?.let {

            binding.tvName.text = it.name
            binding.tvAge.text = it.age.toString() + " Years"
            binding.tvGender.text = "Gender:\n${it.gender}"
            binding.tvMobile.text = "Mobile:\n${it.mobile}"
            binding.tvEmail.text = "Email:\n${it.email}"
            binding.tvAddress.text = "Address:\n${it.address}"
            binding.tvPatientId.text = "Patient Id: ${it.id}"

            if (it.gender == "Male") {
                binding.imgGender.setImageResource(R.drawable.male)
            } else {
                binding.imgGender.setImageResource(R.drawable.female)
            }

            Glide.with(requireContext())
                .load(Uri.parse(it.photo))
                .centerCrop()
                .placeholder(R.drawable.user)
                .into(binding.imgUser)
        }


        binding.imgDelete.setOnClickListener {
            patient?.let {
                mainViewModel.deletePatient(it.id)
                activity?.onBackPressed()

            }
        }

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }


        binding.fbButton.setOnClickListener {
            patient?.let {
                val action =
                    PatientDetailsFragmentDirections.actionPatientDetailsFragmentToPatientUpdateFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }

    }
}