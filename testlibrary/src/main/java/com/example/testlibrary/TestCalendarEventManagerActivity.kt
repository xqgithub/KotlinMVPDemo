package com.example.testlibrary

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.ui.activities.PermissionsActivity
import com.example.baselibrary.utils.PermissionsChecker
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.calendar.CalendarEvent
import com.example.baselibrary.utils.calendar.CalendarProviderManager
import kotlinx.android.synthetic.main.activity_testcalendareventmanager.*


/**
 * Date:2022/4/12
 * Time:14:13
 * author:dimple
 * 日历事件管理器，是时候为你的APP增加一个事件提醒功能啦
 */
@Route(path = RouterTag.TestCalendarEventManagerActivity)
class TestCalendarEventManagerActivity : BaseActivity(), PermissionsActivity.PermissionsListener {
    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_testcalendareventmanager
    }

    /**
     * 加载数据
     */
    private fun initData() {
        //权限判断
        val mPermissionsChecker = PermissionsChecker(this@TestCalendarEventManagerActivity)
        val permissions = arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR)
        if (mPermissionsChecker.lacksPermissions(permissions)) {
            //去申请权限
            PublicPracticalMethodFromJAVA.getInstance().startPermissionsActivity(this@TestCalendarEventManagerActivity, permissions, this, ConfigConstants.PERMISSIONS_GRANTED_CALENDAR_MANAGEMENT)
        }

        btn_main_add.setOnClickListener {
            val calendarEvent = CalendarEvent(
                "马上吃饭",
                "吃好吃的",
                "南信院二食堂",
                System.currentTimeMillis() - 5 * 60000,
                System.currentTimeMillis() + 5 * 60000,
                0, null
            )
            // 添加事件
            when (CalendarProviderManager.addCalendarEvent(this, calendarEvent)) {
                0 -> {
                    Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
                }
                -1 -> {
                    Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
                }
                -2 -> {
                    Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        }

        btn_main_delete.setOnClickListener {
            // 删除事件
            val calID2 = CalendarProviderManager.obtainCalendarAccountID(this)
            val events2 = CalendarProviderManager.queryAccountEvent(this, calID2)
            if (null != events2) {
                if (events2.size == 0) {
                    Toast.makeText(this, "没有事件可以删除", Toast.LENGTH_SHORT).show()
                } else {
                    val eventID = events2[0].id
                    val result2 = CalendarProviderManager.deleteCalendarEvent(this, eventID)
                    if (result2 == -2) {
                        Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show()
            }
        }

        btn_main_update.setOnClickListener {
            // 更新事件
            val calID = CalendarProviderManager.obtainCalendarAccountID(this)
            val events = CalendarProviderManager.queryAccountEvent(this, calID)
            if (null != events) {
                if (events.size == 0) {
                    Toast.makeText(this, "没有事件可以更新", Toast.LENGTH_SHORT).show()
                } else {
                    val eventID = events[0].id
                    val result3 = CalendarProviderManager.updateCalendarEventTitle(
                        this, eventID, "改吃晚饭的房间第三方监督司法"
                    )
                    if (result3 == 1) {
                        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show()
            }
        }

        btn_main_query.setOnClickListener {
            // 查询事件
            val calID4 = CalendarProviderManager.obtainCalendarAccountID(this)
            val events4 = CalendarProviderManager.queryAccountEvent(this, calID4)
            val stringBuilder4 = StringBuilder()
            if (null != events4) {
                for (event in events4) {
                    stringBuilder4.append(events4.toString()).append("\n")
                }
                tv_event.text = stringBuilder4.toString()
                Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show()
            }
        }


        btn_edit.setOnClickListener {
            // 启动系统日历进行编辑事件
            CalendarProviderManager.startCalendarForIntentToInsert(
                this, System.currentTimeMillis(),
                System.currentTimeMillis() + 10 * 60000, "哈", "哈哈哈哈", "蒂埃纳",
                false
            )
        }

        btn_search.setOnClickListener {
            if (CalendarProviderManager.isEventAlreadyExist(
                    this, 1552986006309L,
                    155298606609L, "马上吃饭"
                )
            ) {
                Toast.makeText(this, "存在", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "不存在", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun allPermissionsGranted(mark: Int) {
        LogUtils.iTag(ConfigConstants.TAG_ALL, "=-=  日历权限通过")
    }

    override fun permissionsDenied(mark: Int) {
        LogUtils.iTag(ConfigConstants.TAG_ALL, "=-=  日历权限没有通过")
    }
}