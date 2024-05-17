package com.example.diploma_work

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.diploma_work.databinding.ActivityMainActivity6GraphicBinding
import java.io.File

class MainActivity6Graphic : AppCompatActivity() {

    private lateinit var binding: ActivityMainActivity6GraphicBinding
    private lateinit var adminDataBaseHelper: AdminDataBaseHelper
    private lateinit var usersDataBaseHelper: UsersDataBaseHelper
    private var adminId: Int = -1
    private var userId: Int = -1
    private var startDate: String = ""
    private var endDate: String = ""

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                generateAndDisplayExcelFile()
            } else {
                // Handle permission denied
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainActivity6GraphicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adminId = intent.getIntExtra("ADMIN_ID", -1)
        userId = intent.getIntExtra("USER_ID", -1)
        startDate = intent.getStringExtra("START_DATE") ?: ""
        endDate = intent.getStringExtra("END_DATE") ?: ""

        adminDataBaseHelper = AdminDataBaseHelper(this)
        usersDataBaseHelper = UsersDataBaseHelper(this)

        binding.buttonMain.setOnClickListener { navigateToActivity(MainActivity3Main::class.java) }
        binding.buttonStatistic.setOnClickListener { navigateToActivity(MainActivity5Statistic::class.java) }
        binding.buttonProfile.setOnClickListener { navigateToActivity(MainActivity4Profile::class.java) }

        requestPermissions()

        val fileName = "user_graphic.xlsx"
        val filePath = File(filesDir, fileName).absolutePath
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        binding.webView.loadUrl("file:///$filePath")
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.putExtra("ADMIN_ID", adminId)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSIONS)
        } else {
            generateAndDisplayExcelFile()
        }
    }

    private fun generateAndDisplayExcelFile() {
        val userDataFromDatabase = getUserDataFromDatabase()
        ExcelExporterGraphic.generateAndDisplayExcelFile(this, userDataFromDatabase, usersDataBaseHelper, startDate, endDate)
    }

    private fun getUserDataFromDatabase(): Map<Int, Pair<String, String>> {
        val users = usersDataBaseHelper.getUsersByAdminId(adminId)
        return users.associate { it.id to Pair(it.rank, it.surname) }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1
    }
}
