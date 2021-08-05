package com.example.kotlinmvpdemo.weiget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.RxViewUtils
import com.example.kotlinmvpdemo.R
import kotlinx.android.synthetic.main.layout_number.view.*

/**
 * Date:2021/7/28
 * Time:9:31
 * author:joker
 * 图片选择计数 自定义View
 */

class PictureLayoutNumView @JvmOverloads constructor(
    var mContext: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(mContext, attrs, defStyleAttr), RxViewUtils.Action1<View> {

    private lateinit var mView: View

    private lateinit var clickListener: onClickPictureLayoutNumListener

    //最大照片数据初始值为9
    var maxSelectNum: Int = 9

    init {
        initView()

        initData()

        initListener()
    }

    /**
     * 初始化UI控件
     */
    fun initView() {
        mView = View.inflate(mContext, R.layout.layout_number, this)
        PublicPracticalMethodFromJAVA.getInstance().setDynamicShapeRECTANGLE(mContext, minus, -1f, -1, "", "#FF8F92A1")
        PublicPracticalMethodFromJAVA.getInstance().setDynamicShapeRECTANGLE(mContext, plus, -1f, -1, "", "#FF8F92A1")
    }

    /**
     * 初始化数据
     */
    fun initData() {
        tv_select_num.text = maxSelectNum.toString()
    }

    /**
     * 初始化监听
     */
    fun initListener() {
        RxViewUtils.getInstance().setOnClickListeners(this, 200, minus, plus)
    }


    /**
     * 初始化监听
     */
    fun setOnClickPictureLayoutNumListener(clickListener: onClickPictureLayoutNumListener) {
        this.clickListener = clickListener
    }

    /**
     * 点击回调
     */
    override fun onRxViewClick(v: View) {
        when (v) {
            minus -> {
                if (maxSelectNum > 1) {
                    maxSelectNum--
                    tv_select_num.text = maxSelectNum.toString()
                    clickListener?.let {
                        it.onMinusOrPlusClick()
                    }
                }
            }
            plus -> {
                maxSelectNum++
                tv_select_num.text = maxSelectNum.toString()
                clickListener?.let {
                    it.onMinusOrPlusClick()
                }
            }
        }
    }


    interface onClickPictureLayoutNumListener {
        fun onMinusOrPlusClick()
    }
}