package com.example.kevinkombate.kryptnation.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.example.kevinkombate.kryptnation.App
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.adapter.MainPagerAdapter
import com.example.kevinkombate.kryptnation.fragment.BookmarkFragment
import com.example.kevinkombate.kryptnation.fragment.TransactionFragment
import com.example.kevinkombate.kryptnation.model.Transaction
import com.example.kevinkombate.kryptnation.model.Wallet
import com.example.kevinkombate.kryptnation.util.RxBus
import com.example.kevinkombate.kryptnation.util.isValidEthereumAddress
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.*


class MainActivity : AppCompatActivity(),
        TransactionFragment.OnTransactionFragmentInteractionListener,
        BookmarkFragment.OnBookmarkFragmentInteractionListener {

    override fun onBookmarkClick(item: Wallet?) {

    }

    override fun onTransactionClick(item: Transaction?) {

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_wallet -> {
                supportActionBar?.setTitle(R.string.title_accounts)
                viewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transactions -> {
                supportActionBar?.setTitle(R.string.title_transactions)
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarks -> {
                supportActionBar?.setTitle(R.string.title_bookmarks)
                viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle(R.string.title_accounts)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 3
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_qr_code -> {
                val integrator = IntentIntegrator(this)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                integrator.setPrompt("Scan your Ethereum wallet address")
                integrator.setCameraId(0)
                integrator.setBeepEnabled(false)
                integrator.setBarcodeImageEnabled(false)
                integrator.initiateScan()
                return true
            }
            else -> true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if(result != null){
            if(result.contents == null){
                Toast.makeText(this,"You cancelled the scanning process",
                        Toast.LENGTH_LONG).show()
            }


            else if(result.contents.isValidEthereumAddress()){
                val myIntent = Intent(this, EditWalletNameActivity::class.java)
                myIntent.putExtra("ETHEREUM_ADDRESS", result.contents.replace("ethereum:", ""))
                myIntent.putExtra("FROM_MAIN_ACTIVITY", true)
                startActivity(myIntent)
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
