package com.example.baselibrary.weiget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.baselibrary.R
import kotlinx.android.synthetic.main.view_avatarsview.view.*

/**
 * Date:2022/3/14
 * Time:15:40
 * author:dimple
 * 头像自定义View
 */
class AvatarsView @JvmOverloads constructor(
    private var mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(mContext, attrs, defStyleAttr) {

    private lateinit var mView: View

    //图像点击
    private lateinit var avatarOnClickListener: () -> Unit


    init {
        initView()
    }

    /**
     * 初始化UI
     */
    private fun initView() {
        mView = View.inflate(mContext, R.layout.view_avatarsview, this)
    }

    /**
     * 设置图片
     */
    fun setAvatarData(img: Int, avatarOnClickListener: () -> Unit) {
        iv_avatar.setImageDrawable(ContextCompat.getDrawable(mContext, img))
        this.avatarOnClickListener = avatarOnClickListener
        iv_avatar.setOnClickListener {
            this.avatarOnClickListener.invoke()
        }
    }
}