package com.example.testlibrary

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kotlinx.android.synthetic.main.activity_testcalendarview.*

/**
 * Date:2021/5/10
 * Time:9:34
 * author:joker
 * 自定义日历空间类
 */
@Route(path = RouterTag.TestCalendarViewActivity)
class TestCalendarViewActivity : BaseActivity(), CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {

    var mYear = -1
    var month = -1


    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_testcalendarview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initListener()
    }

    /**
     * 初始化数据
     */
    fun initData() {
        mYear = calendarView.curYear
        month = calendarView.curMonth

        tv_month_day?.let {
            it.text = "${calendarView.curMonth}月${calendarView.curDay}日"
        }
        tv_year?.let {
            it.text = calendarView.curYear.toString()
        }
        tv_lunar?.let {
            it.text = "今日"
        }
        tv_current_day?.let {
            it.text = calendarView.curDay.toString()
        }

        var map = mutableMapOf<String, Calendar>()
        map[getSchemeCalendar(mYear, month, 3, 0xbf24db, "假").toString()] = getSchemeCalendar(mYear, month, 3, 0xbf24db, "假")
        map[getSchemeCalendar(mYear, month, 8, 0xbf24db, "假").toString()] = getSchemeCalendar(mYear, month, 3, 0xbf24db, "假")
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map)
    }


    /**
     * 初始化监听
     */
    fun initListener() {
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnYearChangeListener(this)

        tv_month_day.setOnClickListener {
            if (!calendarLayout.isExpand) {
                calendarLayout.expand()
                return@setOnClickListener
            }

            calendarView.showYearSelectLayout(mYear)
            tv_month_day.text = mYear.toString()

            tv_year.visibility = View.GONE
            tv_lunar.visibility = View.GONE
        }

        ib_calendar.setOnClickListener {
            calendarView.scrollToCurrent()
        }

        tv_pre.setOnClickListener {
            calendarView.scrollToPre(false)
        }

        tv_next.setOnClickListener {
            calendarView.scrollToNext(false)
        }

    }


    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
    }

    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {

        tv_year.visibility = View.VISIBLE
        tv_lunar.visibility = View.VISIBLE
        tv_month_day.text = calendar.month.toString() + "月" + calendar.day + "日"
        tv_year.text = calendar.year.toString()
        tv_lunar.text = calendar.lunar
        mYear = calendar.year
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }

    override fun onYearChange(year: Int) {
        tv_month_day.text = year.toString()
        tv_year.visibility = View.GONE
        tv_lunar.visibility = View.GONE
    }
}