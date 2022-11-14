package com.dicoding.picodiploma.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.picodiploma.githubuser.data.room.UserDao
import com.dicoding.picodiploma.githubuser.data.room.UserDatabase

class FavoriteProvider : ContentProvider() {
    companion object {
        private const val NOTE = 1
//        private const val NOTE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userDao: UserDao
        private val AUTHORITY = "com.dicoding.picodiploma.githubuser"
        private val TABLE_NAME = "user_table"
        const val SCHEME = "content"
        val CONTENT_URI = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE)
        }
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (NOTE) {
            sUriMatcher.match(uri) -> userDao.deleteUser(uri.lastPathSegment) .deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted}

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (NOTE) {
            sUriMatcher.match(uri) -> userDao.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        userDao = UserDatabase.getDatabase(context).userDao()
        return false;}

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

            return when(sUriMatcher.match(uri)) {
                NOTE -> userDao.readFavoriteData()
                else -> null
            }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}