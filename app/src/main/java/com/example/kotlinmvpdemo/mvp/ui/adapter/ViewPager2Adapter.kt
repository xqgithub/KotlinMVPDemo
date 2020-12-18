package com.example.kotlinmvpdemo.mvp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baselibrary.utils.findViewOfItem
import com.example.kotlinmvpdemo.R

/**
 * 因为ViewPager2内部封装的是RecyclerView，因此它的Adapter也就是RecyclerView的Adapter
 */
class ViewPager2Adapter(var context: Context, var items: Array<String>) : RecyclerView.Adapter<ViewPager2Adapter.PagerViewHolder>() {

    class PagerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        /**
         * 获取各个item的id
         */
        fun <T : View> findViewItem(viewid: Int): T {
            return view.findViewOfItem(viewid)
        }

        val tv_test_viewpager2 = findViewItem<TextView>(R.id.tv_test_viewpager2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_viewpager2, parent, false)
        return PagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.tv_test_viewpager2.apply {
            setTextColor(Color.parseColor(items[position]))
            text = items[position]
        }
    }
}