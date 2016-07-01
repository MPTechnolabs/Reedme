package com.example.reedme.barcodescanner.camera;


import com.example.reedme.barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
}
