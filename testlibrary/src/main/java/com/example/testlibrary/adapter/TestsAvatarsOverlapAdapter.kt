package com.example.testlibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.findViewOfItem
import com.example.baselibrary.weiget.RoundImageView
import com.example.testlibrary.R

/**
 * Date:2022/3/14
 * Time:10:14
 * author:dimple
 * 头像重叠适配器
 */
class TestsAvatarsOverlapAdapter(private var mContext: Context) : RecyclerView.Adapter<TestsAvatarsOverlapAdapter.MyViewHolder>() {

    //头像集合
    private var avatars: MutableList<Int> = mutableListOf()

    //图片点击回调函数，kotlin写法
    private lateinit var avatarOnClickListener: (position: Int) -> Unit


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : View> findViewItem(viewid: Int): T {
            return view.findViewOfItem(viewid)
        }

        val ivAvatar: RoundImageView = findViewItem(R.id.iv_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_testavatars, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ivAvatar.setImageDrawable(ContextCompat.getDrawable(mContext, avatars[position]))
        holder.ivAvatar.setOnClickListener {
            avatarOnClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return avatars.size
    }

    /**
     * 设置数据
     */
    fun setAvatarDatas(avatars: MutableList<Int>) {
        this.avatars.addAll(avatars)
    }

    /**
     * 设置监听
     */
    fun setAvatarOnClickListener(avatarOnClickListener: (position: Int) -> Unit) {
        this.avatarOnClickListener = avatarOnClickListener
    }
}