package com.example.diploma_work

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class UsersDataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 30
        const val TABLE_NAME = "user"
        const val COLUMN_ID = "_id"
        const val COLUMN_RANK = "rank"
        const val COLUMN_NAME = "name"
        const val COLUMN_FATHER = "father"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_TIME = "time"
        const val COLUMN_DUTY = "duty"
        const val COLUMN_COMMENT = "comment"
        const val COLUMN_ADMIN_ID = "admin_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RANK TEXT,
                $COLUMN_NAME TEXT,
                $COLUMN_FATHER TEXT,
                $COLUMN_SURNAME TEXT,
                $COLUMN_TIME TEXT,
                $COLUMN_DUTY TEXT,
                $COLUMN_COMMENT TEXT,
                $COLUMN_ADMIN_ID INTEGER
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropUserTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropUserTableQuery)
        onCreate(db)
    }

    fun insertUser(user: User, adminId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RANK, user.rank)
            put(COLUMN_NAME, user.name)
            put(COLUMN_FATHER, user.father)
            put(COLUMN_SURNAME, user.surname)
            put(COLUMN_TIME, user.time)
            put(COLUMN_DUTY, user.duty)
            put(COLUMN_COMMENT, user.comment)
            put(COLUMN_ADMIN_ID, adminId)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun searchUsers(query: String): List<User> {
        val users = mutableListOf<User>()
        readableDatabase.use { db ->
            val selection = "$COLUMN_NAME LIKE ? OR $COLUMN_SURNAME LIKE ?"
            val selectionArgs = arrayOf("%$query%", "%$query%")
            val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

            cursor.use {
                if (it.moveToFirst()) {
                    do {
                        val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                        val rank = it.getString(it.getColumnIndexOrThrow(COLUMN_RANK))
                        val name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME))
                        val father = it.getString(it.getColumnIndexOrThrow(COLUMN_FATHER))
                        val surname = it.getString(it.getColumnIndexOrThrow(COLUMN_SURNAME))
                        val time = it.getString(it.getColumnIndexOrThrow(COLUMN_TIME))
                        val duty = it.getString(it.getColumnIndexOrThrow(COLUMN_DUTY))
                        val comment = it.getString(it.getColumnIndexOrThrow(COLUMN_COMMENT))
                        val user = User(id, rank, name, father, surname, time, duty, comment)
                        users.add(user)
                    } while (it.moveToNext())
                }
            }
        }
        return users
    }


    fun getUsersByAdminId(adminId: Int): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ADMIN_ID = $adminId"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val rank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANK))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val father = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER))
                val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                val duty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUTY))
                val comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT))
                val user = User(id, rank, name, father, surname, time, duty, comment)
                users.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return users
    }

    fun updateUser(user: User) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RANK, user.rank)
            put(COLUMN_NAME, user.name)
            put(COLUMN_FATHER, user.father)
            put(COLUMN_SURNAME, user.surname)
            put(COLUMN_TIME, user.time)
            put(COLUMN_DUTY, user.duty)
            put(COLUMN_COMMENT, user.comment)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(user.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }


    fun getAllUsers(adminId: Int): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ADMIN_ID = $adminId"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val rank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANK))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val father = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER))
                val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                val duty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUTY))
                val comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT))
                val user = User(id, rank, name, father, surname, time, duty, comment)
                users.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return users
    }

    fun deleteUser(userId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(userId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getUserById(userId: Int): User? {
        val db = readableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(userId.toString())
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var user: User? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val rank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANK))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val father = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER))
            val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val duty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUTY))
            val comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT))
            user = User(id, rank, name, father, surname, time, duty, comment)
        }

        cursor.close()
        db.close()

        return user
    }

    fun searchUsersByAdminId(query: String, adminId: Int): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase

        val selection = "($COLUMN_NAME LIKE ? OR $COLUMN_SURNAME LIKE ?) AND $COLUMN_ADMIN_ID = ?"
        val selectionArgs = arrayOf("%$query%", "%$query%", adminId.toString())

        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val rank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANK))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val father = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER))
                val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                val duty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUTY))
                val comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT))
                val user = User(id, rank, name, father, surname, time, duty, comment)
                users.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return users
    }


    fun getUserDateRange(userId: Int): Pair<Date, Date>? {
        val db = readableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(userId.toString())
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_COMMENT),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var startDate: Date? = null
        var endDate: Date? = null
        try {
            if (cursor.moveToFirst()) {
                val comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT))
                val dateRange = comment.split(" - ").map { it.trim() }
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                if (dateRange.size == 2) {
                    startDate = dateFormat.parse(dateRange[0])
                    endDate = dateFormat.parse(dateRange[1])
                }
            }
        } catch (e: Exception) {
            // Log error or handle exception as appropriate
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }

        return if (startDate != null && endDate != null) Pair(startDate, endDate) else null
    }

    fun getUserComment(rankAndSurname: String): String? {
        val db = readableDatabase
        val selection = "$COLUMN_RANK || ' ' || $COLUMN_SURNAME = ?"
        val selectionArgs = arrayOf(rankAndSurname)
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_COMMENT),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var comment: String? = null
        if (cursor.moveToFirst()) {
            comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT))
        }
        cursor.close()
        db.close()
        return comment
    }
}