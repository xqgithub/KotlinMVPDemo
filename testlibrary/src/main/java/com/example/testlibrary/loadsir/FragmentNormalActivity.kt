package com.example.testlibrary.loadsir

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.testlibrary.R

/**
 * Date:2022/7/1
 * Time:9:52
 * author:dimple
 */
class FragmentNormalActivity : BaseActivity() {

    /** Fragment **/
    //Fragment管理器
    lateinit var fragmentManager: FragmentManager

    //Fragment的事务
    lateinit var transaction: FragmentTransaction

    lateinit var normalFragment: NormalFragment

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                true, true,
                R.color.appwhite
            )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fragmentnormal
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        normalFragment = NormalFragment()
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.apply {
            add(R.id.fl_content, normalFragment)
            commit()
            show(normalFragment)
        }
    }

}