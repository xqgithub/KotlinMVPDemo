package com.example.testlibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.ConstraintUtil
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.ScreenUtils
import com.example.baselibrary.weiget.AvatarsView
import com.example.baselibrary.weiget.UniversalItemDecoration
import com.example.testlibrary.adapter.TestsAvatarsOverlapAdapter
import kotlinx.android.synthetic.main.activity_testsavatarsoverlap.*

/**
 * Date:2022/3/14
 * Time:9:59
 * author:dimple
 * 头像重叠
 */
@Route(path = RouterTag.TestsAvatarsOverlapActivity)
class TestsAvatarsOverlapActivity : BaseActivity() {


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
            UniversalItemDecoration.universalItemDecoration.setSpaceItemDecoration(
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
    }
}