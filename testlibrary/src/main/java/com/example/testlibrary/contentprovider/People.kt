package com.example.testlibrary.contentprovider

import android.net.Uri

/**
 * Date:2023/4/16
 * Time:16:22
 * author:joker
 */
class People {
    companion object {
        //多条记录返回
        const val MIME_DIR_PREFIX = "vnd.android.cursor.dir"

        //单条记录返回
        const val MIME_ITEM_PREFIX = "vnd.android.cursor.item"
        const val MIME_ITEM = "vnd.example.people"

        const val MIME_TYPE_SINGLE = "${MIME_ITEM_PREFIX}/${MIME_ITEM}"
        const val MIME_TYPE_MULTIPLE = "${MIME_DIR_PREFIX}/${MIME_ITEM}"

        const val AUTHORITY = "com.example.peopleprovider"
        const val PATH_SINGLE = "people/#"
        const val PATH_MULTIPLE = "people"
        const val CONTENT_URI_STRING = "content://${AUTHORITY}/${PATH_MULTIPLE}"
        val CONTENT_URI = Uri.parse(CONTENT_URI_STRING)

        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
        const val KEY_AGE = "age"
        const val KEY_HEIGHT = "height"
    }

}