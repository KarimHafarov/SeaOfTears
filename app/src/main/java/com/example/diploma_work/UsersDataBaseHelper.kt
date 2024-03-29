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
        private const val DATABASE_VERSION = 6
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "_id"
        const val COLUMN_RANK = "rank"
        const val COLUMN_NAME = "name"
        const val COLUMN_FATHER = "father"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_TIME = "time"
        const val COLUMN_DUTY = "duty"
        const val COLUMN_ADMIN_FIREBASE_ID = "admin_firebase_id"
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
                $COLUMN_ADMIN_FIREBASE_ID TEXT
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun searchUsers(query: String): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val selection = "$COLUMN_NAME LIKE ? OR $COLUMN_SURNAME LIKE ?"
        val selectionArgs = arrayOf("%$query%", "%$query%")
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
                val user = User(id, rank, name, father, surname, time, duty)
                users.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return users
    }

    fun insertUser(user: User) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RANK, user.rank)
            put(COLUMN_NAME, user.name)
            put(COLUMN_FATHER, user.father)
            put(COLUMN_SURNAME, user.surname)
            put(COLUMN_TIME, user.time)
            put(COLUMN_DUTY, user.duty)
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
                val father = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER))
                val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                val duty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUTY))
                val user = User(id, rank, name, father, surname, time, duty)
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
            val father = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER))
            val surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val duty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUTY))
            user = User(id, rank, name, father, surname, time, duty)
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
