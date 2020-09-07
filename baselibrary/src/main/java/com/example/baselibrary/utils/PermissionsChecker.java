package com.example.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

/**
 * 检查权限的工具类
 */
public class PermissionsChecker {

    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
//        int flag = App.getApplication().checkSelfPermission(permission);
//        int flag1 = PermissionChecker.checkSelfPermission(App.getApplication(), permission);
//        int flag2 = ActivityCompat.checkSelfPermission(App.getApplication(), permission);
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
