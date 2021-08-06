package com.example.kotlinmvpdemo.mvp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.RxViewUtils
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerTestPictureSelectorMainComponet
import com.example.kotlinmvpdemo.di.modules.TestPictureSelectorMainModule
import com.example.kotlinmvpdemo.mvp.presenters.TestPictureSelectorMainPresenter
import com.example.kotlinmvpdemo.mvp.ui.adapter.GridImageAdapter
import com.example.kotlinmvpdemo.mvp.views.TestPictureSelectorMainView
import com.example.kotlinmvpdemo.utils.FullyGridLayoutManager
import com.example.kotlinmvpdemo.utils.GlideEngine
import com.example.kotlinmvpdemo.weiget.PictureLayoutNumView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.animators.AnimationType
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.entity.MediaExtraInfo
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.PictureCropParameterStyle
import com.luck.picture.lib.style.PictureParameterStyle
import com.luck.picture.lib.style.PictureWindowAnimationStyle
import com.luck.picture.lib.tools.MediaUtils
import kotlinx.android.synthetic.main.activity_test_picture_selector_main.*
import javax.inject.Inject

@Route(path = RouterTag.TestPictureSelectorMainActivity)
class TestPictureSelectorMainActivity : BaseActivity(), TestPictureSelectorMainView,
    RxViewUtils.Action1<View>, GridImageAdapter.onAddPicClickListener, PictureLayoutNumView.onClickPictureLayoutNumListener,
    RadioGroup.OnCheckedChangeListener {

    @Inject
    lateinit var presenter: TestPictureSelectorMainPresenter

    private lateinit var gridimageadapter: GridImageAdapter

    private lateinit var mPictureParameterStyle: PictureParameterStyle

    private lateinit var mCropParameterStyle: PictureCropParameterStyle

    var isWeChatStyle: Boolean = false

    private var mWindowAnimationStyle = PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle()

    private var animationMode = AnimationType.DEFAULT_ANIMATION

    private var language = -1

    private var chooseMode = PictureMimeType.ofAll()

    private var selectionMode = PictureConfig.MULTIPLE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_picture_selector_main)

        initData()
        initListener()
    }

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerTestPictureSelectorMainComponet.builder()
            .myAppComponet(myAppComponet)
            .testPictureSelectorMainModule(TestPictureSelectorMainModule(this))
            .build()
            .inject(this)
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            com.example.testlibrary.R.color.full_red
        )
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_test_picture_selector_main
    }

    /**
     * 点击事件回调
     * PS:防止重新点击
     */
    override fun onRxViewClick(v: View) {
        when (v) {
            back -> {
                PublicPracticalMethodFromJAVA.getInstance().activityFinish(this@TestPictureSelectorMainActivity, R.anim.slide_out_right)
            }
        }
    }

    /**
     * 初始化数据
     */
    fun initData() {
        mPictureParameterStyle = presenter.getNumStyle(this@TestPictureSelectorMainActivity)
        mCropParameterStyle = presenter.mCropParameterStyle!!

        /** 初始化图片集合的RecyclerView **/
        //1.GridLayoutManager初始化
        val manager = FullyGridLayoutManager(this@TestPictureSelectorMainActivity, 4, GridLayoutManager.VERTICAL, false)
        rv_pics.layoutManager = manager
        //2.添加间距
        rv_pics.addItemDecoration(GridSpacingItemDecoration(4, resources.getDimensionPixelSize(R.dimen.dimen_5x), true))
        //3.初始化适配器
        gridimageadapter = GridImageAdapter(this@TestPictureSelectorMainActivity)
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
        rgb_style.setOnCheckedChangeListener(this)
        rgb_animation.setOnCheckedChangeListener(this)
        rgb_list_anim.setOnCheckedChangeListener(this)
        rgb_language.setOnCheckedChangeListener(this)
        rgb_photo_mode.setOnCheckedChangeListener(this)

        cb_original.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tv_original_tips.visibility = View.VISIBLE
            } else {
                tv_original_tips.visibility = View.GONE
            }
        }

        cb_choose_mode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cb_single_back.visibility = View.GONE
                selectionMode = PictureConfig.MULTIPLE
            } else {
                cb_single_back.visibility = View.VISIBLE
                selectionMode = PictureConfig.SINGLE
            }
        }


    }


    /**
     * 添加图片框点击回调
     */
    override fun onAddPicClick() {
        if (cb_mode.isChecked) {
            //进入相册
            PictureSelector.create(this@TestPictureSelectorMainActivity)
                .openGallery(chooseMode)
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(pln_pics.maxSelectNum)//设置相册最大显示多少
                .setCameraImageFormat(PictureMimeType.JPEG) // 相机图片格式后缀,默认.jpeg
                .selectionData(gridimageadapter!!.getData())// 是否传入已选图片
                .setPictureStyle(mPictureParameterStyle)//动态自定义相册主题
                .isWeChatStyle(isWeChatStyle)// 是否开启微信图片选择风格
                .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
                .isOpenClickSound(cb_voice.isChecked)// 是否开启点击声音
                .isOriginalImageControl(cb_original.isChecked)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，裁剪功能将会失效
                .setRecyclerAnimationMode(animationMode)// 列表动画效果
                .setLanguage(language)// 设置语言，默认中文
                .isPageStrategy(cbPage.isChecked, true)// 是否开启分页策略 & 每页多少条；默认开启
                .isMaxSelectEnabledMask(cbEnabledMask.isChecked)// 选择数到了最大阀值列表是否启用蒙层效果
                .selectionMode(selectionMode)// 多选 or 单选
                .isSingleDirectReturn(cb_single_back.isChecked)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isCamera(cb_isCamera.isChecked)// 是否显示拍照按钮
                .isGif(cb_isGif.isChecked)// 是否显示gif图片
                .isPreviewImage(cb_preview_img.isChecked)// 是否可预览图片
                .isPreviewVideo(cb_preview_video.isChecked)// 是否可预览视频
                .isEnablePreviewAudio(cb_preview_audio.isChecked) // 是否可播放音频
                .forResult(PictureConfig.CHOOSE_REQUEST)
        } else {
            //拍照
            PictureSelector.create(this@TestPictureSelectorMainActivity)
                .openCamera(chooseMode)
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(pln_pics.maxSelectNum)//设置相册最大显示多少
                .setCameraImageFormat(PictureMimeType.JPEG) // 相机图片格式后缀,默认.jpeg
                .selectionData(gridimageadapter!!.getData())// 是否传入已选图片
                .setPictureStyle(mPictureParameterStyle)//动态自定义相册主题
                .isWeChatStyle(isWeChatStyle)// 是否开启微信图片选择风格
                .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
                .isOpenClickSound(cb_voice.isChecked)// 是否开启点击声音
                .isOriginalImageControl(cb_original.isChecked)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，裁剪功能将会失效
                .setRecyclerAnimationMode(animationMode)// 列表动画效果
                .setLanguage(language)// 设置语言，默认中文
                .isPageStrategy(cbPage.isChecked, true)// 是否开启分页策略 & 每页多少条；默认开启
                .isMaxSelectEnabledMask(cbEnabledMask.isChecked)// 选择数到了最大阀值列表是否启用蒙层效果
                .forResult(PictureConfig.CHOOSE_REQUEST)
        }


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
                                val videoExtraInfo = MediaUtils.getVideoSize(this@TestPictureSelectorMainActivity, media.path)
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

    /**
     * 单项选择框接口回调
     */
    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.rb_default_style -> {
                isWeChatStyle = false
                mPictureParameterStyle = presenter.getNumStyle(this@TestPictureSelectorMainActivity)
                mCropParameterStyle = presenter.mCropParameterStyle!!
            }
            R.id.rb_we_chat_style -> {
                isWeChatStyle = true
                mPictureParameterStyle = presenter.getWeChatStyle(this@TestPictureSelectorMainActivity)
                mCropParameterStyle = presenter.mCropParameterStyle!!
            }
            R.id.rb_photo_default_animation -> {
                mWindowAnimationStyle = PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle()
            }
            R.id.rb_photo_up_animation -> {
                mWindowAnimationStyle = PictureWindowAnimationStyle.ofCustomWindowAnimationStyle(R.anim.picture_anim_up_in, R.anim.picture_anim_down_out)
            }
            R.id.rb_default -> {
                animationMode = AnimationType.DEFAULT_ANIMATION
            }
            R.id.rb_alpha -> {
                animationMode = AnimationType.ALPHA_IN_ANIMATION
            }
            R.id.rb_slide_in -> {
                animationMode = AnimationType.SLIDE_IN_BOTTOM_ANIMATION
            }
            R.id.rb_tw -> {
                language = LanguageConfig.TRADITIONAL_CHINESE
            }
            R.id.rb_us -> {
                language = LanguageConfig.ENGLISH
            }
            R.id.rb_ka -> {
                language = LanguageConfig.KOREA
            }
            R.id.rb_spanish -> {
                language = LanguageConfig.SPANISH
            }
            R.id.rb_system -> {
                language = -1
            }
            R.id.rb_all -> {
                chooseMode = PictureMimeType.ofAll()
            }
            R.id.rb_image -> {
                chooseMode = PictureMimeType.ofImage()
            }
            R.id.rb_video -> {
                chooseMode = PictureMimeType.ofVideo()
            }
            R.id.rb_audio -> {
                chooseMode = PictureMimeType.ofAudio()
            }
        }
    }

}
