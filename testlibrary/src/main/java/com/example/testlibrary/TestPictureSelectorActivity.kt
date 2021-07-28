package com.example.testlibrary

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.adapter.GridImageAdapter
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.RxViewUtils
import com.example.decoration.GridSpacingItemDecoration
import com.example.utils.FullyGridLayoutManager
import com.example.utils.GlideEngine
import com.example.weiget.PictureLayoutNumView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.MediaExtraInfo
import com.luck.picture.lib.tools.MediaUtils
import kotlinx.android.synthetic.main.activity_testpictureselector.*


/**
 * Date:2021/6/10
 * Time:10:17
 * author:joker
 * 图片选择工具 PictureSelector  测试类
 */
@Route(path = RouterTag.TestPictureSelectorActivity)
class TestPictureSelectorActivity : BaseActivity(),
    RxViewUtils.Action1<View>, GridImageAdapter.onAddPicClickListener, PictureLayoutNumView.onClickPictureLayoutNumListener {

    private var gridimageadapter: GridImageAdapter? = null

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            R.color.full_red
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initListener()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_testpictureselector
    }


    /**
     * 初始化数据
     */
    fun initData() {
        /** 初始化图片集合的RecyclerView **/
        //1.GridLayoutManager初始化
        val manager = FullyGridLayoutManager(this@TestPictureSelectorActivity, 4, GridLayoutManager.VERTICAL, false)
        rv_pics.layoutManager = manager
        //2.添加间距
        rv_pics.addItemDecoration(GridSpacingItemDecoration(4, resources.getDimensionPixelSize(R.dimen.dimen_5x), true))
        //3.初始化适配器
        gridimageadapter = GridImageAdapter(this@TestPictureSelectorActivity)
        gridimageadapter?.let {
            it.setOnAddPicClickListener(this)
            it.setSelectMax(pln_pics.maxSelectNum)
            //4.设置适配器
            rv_pics.adapter = it
            it.notifyDataSetChanged()
        }
    }

    /**
     * 初始化监听
     */
    private fun initListener() {
        RxViewUtils.getInstance().setOnClickListeners(this, 500, back)
        pln_pics.setOnClickPictureLayoutNumListener(this)
    }

    /**
     * 点击事件回调
     * PS:防止重新点击
     */
    override fun onRxViewClick(v: View) {
        when (v) {
            back -> {
                PublicPracticalMethodFromJAVA.getInstance().activityFinish(this@TestPictureSelectorActivity, R.anim.slide_out_right)
            }
        }
    }

    /**
     * 添加图片框点击回调
     */
    override fun onAddPicClick() {
//        LogUtils.i(ConfigConstants.TAG_ALL, "开始选择图片")
        //进入相册
        PictureSelector.create(this@TestPictureSelectorActivity)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .maxSelectNum(pln_pics.maxSelectNum)
            .setCameraImageFormat(PictureMimeType.JPEG) // 相机图片格式后缀,默认.jpeg
            .selectionData(gridimageadapter!!.getData())// 是否传入已选图片
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {//图片选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    /**
                     * LocalMedia 里面返回五种path
                     * 1.media.getPath(); 原图path
                     * 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                     * 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                     * 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                     * 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                     * PS:如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                     */
                    for (media in selectList) {
                        if (media.width == 0 || media.height == 0) {
                            if (PictureMimeType.isHasImage(media.mimeType)) {
                                val imageExtraInfo: MediaExtraInfo = MediaUtils.getImageSize(media.path)
                                media.width = imageExtraInfo.width
                                media.height = imageExtraInfo.height
                            } else if (PictureMimeType.isHasVideo(media.mimeType)) {
                                val videoExtraInfo = MediaUtils.getVideoSize(this@TestPictureSelectorActivity, media.path)
                                media.width = videoExtraInfo.width
                                media.height = videoExtraInfo.height
                            }
                        }
//                        LogUtils.i(
//                            ConfigConstants.TAG_ALL,
//                            "是否压缩:" + media.isCompressed,
//                            "压缩:" + media.compressPath,
//                            "原图:" + media.path,
//                            "绝对路径:" + media.realPath,
//                            "是否裁剪:" + media.isCut,
//                            "裁剪地址:" + media.cutPath,
//                            "是否开启原图:" + media.isOriginal,
//                            "原图路径:" + media.originalPath,
//                            "Android Q 特有Path:" + media.androidQToPath,
//                            "宽高: " + media.width + "x" + media.height,
//                            "Size: " + media.size
//                        )
                    }
                    gridimageadapter?.let {
                        it.setList(selectList)
                        it.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    /**
     * 增加或者减少 回调事件
     */
    override fun onMinusOrPlusClick() {
        gridimageadapter?.let {
            it.setSelectMax(pln_pics.maxSelectNum)
        }
    }
}