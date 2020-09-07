package com.example.kotlinmvpdemo.mvp.ui.activities

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.PermissionsChecker
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.kotlinmvpdemo.R


/**
 * 检查用户是否具有相应的权限页面
 */
class CheckPermissionsActivity : Activity() {

    //权限检测器
    lateinit var mPermissionsChecker: PermissionsChecker

    /**
     * 所需检查的权限
     */
    val PERMISSIONS = arrayOf<String>(
        READ_PHONE_STATE,
        WRITE_EXTERNAL_STORAGE,
        READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkpermissions)
        PublicPracticalMethodFromJAVA.getInstance().restartApp(this@CheckPermissionsActivity)
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mPermissionsChecker = PermissionsChecker(this)
    }

    override fun onResume() {
        super.onResume()
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {//缺少权限的时候，进入权限配置页面
            startPermissionsActivity()
        } else {//不缺少权限的时候，跳转到指定页面
            val intent = Intent(
                this@CheckPermissionsActivity,
                MainActivity::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    /**
     * 启动权限管理类
     */
    private fun startPermissionsActivity() {
        val dailogcontent = intArrayOf(
            R.string.quit,
            R.string.settings,
            R.string.help,
            R.string.string_help_text
        )
        PermissionsActivity.startActivityForResult(
            this@CheckPermissionsActivity,
            ConfigConstants.PERMISSIONS_INIT_REQUEST_CODE, dailogcontent, PERMISSIONS
        )
    }


}