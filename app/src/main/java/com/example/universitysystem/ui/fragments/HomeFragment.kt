package com.example.universitysystem.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universitysystem.R
import com.example.universitysystem.adapters.AnnouncementAdapter
import com.example.universitysystem.adapters.FaqAdapter
import com.example.universitysystem.adapters.MealsAdapter
import com.example.universitysystem.adapters.NotificationAdapter
import com.example.universitysystem.data.models.Announcement
import com.example.universitysystem.data.models.ChangePasswordBody
import com.example.universitysystem.data.models.StudentCertification
import com.example.universitysystem.databinding.*
import com.example.universitysystem.ui.activities.AuthViewModel
import com.example.universitysystem.ui.activities.LoginActivity
import com.example.universitysystem.utils.*
import com.example.universitysystem.utils.StudentConstant.ID
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment(), OnMapReadyCallback{
    private lateinit var binding: FragmentHomeBinding
    private val announcementViewModel: AnnouncementViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()
    private val faqViewModel: FaqViewModel by viewModels()
    private val mealViewModel: MealViewModel by viewModels()
    private val changePasswordViewModel: AuthViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    private lateinit var announcementAdapter: AnnouncementAdapter
    private lateinit var generalAnnouncementList: ArrayList<Announcement>
    private lateinit var studentAnnouncementList: ArrayList<Announcement>
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mMap : GoogleMap
    private val requestCode = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getAnnouncementsObserver()

        announcementViewModel.getAnnouncements()

        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle = ActionBarDrawerToggle(requireActivity(), binding.root, android.R.string.ok, android.R.string.no)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (generalAnnouncementList.isNotEmpty()) {
                when (checkedId) {
                    binding.rbGeneral.id -> {
                        setupAdapter(generalAnnouncementList)
                    }
                    binding.rbStudent.id -> {
                        getStudentAnnouncement()
                    }
                }
                announcementAdapter.notifyDataSetChanged()
            }
        }
        binding.ivNotification.setOnClickListener {
            notificationBottomSheet()
        }

        binding.ivMenu.setOnClickListener {
            toggleDrawer()
        }

        binding.navigationDrawer.setNavigationItemSelectedListener {
            when (it.itemId){
            R.id.dining_item ->{ NavigationDrawer().showMealsList() }
            R.id.map_item ->{ NavigationDrawer().showMapDialog()}
            R.id.email_item ->{ NavigationDrawer().sendEmail()}
            R.id.rate_item ->{ NavigationDrawer().rateApp()}
            R.id.question_answer_item ->{ NavigationDrawer().showQuestionAnswerDialog() }
            R.id.internship_item ->{ NavigationDrawer().showInternshipDialog() }
            R.id.download_student_certificate_item ->{ NavigationDrawer().showDownloadCertificate() }
            R.id.change_password_item ->{NavigationDrawer().showChangePasswordDialog()}
            R.id.logout_item ->{NavigationDrawer().logout()}
            }
            toggleDrawer()
            true
        }
    }

    private fun getAnnouncementsObserver() {
        announcementViewModel.announcements.observe(viewLifecycleOwner) { state ->
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
                    generalAnnouncementList = state.data
                    setupUi(generalAnnouncementList)
                }
            }
        }
    }

    private fun setupUi(generalAnnouncementList: ArrayList<Announcement>) {

        when (binding.radioGroup.checkedRadioButtonId) {
            binding.rbGeneral.id -> {
                setupAdapter(generalAnnouncementList)
            }
            binding.rbStudent.id -> {
                getStudentAnnouncement()
            }
        }
    }

    private fun setupAdapter(list: ArrayList<Announcement>) {
        announcementAdapter = AnnouncementAdapter(list)
        binding.rvAnnouncements.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAnnouncements.adapter = announcementAdapter
    }

    private fun getStudentAnnouncement() {

        studentAnnouncementList =
            generalAnnouncementList.filter { it.type == 2 }.toCollection(ArrayList())

        setupAdapter(studentAnnouncementList)
    }

    private fun notificationBottomSheet() {
        val view = BottomSheetDialogBinding.inflate(layoutInflater, null, false)
        val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialog)
        dialog.setContentView(view.root)
        notificationViewModel.notifications.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    view.pbBottomSheet.show()

                }
                is UiState.Failure -> {
                    view.pbBottomSheet.hide()
                    requireActivity().toast(state.error)
                }
                is UiState.Success -> {
                    view.pbBottomSheet.hide()

                    if (state.data.isNotEmpty()) {
                        val adapter = NotificationAdapter(state.data)
                        view.rvBottomSheet.layoutManager = LinearLayoutManager(requireContext())
                        view.rvBottomSheet.adapter = adapter
                    }
                }
            }
        }
        notificationViewModel.getNotifications(ID)
        dialog.show()
    }

    private fun toggleDrawer() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val list = MapList.getMapList()
        for (i in list){
            mMap.addMarker(MarkerOptions().position(i.coordinate).title(i.title))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(i.coordinate,14f))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                NavigationDrawer().openDownloadPermission()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class NavigationDrawer{
        fun showMealsList() {
            val customDialog = Dialog(requireContext())
            val dialogBinding = MealDialogBinding.inflate(layoutInflater)
            customDialog.setContentView(dialogBinding.root)
            customDialog.setCanceledOnTouchOutside(true)
            customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            customDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            mealObserver(dialogBinding)
            mealViewModel.getMeals()
            customDialog.show()
        }
        private fun mealObserver(dialogBinding: MealDialogBinding) {
            mealViewModel.meals.observe(viewLifecycleOwner){state ->
                when (state) {
                    is UiState.Loading -> {

                        dialogBinding.progressBar.show()
                    }
                    is UiState.Failure -> {
                        dialogBinding.progressBar.hide()
                        requireActivity().toast(state.error)
                    }
                    is UiState.Success -> {
                        dialogBinding.progressBar.hide()
                        val adapter = MealsAdapter(state.data)
                        dialogBinding.rvMeals.layoutManager = LinearLayoutManager(requireContext())
                        dialogBinding.rvMeals.adapter = adapter
                    }
                }
            }
        }

        fun showMapDialog() {
            val customDialog = Dialog(requireContext())
            val dialogBinding = MapDialogBinding.inflate(layoutInflater)
            customDialog.setContentView(dialogBinding.root)
            customDialog.setCanceledOnTouchOutside(true)
            customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            customDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            val map = childFragmentManager.findFragmentById(R.id.my_map) as SupportMapFragment

            map.getMapAsync(this@HomeFragment)
            dialogBinding.ivBack.setOnClickListener {
                customDialog.hide()
            }
            customDialog.show()
        }

        fun sendEmail() {
            try {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("info@erciyes.edu.tr"))
                    putExtra(Intent.EXTRA_SUBJECT, "Type your title")
                }
                startActivity(
                    Intent.createChooser(
                        intent,
                        "Send Email Using: "
                    )
                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        fun rateApp() {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=tr.edu.erciyes")
                startActivity(this)
            }
        }

        fun showQuestionAnswerDialog() {
            val customDialog = Dialog(requireContext())
            val dialogBinding = FaqDialogBinding.inflate(layoutInflater)
            customDialog.setContentView(dialogBinding.root)
            customDialog.setCanceledOnTouchOutside(true)
            customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            customDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            faqObserver(dialogBinding)
            faqViewModel.getAnnouncements()
            dialogBinding.ivBack.setOnClickListener {
                customDialog.hide()
            }
            customDialog.show()
        }
        private fun faqObserver(dialogBinding: FaqDialogBinding) {
            faqViewModel.faq.observe(viewLifecycleOwner) { state ->
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
                        val adapter = FaqAdapter(state.data)
                        dialogBinding.rvFaq.layoutManager = LinearLayoutManager(requireContext())
                        dialogBinding.rvFaq.adapter = adapter
                    }
                }
            }
        }

        fun showInternshipDialog() {

        }


        fun showDownloadCertificate() {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Download student certificate")
                .setMessage("")
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Yes") { dialog, _ ->
                   openDownloadPermission()
                    dialog.dismiss()
                }
                .show()
        }

        fun openDownloadPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                val granted = PackageManager.PERMISSION_GRANTED

                if (ContextCompat.checkSelfPermission(requireContext(), permission) != granted) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(permission),
                        requestCode)
                } else {
                    getStudentCertificationObserver()
                    studentViewModel.getStudentCertification(ID)
                }
            }
        }
        private fun getStudentCertificationObserver() {
            studentViewModel.getStudentCertification.observe(viewLifecycleOwner) { state ->
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
                        generatePdf(state.data)
                    }
                }
            }
        }
        private fun generatePdf(studentCertification: StudentCertification) {
            try {
                val pdfTemplete = requireContext().assets.open("student_certification_templete.pdf")
                val reader = PdfReader(pdfTemplete)

                val downloadsDirectoryPath =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
                val filePath =
                    File("$downloadsDirectoryPath/${studentCertification.firstName}_${studentCertification.lastName}_${studentCertification.studentNo}_${LocalDate.now()}.pdf")
                val outputStream = FileOutputStream(filePath)

                val stamper = PdfStamper(reader, outputStream)
                val form = stamper.acroFields

                form.setField("full_name", "${studentCertification.firstName} ${studentCertification.lastName}")
                form.setField("father_name", studentCertification.dadName)
                form.setField("birth_place", studentCertification.birthPlace)
                form.setField("student_no",studentCertification.studentNo.toString())
                form.setField("grade",studentCertification.grade.toString())
                form.setField("faculty",studentCertification.facultyName)
                form.setField("mother_name",studentCertification.momName)
                form.setField("birth_date",requireContext().extractDateFromDateTime(studentCertification.birthDate))
                form.setField("id_card",studentCertification.idCard.toString())
                form.setField("year","3")
                form.setField("department",studentCertification.courseName)
                form.setField("enrollment_date",requireContext().extractDateFromDateTime(studentCertification.enrollmentDate))
                form.setField("issue_date", LocalDate.now().toString())
                form.setField("academic_year","2023-2024")
                form.setField("form_education","Face to face")
                form.setField("type_registry","Local exam")
                form.setField("type_preparatory","English")
                form.setField("period", requireContext().getCurrentSemester().second)
                form.setField("edited_time",requireContext().getCurrentDateTime())

                stamper.close()
                reader.close()
                outputStream.close()

                requireContext().toast("Student certification created successfully")
            }catch (e:Exception){
                requireContext().toast(e.localizedMessage)
            }

        }

        fun showChangePasswordDialog() {
            val customDialog = Dialog(requireContext())
            val dialogBinding = ChangePasswordDialogBinding.inflate(layoutInflater)
            customDialog.setContentView(dialogBinding.root)
            customDialog.setCanceledOnTouchOutside(true)
            customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            customDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            dialogBinding.btnSubmit.setOnClickListener {

                if (changePasswordValidation(dialogBinding)){
                    changePasswordObserver(dialogBinding, customDialog)
                    val changePasswordBody = ChangePasswordBody(ID,dialogBinding.etOldPassword.text.toString(),dialogBinding.etNewPassword.text.toString())
                    changePasswordViewModel.changePassword(changePasswordBody)
                }
            }
            customDialog.show()
        }
        private fun changePasswordValidation(dialogBinding: ChangePasswordDialogBinding): Boolean {
            var validate = true
            if (dialogBinding.etOldPassword.text.isNullOrEmpty()){
                validate = false
                requireContext().toast("You need to enter your old password")
            }else if (dialogBinding.etNewPassword.text.isNullOrEmpty()){
                validate = false
                requireContext().toast("You need to enter your new password")
            }else if (dialogBinding.etNewPasswordAgain.text.isNullOrEmpty()){
                validate = false
                requireContext().toast("You need to enter your new password again")
            }else if (dialogBinding.etNewPasswordAgain.text.toString() != dialogBinding.etNewPasswordAgain.text.toString()){
                validate = false
                requireContext().toast("Your new password does not match new password again")
                dialogBinding.etNewPasswordAgain.text?.clear()
            }
            return validate
        }

        private fun changePasswordObserver(dialogBinding: ChangePasswordDialogBinding, customDialog: Dialog, ) {
            changePasswordViewModel.changePassword.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Loading -> {
                        dialogBinding.submitProgress.show()
                    }
                    is UiState.Failure -> {
                        dialogBinding.submitProgress.hide()
                        requireActivity().toast(state.error)
                    }
                    is UiState.Success -> {
                        dialogBinding.submitProgress.hide()
                        requireActivity().toast(state.data)
                        customDialog.hide()
                    }
                }
            }
        }

        fun logout(){
            activity?.finish()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

    }



}