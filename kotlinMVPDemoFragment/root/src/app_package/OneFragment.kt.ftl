package ${escapeKotlinIdentifiers(packageName)}.mvp.ui.fragments

import android.os.Bundle
import com.example.baselibrary.base.BaseFragment
import ${escapeKotlinIdentifiers(packageName)}.R

class ${fragmentClass} : BaseFragment(),${fragmentClass}View {

    override fun setupComponent(myAppComponet: MyAppComponet) {

    }

        override fun getLayoutId(): Int {
        return R.layout.${fragmentName}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun lazyLoad() {
    }
}
