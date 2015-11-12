package com.chn.halo.view.smartcamera.base;

import android.annotation.TargetApi;
import android.hardware.Camera;

import com.chn.halo.view.smartcamera.model.CameraInfo2;

/**
 * Description:
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 15/11/11
 */
@TargetApi(9)
public class CameraHelperGB implements CameraHelperImpl {
    @Override
    public int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    @Override
    public Camera openCamera(final int id) {
        return Camera.open(id);
    }

    @Override
    public Camera openDefaultCamera() {
        return Camera.open(0);
    }

    @Override
    public boolean hasCamera(final int facing) {
        return getCameraId(facing) != -1;
    }

    @Override
    public Camera openCameraFacing(final int facing) {
        return Camera.open(getCameraId(facing));
    }

    @Override
    public void getCameraInfo(final int cameraId, final CameraInfo2 cameraInfo) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        cameraInfo.facing = info.facing;
        cameraInfo.orientation = info.orientation;
    }

    private int getCameraId(final int facing) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int id = 0; id < numberOfCameras; id++) {
            Camera.getCameraInfo(id, info);
            if (info.facing == facing) {
                return id;
            }
        }
        return -1;
    }
}
