package com.chn.halo.view.smartcamera.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.chn.halo.view.smartcamera.model.PhotoItem;
import com.chn.halo.view.smartcamera.ui.CameraActivity;
import com.chn.halo.view.smartcamera.ui.PhotoProcessActivity;
import com.chn.halo.view.smartcamera.util.ImageUtils;

import java.util.Stack;

/**
 * Description:相机管理类
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 15/11/12
 */
public class CameraManager {
    private static CameraManager mInstance;
    private Stack<Activity> cameras = new Stack<Activity>();

    public static CameraManager getInst() {
        if (mInstance == null) {
            synchronized (CameraManager.class) {
                if (mInstance == null)
                    mInstance = new CameraManager();
            }
        }
        return mInstance;
    }

    //打开照相界面
    public void openCamera(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    //判断图片是否需要裁剪
    public void processPhotoItem(Activity activity, PhotoItem photo) {
        Uri uri = photo.getImageUri().startsWith("file:") ? Uri.parse(photo
                .getImageUri()) : Uri.parse("file://" + photo.getImageUri());
        if (ImageUtils.isSquare(photo.getImageUri())) {
            Intent newIntent = new Intent(activity, PhotoProcessActivity.class);
            newIntent.setData(uri);
            activity.startActivity(newIntent);
        } else {
//            Intent i = new Intent(activity, CropPhotoActivity.class);
//            i.setData(uri);
//            //TODO稍后添加
//            activity.startActivityForResult(i, CameraConfig.REQUEST_CROP);
        }
    }

    public void close() {
        for (Activity act : cameras) {
            try {
                act.finish();
            } catch (Exception e) {

            }
        }
        cameras.clear();
    }

    public void addActivity(Activity act) {
        cameras.add(act);
    }

    public void removeActivity(Activity act) {
        cameras.remove(act);
    }
}
