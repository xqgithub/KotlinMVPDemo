package com.example.testlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.constants.RouterTag
import kotlinx.android.synthetic.main.activity_main_test.*

@Route(path = RouterTag.TestMainActivity)
class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        tv_test_main.setOnClickListener {
            ARouter.getInstance().build(RouterTag.MainActivity).navigation()
            finish()
        }
    }

}