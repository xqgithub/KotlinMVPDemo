package ${escapeKotlinIdentifiers(packageName)}.mvp.ui.activities

import ${superClassFqcn}
import android.os.Bundle
import ${escapeKotlinIdentifiers(packageName)}.R

class ${activityClass} : BaseActivity(),${subordinate}View{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<#if generateLayout>
        setContentView(R.layout.${layoutName})
</#if>
    }

    override fun setupComponent(myAppComponet: MyAppComponet){

    }


    override fun getLayoutId():Int{
        return R.layout.${layoutName}
}

}
