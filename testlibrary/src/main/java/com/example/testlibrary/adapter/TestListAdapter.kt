package com.example.testlibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baselibrary.utils.findViewOfItem
import com.example.testlibrary.R

/**
 * 首页的适配器
 */
class TestListAdapter(
    var context: Context,
    var items: List<String>
) :
    RecyclerView.Adapter<TestListAdapter.MyViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_testlist, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_test.apply {
            text = items[position]
        }.setOnClickListener {
            onItemClickListener?.setItemOnClick(position)
        }
    }

    /**
     * 防止滑动后，数据错乱，但是对于多布局的时候 貌似会失效
     */
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    interface OnItemClickListener {
        fun setItemOnClick(position: Int)
    }


}