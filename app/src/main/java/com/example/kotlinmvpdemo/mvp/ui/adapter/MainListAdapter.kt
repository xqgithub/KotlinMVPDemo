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
        var picPath: String = ""
        if (position == 7) {
            picPath = "https://n.sinaimg.cn/sinacn20200204ac/551/w816h535/20200204/cc25-inzcrxs3080005.png"
            holder.iv_test.tag = picPath
            holder.iv_test.visibility = View.VISIBLE
        } else {
            holder.iv_test.visibility = View.INVISIBLE
        }
        holder.tv_test.apply {
            text = "$position. ${items[position]}"
        }.setOnClickListener {
            onItemClickListener.setItemOnClick(position, holder.iv_test, picPath)
        }
    }

    /**
     * 防止滑动后，数据错乱，但是对于多布局的时候 貌似会失效
     */
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    interface OnItemClickListener {
        fun setItemOnClick(position: Int, imageView: ImageView, srcpath: String)
    }


}