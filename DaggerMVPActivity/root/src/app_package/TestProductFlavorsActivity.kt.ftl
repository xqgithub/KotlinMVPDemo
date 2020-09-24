package ${packageName}.mvp.ui.activities


import ${superClassFqcn}
import android.os.Bundle
import com.example.baselibrary.base.BaseActivity
import ${packageName}.R
import ${packageName}.mvp.views.${activityClass}View
<#if (includeCppSupport!false) && generateLayout>
</#if>

class ${activityClass}Activity : BaseActivity(), ${activityClass}View {


    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun getLayoutId(): Int {
        return R.layout.${layoutName}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}