package com.example.diploma_work

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersDataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 3
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "_id"
        const val COLUMN_RANK = "rank"
        const val COLUMN_NAME = "name"
        const val COLUMN_FATHERNAME = "fathername"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RANK TEXT,
                $COLUMN_NAME TEXT,
                $COLUMN_FATHERNAME TEXT,
                $COLUMN_SURNAME TEXT,
                $COLUMN_TIME TEXT
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUser(user: User) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RANK, user.rank)
            put(COLUMN_NAME, user.name)
            put(COLUMN_FATHERNAME, user.fathername)
            put(COLUMN_SURNAME, user.surname)
            put(COLUMN_TIME, user.time)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val rank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANK))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val fathername = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHERNAME))
                val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                val user = User(id, rank, name, fathername, surname, time)
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
            put(COLUMN_RANK,user.rank)
            put(COLUMN_NAME, user.name)
            put(COLUMN_FATHERNAME, user.fathername)
            put(COLUMN_SURNAME, user.surname)
            put(COLUMN_TIME, user.time)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(user.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getUserById(userID: Int): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $userID"
        val cursor: Cursor = db.rawQuery(query, null)

        var user: User? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val rank = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANK))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val fathername = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHERNAME))
            val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            user = User(id, rank, name, fathername, surname, time)
        }

        cursor.close()
        db.close()
        return user
    }

    fun deleteUser(contactId: Long) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contactId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}