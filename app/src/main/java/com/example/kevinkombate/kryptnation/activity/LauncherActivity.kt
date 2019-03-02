package com.example.kevinkombate.kryptnation.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.kevinkombate.kryptnation.App
import com.example.kevinkombate.kryptnation.R
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        supportActionBar?.hide()
        launch(UI) {
            val query = async(CommonPool) {App.appDatabase.walletDao().getAll()}
            if (query.await().isEmpty()) {
                startActivity(Intent(this@LauncherActivity, ScanActivity::class.java))
            } else {
                startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
            }
            ActivityCompat.finishAfterTransition(this@LauncherActivity)
        }

    }
}
