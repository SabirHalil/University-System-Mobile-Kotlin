package com.example.universitysystem.ui.fragments.lectures

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class LectureTapAdapter(fm: FragmentManager, private var totalTabs:Int):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                StudentLecturesFragment()
            }
            1 -> {
                LectureSelectionFragment()
            }
            else ->  getItem(position)
        }
    }
}