package com.example.testlibrary.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.baselibrary.utils.StringUtils

/**
 * Date:2023/4/16
 * Time:15:54
 * author:joker
 * 测试 ContentProvider
 */
class PeopleProvider : ContentProvider() {


    private val DB_NAME = "people.db"
    private val DB_TABLE = "peopleinfo"
    private val DB_VERSION = 1
    private lateinit var db: SQLiteDatabase
    private lateinit var dbOpenHelper: DBOpenHelper


    private val MULTIPLE_PEOPLE = 1
    private val SINGLE_PEOPLE = 2
    private lateinit var uriMatcher: UriMatcher

    init {
        //实例化UriMatcher对象
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        //可以实现匹配URI的功能
        uriMatcher.addURI(People.AUTHORITY, People.PATH_MULTIPLE, MULTIPLE_PEOPLE)
        uriMatcher.addURI(People.AUTHORITY, People.PATH_SINGLE, SINGLE_PEOPLE)
    }

    override fun onCreate(): Boolean {
        dbOpenHelper = DBOpenHelper(context!!, DB_NAME, null, DB_VERSION)
        db = dbOpenHelper.writableDatabase
        return !StringUtils.isBlank(db)
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = DB_TABLE
        when (uriMatcher.match(uri)) {
            SINGLE_PEOPLE -> {
                qb.appendWhere(People.KEY_ID + "=" + uri.pathSegments[1])
            }
        }
        val cursor = qb.query(
            db,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher.match(uri)) {
            MULTIPLE_PEOPLE -> {
                return People.MIME_TYPE_MULTIPLE
            }
            SINGLE_PEOPLE -> {
                return People.MIME_TYPE_SINGLE
            }
            else -> throw IllegalArgumentException("Unkown uro:$uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = db.insert(DB_TABLE, null, values)
        if (id > 0) {
            val newUri = ContentUris.withAppendedId(People.CONTENT_URI, id)
            context?.contentResolver?.notifyChange(newUri, null)
            return newUri
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            MULTIPLE_PEOPLE -> {
                count = db.delete(DB_TABLE, selection, selectionArgs)
            }
            SINGLE_PEOPLE -> {
                val segment = uri.pathSegments[1]
                count = db.delete(DB_TABLE, People.KEY_ID + "=" + segment, selectionArgs);
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            MULTIPLE_PEOPLE -> {
                count = db.update(DB_TABLE, values, selection, selectionArgs)
            }
            SINGLE_PEOPLE -> {
                val segment = uri.pathSegments[1]
                count = db.update(DB_TABLE, values, People.KEY_ID + "=" + segment, selectionArgs)
            }
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }
}