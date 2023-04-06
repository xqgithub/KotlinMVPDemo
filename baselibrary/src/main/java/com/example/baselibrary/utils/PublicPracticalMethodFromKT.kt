import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View


/**
 * Date:2021/11/12
 * Time:9:55
 * author:dimple
 * 公共实用类 kotlin
 */
class PublicPracticalMethodFromKT {

    companion object {
        val ppmfKT: PublicPracticalMethodFromKT by
        lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PublicPracticalMethodFromKT()
        }
    }

    /**
     * 是否为空白,包括null和""
     */
    fun isBlank(str: Any?): Boolean {
        return str == null ||
                (str is String && str.toString().trim().isEmpty()) ||
                (str is String && "null" == str.toString().toUpperCase())
    }

    fun isBlank(vararg strs: Any?): Boolean {
        for (str in strs) {
            if (isBlank(str)) {
                return true
            }
        }
        return false
    }

    /**
     * 页面跳转
     * @param mActivity   上下文
     * @param cls         目标的Activity
     * @param flag        跳转的方式
     * @param bundle      bundle值
     * @param isFinish    进入页面后，是否结束上一个页面
     * @param enterAnimID 进入动画ID
     * @param exitAnimID  退出动画ID
     */
    fun intentToJump(
        mActivity: Activity, cls: Class<*>,
        bundle: Bundle? = null, isFinish: Boolean = false,
        enterAnimID: Int = -1, exitAnimID: Int = -1,
        flag: Int = -1
    ) {
        val intent = Intent(mActivity, cls)
        intent.apply {
            bundle?.let { putExtras(it) }
            if (flag != -1) flags = flag
            mActivity.startActivity(this)
            if (isFinish) mActivity.finish()
            if (enterAnimID == -1 && exitAnimID == -1) {
                return
            } else {
                mActivity.overridePendingTransition(
                    if (enterAnimID == -1) 0 else enterAnimID,
                    if (exitAnimID == -1) 0 else exitAnimID
                )
            }
        }
    }

    /**
     * Acticity 页面关闭,可以传动动画文件
     * @param mActivity
     * @param exitAnimID
     */
    fun activityFinish(activity: Activity, enterAnimID: Int = 0, exitAnimID: Int = 0) {
        activity.finish()
        activity.overridePendingTransition(enterAnimID, exitAnimID)
    }


    /**
     * 动态设置Shape  RECTANGLE        背景色或者渐变色
     * @param view                    被设置的对象view
     * @param CornerRadiusLeftTop     左上角度数
     * @param CornerRadiusRightTop    右上角度数
     * @param CornerRadiusLeftBottom  左下角度数
     * @param CornerRadiusRightBottom 右下角度数
     * @param strokeWidth             边线的宽度
     * @param strokeColor             边线的颜色
     * @param orientation             渐变方向
     * @param bgColor                 渐变色
     */
    fun setDynamicShapeRectangle(
        view: View,
        strokeWidth: Int = 0, strokeColor: String = "",
        CornerRadiusLeftTop: Float = 0f, CornerRadiusRightTop: Float = 0f,
        CornerRadiusLeftBottom: Float = 0f, CornerRadiusRightBottom: Float = 0f,
        orientation: GradientDrawable.Orientation?, vararg bgColor: String
    ) {
        val drawable = GradientDrawable()
        drawable.apply {
            //设置shape的形状
            shape = GradientDrawable.RECTANGLE
            //设置shape的圆角度数
            val radius = floatArrayOf(
                CornerRadiusLeftTop, CornerRadiusLeftTop, CornerRadiusRightTop, CornerRadiusRightTop,
                CornerRadiusRightBottom, CornerRadiusRightBottom, CornerRadiusLeftBottom, CornerRadiusLeftBottom
            )
            cornerRadii = radius
            //设置shape的边的宽度和颜色
            if (strokeWidth != -1 && !isBlank(strokeColor)) setStroke(strokeWidth, Color.parseColor(strokeColor))
            //设置shape的背景色
            if (!isBlank(bgColor)) {
                if (bgColor.size > 1 && !isBlank(orientation)) {
                    val colors = IntArray(bgColor.size)
                    for (i in bgColor.indices) {
                        colors[i] = Color.parseColor(bgColor[i])
                    }
                    setOrientation(orientation)
                    setColors(colors)
                } else {
                    setColor(Color.parseColor(bgColor[0]))
                }
            }
            view.background = this
        }
    }

    /**
     * 动态设置Shape OVAL
     * @param view 被设置的对象view
     * @param strokeWidth 边线的宽度
     * @param strokeColor 边线的颜色
     * @param width 圆的大小
     * @param bgColor 圆的背景色
     */
    fun setDynamicShapeOVAL(
        view: View,
        width: Int = 0, bgColor: String,
        strokeWidth: Int = 0, strokeColor: String = ""
    ) {
        val drawable = GradientDrawable()
        drawable.apply {
            //设置shape的形状
            shape = GradientDrawable.OVAL
            //设置shape的边的宽度和颜色
            if (strokeWidth != -1 && !isBlank(strokeColor)) setStroke(strokeWidth, Color.parseColor(strokeColor))
            //设置shape的背景色
            if (!isBlank(bgColor)) setColor(Color.parseColor(bgColor))
            //设置圆的size大小
            setSize(width, width)


            view.background = this
        }
    }

    /**
     * 判断是否安装过该应用
     * @param packageName 被检测的应用包名
     */
    fun isInstallApp(context: Context, packageName: String): Boolean {
        //获取packagemanager
        val packageManager = context.packageManager
        //获取所有已安装程序的包信息
        val packageInfos = packageManager.getInstalledPackages(0)
        if (!isBlank(packageInfos, packageName)) {
            for (packageinfo in packageInfos) {
                if (packageName == packageinfo.packageName) {
                    return true
                }
            }
        }
        return false
    }
}