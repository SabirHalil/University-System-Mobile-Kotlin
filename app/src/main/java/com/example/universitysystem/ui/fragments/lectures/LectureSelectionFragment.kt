package com.example.universitysystem.ui.fragments.lectures

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universitysystem.R
import com.example.universitysystem.adapters.LectureSelectionAdapter
import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.data.models.StudentLecture
import com.example.universitysystem.databinding.FragmentLectureSelectionBinding
import com.example.universitysystem.utils.*
import com.example.universitysystem.utils.StudentConstant.ID
import com.example.universitysystem.utils.StudentConstant.TOKEN
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class LectureSelectionFragment : Fragment(), LectureSelectionAdapter.OnAddOrDeleteClicked {
    private lateinit var binding: FragmentLectureSelectionBinding
    private lateinit var availableLecturesAdapter: LectureSelectionAdapter
    private lateinit var selectedLectureAdapter: LectureSelectionAdapter
    private var availableLectureList = ArrayList<Lecture>()
    private var selectedLectureList = ArrayList<Lecture>()
    private val lectureViewModel: LectureViewModel by viewModels()
    private lateinit var animationFadeIn: Animation
    private lateinit var animationFadeOut: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Animation
        animationFadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        animationFadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLectureSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        availableLectureObserver()
        addLecturesObserver()
        lectureViewModel.getAllLectures( ID)
        setupSelectedAdapter()

        binding.btnSave.setOnClickListener {
            if (selectedLectureList.size > 0){
                println("save button clicked")
                lectureViewModel.addLecture(setupLecturesList())
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addLecturesObserver() {
        lectureViewModel.addLecture.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.pbAvailableLectures.show()
                }
                is UiState.Failure -> {
                    requireActivity().toast(state.error)
                    binding.pbAvailableLectures.hide()
                }
                is UiState.Success -> {
                    binding.pbAvailableLectures.hide()
                    selectedLectureList.clear()
                    binding.btnSave.startAnimation(animationFadeIn)
                    binding.tvWarnSelectedRv.text = "Lectures have added successfully!"
                    binding.tvWarnSelectedRv.startAnimation(animationFadeOut)
                    Handler().postDelayed({
                        binding.tvWarnSelectedRv.show()
                        binding.btnSave.hide()
                    },500)
                    selectedLectureAdapter.notifyDataSetChanged()

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupLecturesList(): ArrayList<StudentLecture> {
        val lectures = ArrayList<StudentLecture>()
        val semesterId = requireContext().getCurrentSemester().first
        val year = LocalDate.now().year
                for(lecture in selectedLectureList){
                    val l = StudentLecture(lectureId = lecture.id, studentId = ID, semesterId = semesterId, takenYear = year)
                    lectures.add(l)
                }
        return  lectures

    }

    private fun availableLectureObserver() {
        lectureViewModel.getAllLectures.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.pbAvailableLectures.show()
                }
                is UiState.Failure -> {
                    binding.pbAvailableLectures.hide()
                    requireActivity().toast(state.error)
                }
                is UiState.Success -> {
                    binding.pbAvailableLectures.hide()
                    availableLectureList = state.data
                    if (availableLectureList.size > 0){
                        binding.tvWarnAvailableRv.hide()
                        availableLecturesAdapter = LectureSelectionAdapter(state.data, this, true)
                        binding.rvAvailableLecture.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvAvailableLecture.adapter = availableLecturesAdapter
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onAddOrDeleteClicked(id: Int, isAdd: Boolean) {

        if (isAdd) {
            if (selectedLectureList.size == 0) {
                binding.tvWarnSelectedRv.startAnimation(animationFadeOut)
                binding.btnSave.startAnimation(animationFadeIn)
                Handler().postDelayed({
                    binding.tvWarnSelectedRv.hide()
                    binding.btnSave.show()
                }, 500)

            }
            val lecture = availableLectureList[id]
            selectedLectureList.add(lecture)
            availableLectureList.remove(lecture)
            if (availableLectureList.size == 0) {
                binding.tvWarnAvailableRv.show()
                binding.tvWarnAvailableRv.startAnimation(animationFadeIn)
            }

        } else {
            if (availableLectureList.size == 0) {
                binding.tvWarnAvailableRv.startAnimation(animationFadeOut)
                Handler().postDelayed({
                    binding.tvWarnAvailableRv.hide()
                }, 500)

            }
            val lecture = selectedLectureList[id]
            selectedLectureList.remove(lecture)
            availableLectureList.add(lecture)
            if (selectedLectureList.size == 0) {
                binding.tvWarnSelectedRv.startAnimation(animationFadeIn)
                binding.btnSave.startAnimation(animationFadeOut)
                Handler().postDelayed({
                    binding.tvWarnSelectedRv.show()
                    binding.btnSave.hide()
                }, 500)

            }
        }
        selectedLectureAdapter.notifyDataSetChanged()
        availableLecturesAdapter.notifyDataSetChanged()
    }


    private fun setupSelectedAdapter() {
        selectedLectureAdapter = LectureSelectionAdapter(selectedLectureList, this, false)
        binding.rvSelectedLecture.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSelectedLecture.adapter = selectedLectureAdapter
    }


}