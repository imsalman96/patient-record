package com.example.patientrecord.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.patientrecord.CameraActivity
import com.example.patientrecord.MainViewModel
import com.example.patientrecord.R
import com.example.patientrecord.data.Patient
import com.example.patientrecord.databinding.FragmentPatientUpdateBinding
import com.example.patientrecord.utils.Constants.TAG
import java.util.regex.Pattern

class PatientUpdateFragment : Fragment() {

    private var safebinding: FragmentPatientUpdateBinding? = null
    private val binding get() = safebinding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    private var photoUri = ""

    companion object {
        private const val TAG = "register"


        val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

    }
    private var gender = ""

    private var patient: Patient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        safebinding = FragmentPatientUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patient = requireArguments().getSerializable("update") as Patient ?: return


        patient?.let {

            binding.etPatientName.setText(it.name)
            binding.etPatientAge.setText(it.age.toString())
            binding.etMobileNumber.setText(it.mobile)
            binding.etUserEmail.setText(it.email)
            binding.etPatientAddress.setText(it.address)

            if (it.gender == "Male") {
                binding.gender.check(R.id.rbMale)
            } else {
                binding.gender.check(R.id.rbFemale)
            }

            gender = it.gender
            photoUri = it.photo
            binding.imgProfile.setLocalImage(Uri.parse(photoUri), true)

        }


        binding.gender.setOnCheckedChangeListener { radioGroup, id ->

            if (id == R.id.rbMale) {
                gender = "Male"
            } else {
                gender = "Female"
            }
        }

        binding.btnSave.setOnClickListener {

            if (photoUri.isEmpty()) {
                Toast.makeText(requireContext(), "Capture Photo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = binding.etPatientName.text.toString().trim()

            if (name.isEmpty()) {
                binding.textPatientName.error = "Enter Name"
                return@setOnClickListener
            }
            binding.textPatientName.error = null

            val ageText = binding.etPatientAge.text.toString().trim()

            if (ageText.isEmpty()) {
                binding.textPatientAge.error = "Enter Age"
                return@setOnClickListener
            }
            var age = 0
            try {
                age = ageText.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                binding.textPatientAge.error = "Invalid age"
                return@setOnClickListener

            }

            binding.textPatientAge.error = null

            if (gender.isEmpty()) {
                Toast.makeText(requireContext(), "Select Gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mobile = binding.etMobileNumber.text.toString().trim()
            if (mobile.length != 10) {
                binding.textMobileNumber.error = "Invalid Mobile"
                return@setOnClickListener
            }

            binding.textMobileNumber.error = null

            val email = binding.etUserEmail.text.toString().trim()

            if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()) {
                binding.textEmail.error = "Enter Valid Email"
                return@setOnClickListener
            }

            binding.textEmail.error = null

            val address = binding.etPatientAddress.text.toString().trim()

            if (address.isEmpty()) {
                binding.textPatientAddress.error = "Enter Address"
                return@setOnClickListener
            }

            binding.textPatientAddress.error = null

            val patient = Patient(name, age, gender, mobile, email, address, photoUri)
            patient.id = this.patient?.id ?: 0

            mainViewModel.savePatient(patient)

            Toast.makeText(requireContext(), "Patient Updated Successfully", Toast.LENGTH_SHORT)
                .show()

            findNavController().popBackStack(R.id.homeFragment,false)

        }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uri = result?.data?.getStringExtra("uri")
                    uri?.let {
                        photoUri = it
                        val msg = "Photo capture succeeded: $it"
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, msg)
                        binding.imgProfile.setLocalImage(Uri.parse(it), true)

                    }
                }
            }

        binding.btnTakePic.setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            resultLauncher.launch(intent)
        }


    }

    fun ImageView.setLocalImage(uri: Uri, applyCircle: Boolean = false) {
        val glide = Glide.with(this).load(uri)
        if (applyCircle) {
            glide.apply(RequestOptions.circleCropTransform()).into(this)
        } else {
            glide.into(this)
        }
    }

}
