package com.example.testlibrary

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.customizeviews.DropDownHeaderView
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.ScreenUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import kotlinx.android.synthetic.main.activity_main_test.*


@Route(path = RouterTag.TestMainActivity)
class TestMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                false, true,
                R.color.full_red
            )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main_test
    }

    /**
     * 初始化数据
     */

    fun initData() {

        tv_test_main.apply {
            text = "跳转到主程序app中的MainActivity"
        }.setOnClickListener {
            ARouter.getInstance().build(RouterTag.MainActivity).navigation()
            finish()
        }

        addUiDynamic()
        addRefreshUi(true)
    }

    /**
     *  动态添加UI控件
     */
    private fun addUiDynamic() {
        //1.自定义一个TextView1
        val textView = TextView(this@TestMainActivity)
        textView.apply {
            text = "动态添加的TextView1"
            textSize = this.resources.getDimensionPixelSize(R.dimen.dimen_14x) / ScreenUtils.getScreenDensity(context)
            setTextColor(ContextCompat.getColor(this@TestMainActivity, R.color.black))
            setBackgroundColor(ContextCompat.getColor(this@TestMainActivity, R.color.full_red))
            gravity = Gravity.LEFT
            //自定义控件id
            val typedArray = this@TestMainActivity.obtainStyledAttributes(R.styleable.textviewhaha)
            val textviewID = typedArray.getInt(
                R.styleable.textviewhaha_textview1,
                99999
            )
            id = textviewID
        }
        val rlp = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = rlp
        //添加控件
        rl_test_main.addView(textView)

        //2.自定义一个textView2
        val textView2 = TextView(this@TestMainActivity)
        textView2.apply {
            val lambdaTest = { a: String, b: String -> "$a VS $b" }
            val lambdaTest2: (String, String) -> String = { a, b -> "$a VS $b" }
            val lambdaTest3 = {
                val a = "路飞"
                val b = "索隆"
                "$a VS $b"
            }
            text = "动态添加的TextView_${lambdaTest3()}"
            textSize = this.resources.getDimensionPixelSize(R.dimen.dimen_14x) / ScreenUtils.getScreenDensity(context)
            setTextColor(ContextCompat.getColor(this@TestMainActivity, R.color.black))
            setBackgroundColor(ContextCompat.getColor(this@TestMainActivity, R.color.crowded_yellow))
            gravity = Gravity.LEFT
        }
        val rlp2 = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        /**
         *layoutParams.alignWithParent = true  如果对应的兄弟元素找不到的话就以父元素做参照物
         *RelativeLayout.CENTER_HORIZONTAL   在父控件中水平居中
         *RelativeLayout.CENTER_VERTICAL   在父控件中垂直居中
         *RelativeLayout.CENTER_IN_PARENT  相对于父控件完全居中
         *RelativeLayout.ALIGN_PARENT_BOTTOM  紧贴父控件的下边缘
         *RelativeLayout.ALIGN_PARENT_TOP  紧贴父控件的上边缘
         *RelativeLayout.ALIGN_PARENT_LEFT紧贴父控件的左边边缘
         *RelativeLayout.ALIGN_PARENT_RIGHT  紧贴父控件的右边缘
         *RelativeLayout.ABOVE 在某元素的上方  需要第二个参数为某元素的ID
         *RelativeLayout.BELOW 在某元素的下方 需要第二个参数为某元素的ID
         *RelativeLayout.LEFT_OF 在某元素的左边 需要第二个参数为某元素的ID
         *RelativeLayout.RIGHT_OF  在某元素的右边 需要第二个参数为 某元素的ID
         *RelativeLayout.ALIGN_TOP 本元素的上边缘和某元素的的上边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_BOTTOM 本元素的上边缘和某元素的的下边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_LEFT  本元素的上边缘和某元素的的左边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_RIGHT  本元素的上边缘和某元素的的右边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_BASELINE   本元素的基线和某元素的的基线对齐 需要第二个参数为某元素的ID
         */
        rlp2.addRule(RelativeLayout.BELOW, textView.id)
        rlp2.setMargins(0, this.resources.getDimensionPixelSize(R.dimen.dimen_10x), 0, 0)
        textView2.layoutParams = rlp2
        //添加控件
        rl_test_main.addView(textView2)
    }

    /**
     * 添加刷新控件
     * @param isOpenRefresh 是否添加刷新UI
     */
    private fun addRefreshUi(isOpenRefresh: Boolean) {
        if (isOpenRefresh) {
            //1.下拉刷新
//            srl_main.setRefreshHeader(ClassicsHeader(this@TestMainActivity))
            srl_main.setRefreshHeader(DropDownHeaderView(this@TestMainActivity))
            //下拉刷新监听
            srl_main.setOnRefreshListener {
                LogUtils.i(ConfigConstants.TAG_ALL, "我被下拉刷新了")
                it.finishRefresh(5000)
            }

            //3.上推加载
            srl_main.setRefreshFooter(ClassicsFooter(this@TestMainActivity))
            //上推加载监听
            srl_main.setOnLoadMoreListener {
                LogUtils.i(ConfigConstants.TAG_ALL, "我被上推加载了")
                it.finishLoadMore(500)
            }
        } else {
            srl_main.setEnableRefresh(isOpenRefresh)
            srl_main.setEnableLoadMore(isOpenRefresh)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}