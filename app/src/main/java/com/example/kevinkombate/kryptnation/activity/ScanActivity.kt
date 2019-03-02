package com.example.kevinkombate.kryptnation.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.util.isValidEthereumAddress
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanActivity : AppCompatActivity() {

  lateinit var scan_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        supportActionBar?.hide() // Pour cacher la toolbar

        scan_button = findViewById(R.id.scan_button)
        val activity : Activity = this
        scan_button.setOnClickListener(object : View.OnClickListener{

            override fun onClick(v: View?) {
                val integrator = IntentIntegrator(activity)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                integrator.setPrompt("Scan your Ethereum wallet address")
                integrator.setCameraId(0)
                integrator.setBeepEnabled(false)
                integrator.setBarcodeImageEnabled(false)
                integrator.initiateScan()
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result:IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if(result != null){
            if(result.contents == null){
                Toast.makeText(this,"You cancelled the scanning process",
                        Toast.LENGTH_LONG).show()
            }


            else if(result.contents.isValidEthereumAddress()){
                val myIntent = Intent(this, EditWalletNameActivity::class.java)
                myIntent.putExtra("ETHEREUM_ADDRESS", result.contents.replace("ethereum:", ""))
                startActivity(myIntent)
                ActivityCompat.finishAfterTransition(this)
            }


            else {
                Toast.makeText(this,"This is not an Ethereum QR Code !",
                        Toast.LENGTH_LONG).show()
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}
