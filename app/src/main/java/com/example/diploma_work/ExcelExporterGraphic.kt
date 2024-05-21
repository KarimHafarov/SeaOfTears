package com.example.diploma_work

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ExcelExporterGraphic {

    fun generateAndDisplayExcelFile(
        context: Context,
        userDataFromDatabase: Map<Int, Pair<String, String>>,
        usersDataBaseHelper: UsersDataBaseHelper,
        startDate: String,
        endDate: String
    ) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("User Graphic")

        val currentDate = getCurrentDate()
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        writeHeaderRow(sheet, currentDate, currentMonth)
        writeUserDataRows(sheet, usersDataBaseHelper, userDataFromDatabase, startDate, endDate)

        val fileName = "user_graphic.xlsx"
        val fileDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val filePath = File(fileDir, fileName)

        try {
            FileOutputStream(filePath).use { outputStream ->
                workbook.write(outputStream)
            }
            openExcelFile(context, filePath)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save Excel file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeHeaderRow(sheet: XSSFSheet, currentDate: String, currentMonth: Int) {
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("User List as of $currentDate")

        val dateRow = sheet.createRow(1)
        for (i in 1..31) {
            val cell = dateRow.createCell(i)
            cell.setCellValue(String.format("%02d/%02d", i, currentMonth))
        }
    }

    private fun writeUserDataRows(sheet: XSSFSheet, usersDataBaseHelper: UsersDataBaseHelper, userDataFromDatabase: Map<Int, Pair<String, String>>, startDate: String, endDate: String) {
        val highlightStyle = sheet.workbook.createCellStyle().apply {
            fillPattern = FillPatternType.SOLID_FOREGROUND
            fillForegroundColor = IndexedColors.BLACK.index
        }

        userDataFromDatabase.forEach { (userId, userData) ->
            val userRow = sheet.createRow(sheet.lastRowNum + 1)
            userRow.createCell(0).setCellValue("${userData.first} ${userData.second}")

            val dateRange = usersDataBaseHelper.getUserDateRange(userId)

            dateRange?.let { (start, end) ->
                val startCal = Calendar.getInstance().apply { time = start }
                val endCal = Calendar.getInstance().apply { time = end }

                for (i in 1..31) {
                    val cell = userRow.createCell(i)
                    val cellDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, startCal.get(Calendar.YEAR))
                        set(Calendar.MONTH, startCal.get(Calendar.MONTH))
                        set(Calendar.DAY_OF_MONTH, i)
                    }
                    if (!cellDate.before(startCal) && !cellDate.after(endCal) || cellDate.equals(endCal)) {
                        cell.cellStyle = highlightStyle
                    }
                }
            }
        }
    }

    private fun openExcelFile(context: Context, file: File) {
        val fileUri = FileProvider.getUriForFile(context, "com.example.diploma_work.provider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(fileUri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No application available to view Excel files", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
