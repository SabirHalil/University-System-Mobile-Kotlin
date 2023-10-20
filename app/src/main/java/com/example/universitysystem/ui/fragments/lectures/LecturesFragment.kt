package com.example.universitysystem.ui.fragments.lectures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.universitysystem.databinding.FragmentLecturesBinding
import com.example.universitysystem.utils.StudentConstant.ID
import com.example.universitysystem.utils.StudentConstant.TOKEN
import com.example.universitysystem.utils.UiState
import com.example.universitysystem.utils.hide
import com.example.universitysystem.utils.show
import com.example.universitysystem.utils.toast
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LecturesFragment : Fragment(){
private lateinit var binding:FragmentLecturesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLecturesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTapLayout()
    }

    private fun setupTapLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("My Lectures"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lecture Selection"))

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = LectureTapAdapter(childFragmentManager, binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }


//    private fun setupUi(){
//        studentLecturesObserver()
//        allLectureObserver()
//        lecturesViewModel.getAllLectures(TOKEN!!)
//        lecturesViewModel.getStudentLectures(TOKEN!!, ID)
//
//    }
//
//    private fun studentLecturesObserver(){
//        lecturesViewModel.getStudentLectures.observe(viewLifecycleOwner){ state ->
//            when (state) {
//                is UiState.Loading -> {
//                    binding.pbStudentLectures.show()
//                }
//                is UiState.Failure -> {
//                    binding.pbStudentLectures.hide()
//                    requireActivity().toast(state.error)
//                }
//                is UiState.Success -> {
//                    binding.pbStudentLectures.hide()
//                    val adapter = StudentLecturesAdapter(state.data,this)
//                    binding.rvStudentLecture.layoutManager = LinearLayoutManager(requireContext())
//                    binding.rvStudentLecture.adapter = adapter
//                }
//            }
//        }
//    }
//
//    private fun allLectureObserver(){
//        lecturesViewModel.getAllLectures.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is UiState.Loading -> {
//                   binding.pbAllLectures.show()
//                }
//                is UiState.Failure -> {
//                    binding.pbAllLectures.hide()
//                    requireActivity().toast(state.error)
//                }
//                is UiState.Success -> {
//                    binding.pbAllLectures.hide()
//                    val adapter = LectureAdapter(state.data,this)
//                    binding.rvAllLecture.layoutManager = LinearLayoutManager(requireContext())
//                    binding.rvAllLecture.adapter = adapter
//                }
//            }
//        }
//    }
//
//
//
//    override fun onAddClicked(lectureId: Int) {
//        val lecture = Lectures(id = 0, studentId = ID, lectureId = lectureId, vize = 0, final = 0)
//        addLectureObserver()
//        lecturesViewModel.addLecture(TOKEN!!,lecture)
//    }
//
//    private fun addLectureObserver() {
//        lecturesViewModel.addLecture.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is UiState.Loading -> {
//                    binding.pbAllLectures.show()
//                }
//                is UiState.Failure -> {
//                    binding.pbAllLectures.hide()
//                    requireActivity().toast(state.error)
//                }
//                is UiState.Success -> {
//                    binding.pbAllLectures.hide()
//                    lecturesViewModel.getStudentLectures(TOKEN!!, ID)
//                }
//            }
//        }
//    }
//
//    override fun onDeleteClicked(lectureId: Int) {
//        deleteLectureObserver()
//        lecturesViewModel.deleteLecture(TOKEN!!,ID,lectureId)
//    }
//
//    private fun deleteLectureObserver() {
//        lecturesViewModel.addLecture.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is UiState.Loading -> {
//                    binding.pbStudentLectures.show()
//                }
//                is UiState.Failure -> {
//                    binding.pbStudentLectures.hide()
//                    requireActivity().toast(state.error)
//                }
//                is UiState.Success -> {
//                    binding.pbStudentLectures.hide()
//                }
//            }
//        }
//    }


}