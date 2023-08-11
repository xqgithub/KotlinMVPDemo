package com.example.baselibrary.weiget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Date:2022/3/14
 * Time:11:13
 * author:dimple
 *  通用RecyclerView分割线
 */
class UniversalItemDecoration : RecyclerView.ItemDecoration() {

    private var mLeft: Int = 0
    private var mTop: Int = 0
    private var mRight: Int = 0
    private var mBottom: Int = 0
    private var specifiedPosition: Int = -1

//    /**
//     * 这样写有一个弊端，如果一个页面有多个RecyclerView，需要设定分割距离的话，会造成影响
//     */
//    companion object {
//        val universalItemDecoration: UniversalItemDecoration by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//            UniversalItemDecoration()
//        }
//    }

//    private lateinit var universalItemDecoration: UniversalItemDecoration

    fun getInstance(): UniversalItemDecoration {
        return UniversalItemDecoration()
    }


    /**
     * 设置间距
     */
    fun setSpaceItemDecoration(left: Int, top: Int, right: Int, bottom: Int): UniversalItemDecoration {
        this.mLeft = left
        this.mTop = top
        this.mRight = right
        this.mBottom = bottom
        return this
    }

    /**
     * 排除指定的item不按照规则
     */
    fun excludeDesignationItem(specifiedPosition: Int): UniversalItemDecoration {
        this.specifiedPosition = specifiedPosition
        return this
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (specifiedPosition != -1) {
            if (parent.getChildLayoutPosition(view) == specifiedPosition) {
                outRect.apply {
                    left = 0
                    top = 0
                    right = 0
                    bottom = 0
                }
            } else {
                outRect.apply {
                    left = mLeft
                    top = mTop
                    right = mRight
                    bottom = mBottom
                }
            }
        } else {
            outRect.apply {
                left = mLeft
                top = mTop
                right = mRight
                bottom = mBottom
            }
        }
    }

}