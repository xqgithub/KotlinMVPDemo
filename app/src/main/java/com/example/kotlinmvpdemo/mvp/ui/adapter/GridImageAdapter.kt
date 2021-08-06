package com.example.kotlinmvpdemo.mvp.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.StringUtils
import com.example.baselibrary.utils.findViewOfItem
import com.example.kotlinmvpdemo.R
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.DateUtils


/**
 * Date:2021/7/27
 * Time:11:34
 * author:joker
 * 图片集合展示 适配器
 */
class GridImageAdapter(var context: Context) : RecyclerView.Adapter<GridImageAdapter.GridImageViewholder>() {

    private var list: MutableList<LocalMedia> = mutableListOf()

    private lateinit var mOnAddPicClickListener: onAddPicClickListener

    //最大的选择数
    private var selectMax = 9

    class GridImageViewholder(val view: View) : RecyclerView.ViewHolder(view) {
        /**
         * 获取各个item的id
         */
        fun <T : View> findViewItem(viewid: Int): T {
            return view.findViewOfItem(viewid)
        }

        val iv_fiv: ImageView = findViewItem(R.id.iv_fiv)
        val iv_del: ImageView = findViewItem(R.id.iv_del)
        val tv_duration: TextView = findViewItem(R.id.tv_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridImageViewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_gridimage, parent, false)
        return GridImageViewholder(view)
    }

    override fun onBindViewHolder(holder: GridImageViewholder, position: Int) {
        if (getItemViewType(position) == ConfigConstants.TYPE_CAMERA) {//少于MaxSize张，显示继续添加的图标
            holder.iv_fiv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add_image))
            holder.iv_del.visibility = View.GONE
            holder.iv_fiv.setOnClickListener {
                mOnAddPicClickListener.onAddPicClick()
            }
        } else {
            holder.iv_del.visibility = View.VISIBLE
            holder.iv_del.setOnClickListener {
                var index = holder.absoluteAdapterPosition
                if (index != RecyclerView.NO_POSITION && (list.size > index)) {
                    list.removeAt(index)
                    notifyItemRemoved(index)
                    notifyItemRangeChanged(index, list.size)
                }
            }

            val media = list[position]
            val chooseModel = media.chooseModel
            var path: String?
            if (media.isCut && !media.isCompressed) {// 裁剪过
                path = media.cutPath
                LogUtils.i(ConfigConstants.TAG_ALL, "裁剪地址:$path")
            } else if (media.isCompressed || (media.isCut && media.isCompressed)) {// 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.compressPath
                LogUtils.i(ConfigConstants.TAG_ALL, "压缩地址:$path")
            } else {//原图
                path = media.path
                LogUtils.i(ConfigConstants.TAG_ALL, "原图地址:$path")
            }

            if (media.isOriginal) {
                LogUtils.i(ConfigConstants.TAG_ALL, "开启原图功能后地址:${media.originalPath}")
            }


            val duration = media.duration
            if (PictureMimeType.isHasVideo(media.mimeType) || PictureMimeType.isHasAudio(media.mimeType)) {
                holder.tv_duration.visibility = View.VISIBLE
                if (chooseModel == PictureMimeType.ofAudio()) {
                    holder.tv_duration.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.picture_icon_audio, 0, 0, 0)
                } else {
                    holder.tv_duration.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.picture_icon_video, 0, 0, 0)
                }
                holder.tv_duration.text = DateUtils.formatDurationTime(duration)
            } else {
                holder.tv_duration.visibility = View.GONE
            }

            if (chooseModel == PictureMimeType.ofAudio()) {
                holder.iv_fiv.setImageResource(R.drawable.picture_audio_placeholder)
            } else {
                Glide.with(context)
                    .load(if (PictureMimeType.isContent(path) && !media.isCut && !media.isCompressed) Uri.parse(path) else path)
                    .centerCrop()
                    .placeholder(R.color.app_color_f6)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_fiv)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list.size < selectMax) {
            list.size + 1
        } else {
            list.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowAddItem(position)) {
            ConfigConstants.TYPE_CAMERA
        } else {
            ConfigConstants.TYPE_PICTURE
        }
    }

    fun setOnAddPicClickListener(mOnAddPicClickListener: onAddPicClickListener) {
        this.mOnAddPicClickListener = mOnAddPicClickListener
    }


    private fun isShowAddItem(position: Int): Boolean {
        var size = list.size
        return position == size
    }

    fun setSelectMax(selectMax: Int) {
        this.selectMax = selectMax
    }

    fun setList(list: MutableList<LocalMedia>) {
        this.list = list
    }

    fun getData(): MutableList<LocalMedia> {
        return if (StringUtils.isBlank(list)) {
            mutableListOf()
        } else {
            list
        }
    }

    interface onAddPicClickListener {
        fun onAddPicClick()
    }
}