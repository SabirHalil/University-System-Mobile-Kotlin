package com.example.universitysystem.ui.fragments.lectures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.data.models.StudentLecture
import com.example.universitysystem.data.models.StudentLectureDTO
import com.example.universitysystem.data.repository.LecturesRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LectureViewModel @Inject constructor(private val repository: LecturesRepository):ViewModel() {
    private val _getAllLectures = MutableLiveData<UiState<ArrayList<Lecture>>>()
    val getAllLectures : LiveData<UiState<ArrayList<Lecture>>>
        get() = _getAllLectures

    private val _getStudentLectures = MutableLiveData<UiState<ArrayList<StudentLectureDTO>>>()
    val getStudentLectures : LiveData<UiState<ArrayList<StudentLectureDTO>>>
        get() = _getStudentLectures

    private val _addLecture = MutableLiveData<UiState<String>>()
    val addLecture : LiveData<UiState<String>>
        get() = _addLecture

    private val _deleteLecture = MutableLiveData<UiState<String>>()
    val deleteLecture : LiveData<UiState<String>>
        get() = _deleteLecture


    fun getAllLectures(studentId: Int){
        _getAllLectures.value = UiState.Loading
        repository.getAllLectures(studentId){
            _getAllLectures.value = it
        }
    }

    fun getStudentLectures(studentId:Int){
        _getStudentLectures.value = UiState.Loading
        repository.getStudentLectures(studentId){
            _getStudentLectures.value = it
        }
    }

    fun addLecture(lecture: ArrayList<StudentLecture>){
        _addLecture.value = UiState.Loading
        repository.addLecture(lecture){
            _addLecture.value = it
        }
    }

    fun deleteLecture(studentId:Int, lectureId:Int){
        _deleteLecture.value = UiState.Loading
        repository.deleteLectures(studentId,lectureId){
            _deleteLecture.value = it
        }
    }

}