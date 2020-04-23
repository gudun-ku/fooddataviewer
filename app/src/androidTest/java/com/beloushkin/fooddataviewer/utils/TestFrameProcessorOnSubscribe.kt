package com.beloushkin.fooddataviewer.utils

import com.beloushkin.fooddataviewer.scan.utils.FrameProcessorOnSubscribe
import io.fotoapparat.preview.Frame

class TestFrameProcessorOnSubscribe : FrameProcessorOnSubscribe() {

    var testFrame: Frame? = null

    override fun invoke(frame: Frame) {
        if (testFrame != null) {
            super.invoke(testFrame!!)
            // for stop sending frames after test one was sent to frame processor
            complete()
        }
    }
}