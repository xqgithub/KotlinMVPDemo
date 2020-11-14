package com.example.kotlinmvpdemo.mvp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baselibrary.utils.findViewOfItem
import com.example.kotlinmvpdemo.R

/**
 * 首页的适配器
 */
class MainListAdapter(
    var context: Context,
    var items: List<String>
) :
    RecyclerView.Adapter<MainListAdapter.MyViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        /**
         * 获取各个item的id
         */
        fun <T : View> findViewItem(viewid: Int): T {
            return view.findViewOfItem(viewid)
        }

        val tv_test: TextView = findViewItem(R.id.tv_test)
        val iv_test: ImageView = findViewItem(R.id.iv_test)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_test.apply {
            text = items[position]
        }.setOnClickListener {
            onItemClickListener.setItemOnClick(position, holder.iv_test)
        }
    }

    interface OnItemClickListener {
        fun setItemOnClick(position: Int, imageView: ImageView)
    }


}