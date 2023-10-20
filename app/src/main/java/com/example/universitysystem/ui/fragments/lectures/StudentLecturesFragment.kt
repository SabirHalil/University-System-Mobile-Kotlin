package com.example.universitysystem.ui.fragments.lectures

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universitysystem.R
import com.example.universitysystem.adapters.StudentLecturesAdapter
import com.example.universitysystem.data.models.StudentLectureDTO
import com.example.universitysystem.databinding.FragmentStudentLecturesBinding
import com.example.universitysystem.utils.StudentConstant.ID
import com.example.universitysystem.utils.UiState
import com.example.universitysystem.utils.hide
import com.example.universitysystem.utils.show
import com.example.universitysystem.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentLecturesFragment : Fragment(),StudentLecturesAdapter.OnDeleteLectureListener {
    lateinit var binding: FragmentStudentLecturesBinding
    private val lecturesViewModel: LectureViewModel by viewModels()
    private lateinit var passedLectures:ArrayList<StudentLectureDTO>
    private lateinit var lectures:ArrayList<StudentLectureDTO>
    private lateinit var lecturesAdapter:StudentLecturesAdapter
    private lateinit var passedLecturesAdapter:StudentLecturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentLecturesBinding.inflate(inflater, container, false)
        println("oncreateview ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lectureObserver()
        lectureDeleteObserver()
        lecturesViewModel.getStudentLectures(ID)
    }


    private fun lectureDeleteObserver() {
        lecturesViewModel.deleteLecture.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.pbStudentLectures.show()
                }
                is UiState.Failure -> {
                    binding.pbStudentLectures.hide()
                    requireActivity().toast(state.error)
                }
                is UiState.Success -> {
                    binding.pbStudentLectures.hide()
                    requireContext().toast(state.data)
                }
            }
        }
    }

    private fun lectureObserver() {
        lecturesViewModel.getStudentLectures.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.pbStudentLectures.show()
                }
                is UiState.Failure -> {
                    binding.pbStudentLectures.hide()
                    requireActivity().toast(state.error)
                }
                is UiState.Success -> {
                    binding.pbStudentLectures.hide()
                    setupAdapters(state.data)
                    println("lecture observer clicked")
                }
            }
        }
    }


    private fun setupAdapters(data: ArrayList<StudentLectureDTO>) {
         lectures = data.filter { it.passed == 0 }.toCollection(ArrayList())

         passedLectures = data.filter { it.passed == 1 }.toCollection(ArrayList())

        if (lectures.size >0){
            lecturesAdapter = StudentLecturesAdapter(lectures, false,this)
            binding.rvMyLectures.layoutManager = LinearLayoutManager(requireContext())
            binding.rvMyLectures.adapter = lecturesAdapter
            binding.tvNoStudentLecture.hide()
        }

        if (passedLectures.size > 0){
            passedLecturesAdapter = StudentLecturesAdapter(passedLectures, true)
            binding.rvPassedLectures.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPassedLectures.adapter = passedLecturesAdapter
            binding.tvNoPassedLecture.hide()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteLectureListener(lecture: StudentLectureDTO) {
        println(lecture.lectureCode)
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure to delete the lecture?")
            .setPositiveButton("Delete"){ dialog, _ ->

                lecturesViewModel.deleteLecture(ID,lecture.id)
                lectures.remove(lecture)
                lecturesAdapter.notifyDataSetChanged()
                if(lectures.size == 0)
                    binding.tvNoStudentLecture.show()
                }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create()
        builder.show()
    }

}