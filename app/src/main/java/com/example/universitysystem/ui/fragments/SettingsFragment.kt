package com.example.universitysystem.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.universitysystem.data.models.StudentInfo
import com.example.universitysystem.data.models.StudentUpdatableAttribute
import com.example.universitysystem.databinding.FragmentSettingsBinding
import com.example.universitysystem.utils.StudentConstant.ID
import com.example.universitysystem.utils.UiState
import com.example.universitysystem.utils.hide
import com.example.universitysystem.utils.show
import com.example.universitysystem.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var studentInfo: StudentInfo
    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        getStudentObserver()
        studentViewModel.getStudentInfo(ID)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            if (validation()) {
                updateStudentObserver()
                val student = StudentUpdatableAttribute()
                student.address = binding.etAddress.text.toString()
                student.phone = binding.etPhone.text.toString()
                student.iban = binding.etIban.text.toString()
                studentViewModel.updateStudent(ID, student)
            }
        }
    }


    private fun validation(): Boolean {
        var isValid = true
        if (studentInfo.address == binding.etAddress.text.toString() && studentInfo.phone == binding.etPhone.text.toString() && studentInfo.iban == binding.etIban.text.toString()) {
            isValid = false
            requireActivity().toast("Can not update with same info!")
        } else {
            if (binding.etAddress.text.isNullOrEmpty() || binding.etPhone.text.isNullOrEmpty() || binding.etIban.text.isNullOrEmpty()) {
                isValid = false
                requireActivity().toast("You need to fill empty fields")
            }
        }
        return isValid
    }

    private fun updateStudentObserver() {
        studentViewModel.updateStudent.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                    binding.btnSave.text = ""
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSave.text = "Save"
                    requireActivity().toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    requireActivity().toast(state.data)
                    binding.btnSave.text = "Save"

                }
            }
        }
    }

    private fun getStudentObserver() {
        studentViewModel.getStudentInfo.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()

                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    requireActivity().toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    studentInfo = state.data
                    setupUi()
                }
            }
        }
    }

    private fun setupUi() {
        binding.tvName.text = "${studentInfo.firstName} ${studentInfo.lastName}"
        binding.tvNo.text = studentInfo.studentNo.toString()
        binding.etAddress.setText(studentInfo.address)
        binding.etPhone.setText(studentInfo.phone)
        binding.etIban.setText(studentInfo.iban)
        binding.tvGpa.text = studentInfo.gpa.toString()
        binding.tvGrade.text = studentInfo.grade.toString()

    }

}