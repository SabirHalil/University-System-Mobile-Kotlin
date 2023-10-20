package com.example.universitysystem.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.StudentCertification
import com.example.universitysystem.data.models.StudentInfo
import com.example.universitysystem.data.models.StudentUpdatableAttribute
import com.example.universitysystem.data.repository.StudentRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(private val repository: StudentRepository):ViewModel() {
    private val _getStudentInfo = MutableLiveData<UiState<StudentInfo>>()
    val getStudentInfo :LiveData<UiState<StudentInfo>>
        get() =_getStudentInfo

    private val _getStudentCertification = MutableLiveData<UiState<StudentCertification>>()
    val getStudentCertification :LiveData<UiState<StudentCertification>>
        get() = _getStudentCertification

    private val _updateStudent = MutableLiveData<UiState<String>>()
    val updateStudent :LiveData<UiState<String>>
        get() =_updateStudent

    fun getStudentInfo( id:Int){
        _getStudentInfo.value = UiState.Loading
        repository.getStudentInfo(id){_getStudentInfo.value = it}
    }

    fun getStudentCertification(id:Int){
        _getStudentCertification.value = UiState.Loading
        repository.getStudentCertification(id){_getStudentCertification.value = it}
    }

    fun updateStudent(id: Int,student: StudentUpdatableAttribute){
        _updateStudent.value = UiState.Loading
        repository.updateStudent(id,student){_updateStudent.value = it}
    }
}