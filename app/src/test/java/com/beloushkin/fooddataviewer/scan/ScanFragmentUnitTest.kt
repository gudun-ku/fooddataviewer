package com.beloushkin.fooddataviewer.scan

import android.Manifest
import androidx.test.rule.GrantPermissionRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule

class ScanFragmentUnitTest {

    @Rule
    @JvmField
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.CAMERA
    )

    private val mockWebServer = MockWebServer()

    //private val json

}