package com.example.diploma_work

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ExcelExporter {

    fun exportDataToExcel(context: Context, userList: List<User>): String? {
        val workbook = HSSFWorkbook()
        val sheet = workbook.createSheet("User Data")

        val headerRow: Row = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Звання")
        headerRow.createCell(1).setCellValue("Прізвище")
        headerRow.createCell(2).setCellValue("Ім'я")
        headerRow.createCell(3).setCellValue("По батькові")
        headerRow.createCell(4).setCellValue("Години")
        headerRow.createCell(5).setCellValue("Наряди")
        headerRow.createCell(6).setCellValue("Примітки")

        var rowNumber = 1
        for (user in userList) {
            val row: Row = sheet.createRow(rowNumber++)
            var cellNumber = 0
            row.createCell(cellNumber++).setCellValue(user.rank)
            row.createCell(cellNumber++).setCellValue(user.surname)
            row.createCell(cellNumber++).setCellValue(user.name)
            row.createCell(cellNumber++).setCellValue(user.father)
            row.createCell(cellNumber++).setCellValue(user.time)
            row.createCell(cellNumber++).setCellValue(user.duty)
            row.createCell(cellNumber++).setCellValue(user.comment)
        }

        return try {
            val fileDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val filePath = File(fileDir, "users_data.xls")
            val fileOutputStream = FileOutputStream(filePath)
            workbook.write(fileOutputStream)
            fileOutputStream.close()
            Log.d("ExcelExporter", "Excel file exported successfully")
            filePath.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun openExcelFile(context: Context, file: File) {
        val fileUri: Uri = FileProvider.getUriForFile(context, "com.example.diploma_work.provider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, "application/vnd.ms-excel")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }
}
