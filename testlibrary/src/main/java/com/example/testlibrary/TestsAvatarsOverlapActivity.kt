package com.example.testlibrary

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.DateUtil
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.ScreenUtils
import com.example.baselibrary.weiget.AvatarsView
import com.example.baselibrary.weiget.UniversalItemDecoration
import com.example.testlibrary.adapter.TestsAvatarsOverlapAdapter
import kotlinx.android.synthetic.main.activity_testsavatarsoverlap.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Date:2022/3/14
 * Time:9:59
 * author:dimple
 * 头像重叠
 */
@Route(path = RouterTag.TestsAvatarsOverlapActivity)
class TestsAvatarsOverlapActivity : BaseActivity() {


    private var pvCustomTime: TimePickerView? = null


    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_testsavatarsoverlap
    }

    /**
     * 加载数据
     */
    @SuppressLint("ResourceType")
    private fun initData() {
        //1.RecyclerView的做法
        val linearLayoutManager = LinearLayoutManager(this@TestsAvatarsOverlapActivity)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        rv_avatarsover.layoutManager = linearLayoutManager
        //添加分割规则
        rv_avatarsover.addItemDecoration(
            UniversalItemDecoration().getInstance().setSpaceItemDecoration(
                -ScreenUtils.dip2px(this@TestsAvatarsOverlapActivity, 20f),
                0,
                0,
                0
            ).excludeDesignationItem(0)
        )

        val adapter = TestsAvatarsOverlapAdapter(this@TestsAvatarsOverlapActivity)
        rv_avatarsover.adapter = adapter

        //获取本地数据
        val avatars = mutableListOf<Int>()
        for (i in 0..3) {
            avatars.add(R.mipmap.app_logo_jingyong)
            avatars.add(R.mipmap.app_logo_shandian)
            avatars.add(R.mipmap.testtinker)
        }
        adapter.setAvatarDatas(avatars)
        adapter.notifyDataSetChanged()
        adapter.setAvatarOnClickListener { position ->
            LogUtils.i(ConfigConstants.TAG_ALL, "$position 位置被点击了！")
        }

        //2.自定义View的做法
        //自定义头像的ID集合
        val avatarsViewIDs = mutableListOf<Int>()
        for (i in avatars.indices) {
            val avatarsView = AvatarsView(this@TestsAvatarsOverlapActivity)

            avatarsView.setAvatarData(avatars[i]) {
                LogUtils.i(ConfigConstants.TAG_ALL, "$i 位置被点击了！")
            }

            //自定义LayoutParams 实现AvatarsView的位置等基本信息
            val cl_layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            //自定义ID
            avatarsView.id = 10000 + i
            when (i) {
                0 -> {
                    cl_layoutParams.leftToLeft = R.id.cl_avatars
                }

                else -> {
                    cl_layoutParams.leftToLeft = avatarsViewIDs[i - 1]
                    cl_layoutParams.leftMargin = ScreenUtils.dip2px(this@TestsAvatarsOverlapActivity, 60f) - ScreenUtils.dip2px(this@TestsAvatarsOverlapActivity, 20f)
                }
            }
            avatarsViewIDs.add(avatarsView.id)
            avatarsView.layoutParams = cl_layoutParams
            cl_avatars.addView(avatarsView)
        }

        //3.日期选择弹出
        initCustomTimePicker()
        tv_pickdate.setOnClickListener {
            pvCustomTime?.show()
        }
    }

    /**
     * 自定义时间选择器
     */
    private fun initCustomTimePicker() {
        /**
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        var selectedDate: Calendar = Calendar.getInstance() //系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(1900, 0, 1)
        val endDate = Calendar.getInstance()
        endDate.time = Date()
        //时间选择器 ，自定义布局
        pvCustomTime = TimePickerBuilder(this) { date, v -> //选中事件回调
            val format: String = SimpleDateFormat("yyyy-MM-dd").format(date)
            tv_pickdate.text = format
        } /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               / *.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
            /*.animGravity(Gravity.RIGHT)// default is center*/
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setLayoutRes(R.layout.pickerview_custom_time) { v ->
                val tvSubmit: TextView = v.findViewById(R.id.tv_finish) as TextView
                val ivCancel: ImageView = v.findViewById(R.id.iv_cancel) as ImageView
                tvSubmit.setOnClickListener {
                    pvCustomTime!!.returnData()
                    pvCustomTime!!.dismiss()
                }
                ivCancel.setOnClickListener {
                    pvCustomTime!!.dismiss()
                }
            }
            .setContentTextSize(18)
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLabel("年", "月", "日", "时", "分", "秒")
            .setLineSpacingMultiplier(1.2f)
            .setTextXOffset(0, 0, 0, 40, 0, -40)
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setDividerColor(Color.WHITE)
            .build()
    }
}