package com.example.kevinkombate.kryptnation.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.kevinkombate.kryptnation.App
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.model.Wallet
import com.example.kevinkombate.kryptnation.util.RxBus
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.*

class EditWalletNameActivity : AppCompatActivity() {

    private lateinit var ethereumAddress: String
    private var isFromMainActivity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.hide() // Pour cacher la toolbar
        ethereumAddress = intent.getStringExtra("ETHEREUM_ADDRESS")
        isFromMainActivity = intent.getBooleanExtra("FROM_MAIN_ACTIVITY", false)
        wallet_address.text = ethereumAddress
        addToBookmarkCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                renameEditTextInputLayout.visibility = VISIBLE
            } else {
                renameEditTextInputLayout.visibility = GONE
            }
        }
        getStart.setOnClickListener {
            if(addToBookmarkCheckbox.isChecked && (renameEditText.text.isEmpty() || renameEditText.text.isBlank())) {
                Toast.makeText(this, getString(R.string.error_when_try_to_add_to_bookmark),
                        Toast.LENGTH_SHORT).show()
            } else {
                launch(UI) {
                    val wallet = Wallet(ethereumAddress,
                            renameEditText.text.toString(), addToBookmarkCheckbox.isChecked, Date().time)

                    val query = async(CommonPool) { // Async stuff
                        App.appDatabase.walletDao().insertAll(wallet)
                    }
                    query.await()
                    ActivityCompat.finishAfterTransition(this@EditWalletNameActivity)
                    if(isFromMainActivity){
                        RxBus.publish(wallet)
                    } else {
                        startActivity(Intent(this@EditWalletNameActivity, MainActivity::class.java))
                    }
                }
            }
        }
    }

}