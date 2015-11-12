package com.chn.halo.view.smartcamera.base;

import android.hardware.Camera;

import com.chn.halo.view.smartcamera.model.CameraInfo2;

/**
 * Description:
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 15/11/11
 */
public interface CameraHelperImpl {
    int getNumberOfCameras();

    Camera openCamera(int id);

    Camera openDefaultCamera();

    Camera openCameraFacing(int facing);

    boolean hasCamera(int cameraFacingFront);

    void getCameraInfo(int cameraId, CameraInfo2 cameraInfo);
}
