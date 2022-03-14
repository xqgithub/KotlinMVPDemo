package com.example.testlibrary

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.ScreenUtils
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
    private fun initData() {
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
    }
}