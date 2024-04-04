package com.example.diploma_work

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminDataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 23

        const val TABLE_ADMIN = "admins"
        const val COLUMN_ADMIN_ID = "_id"
        const val COLUMN_ADMIN_LOGIN = "login"
        const val COLUMN_ADMIN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createAdminTableQuery = """
            CREATE TABLE $TABLE_ADMIN (
                $COLUMN_ADMIN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ADMIN_LOGIN TEXT,
                $COLUMN_ADMIN_PASSWORD TEXT
            )
        """.trimIndent()

        db.execSQL(createAdminTableQuery)
    }

    fun insertAdmin(username: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_ADMIN_LOGIN, username)
            put(COLUMN_ADMIN_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(TABLE_ADMIN, null, values)
    }

    fun readAdmin(login: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_ADMIN_LOGIN = ? AND $COLUMN_ADMIN_PASSWORD = ?"
        val selectionArgs = arrayOf(login, password)
        val cursor = db.query(TABLE_ADMIN, null, selection, selectionArgs, null, null, null)

        val adminExists = cursor.count > 0
        cursor.close()
        return adminExists
    }

    fun getAdminId(login: String, password: String): Int {
        val db = readableDatabase
        var adminId = -1

        val selection = "$COLUMN_ADMIN_LOGIN = ? AND $COLUMN_ADMIN_PASSWORD = ?"
        val selectionArgs = arrayOf(login, password)

        val cursor = db.query(TABLE_ADMIN, null, selection, selectionArgs, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ADMIN_ID)
            adminId = cursor.getInt(idIndex)
        }

        cursor?.close()
        db.close()

        return adminId
    }

    fun getAdmin(): Admin? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_ADMIN LIMIT 1"
        val cursor = db.rawQuery(query, null)

        var admin: Admin? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_ID))
            val login = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_LOGIN))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_PASSWORD))
            admin = Admin(id, login, password)
        }

        cursor.close()
        db.close()

        return admin
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropAdminTableQuery = "DROP TABLE IF EXISTS $TABLE_ADMIN"
        db.execSQL(dropAdminTableQuery)
        onCreate(db)
    }

    fun getAdminById(adminId: Int): Admin? {
        val db = readableDatabase
        val selection = "$COLUMN_ADMIN_ID = ?"
        val selectionArgs = arrayOf(adminId.toString())

        val cursor = db.query(TABLE_ADMIN, null, selection, selectionArgs, null, null, null)
        var admin: Admin? = null

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_ID))
            val login = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_LOGIN))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADMIN_PASSWORD))
            admin = Admin(id, login, password)
        }

        cursor?.close()
        db.close()

        return admin
    }
}
