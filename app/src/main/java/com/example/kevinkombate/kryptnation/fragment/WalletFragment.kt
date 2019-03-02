package com.example.kevinkombate.kryptnation.fragment


import android.app.LauncherActivity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinkombate.kryptnation.App

import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.api.EtherScanApiManager
import com.example.kevinkombate.kryptnation.model.Result
import com.example.kevinkombate.kryptnation.model.Wallet
import com.example.kevinkombate.kryptnation.util.RxBus
import com.example.kevinkombate.kryptnation.util.toEther
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = WalletFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        compositeDisposable.add(RxBus.listen(Wallet::class.java).subscribe { updateUI() })
    }

    fun updateUI() {
        launch(UI) {
            val query = async(CommonPool) { // Async stuff
                App.appDatabase.walletDao().getAll()
            }
            val wallets = query.await()
            if(wallets.isNotEmpty()) {
                val wallet = wallets.first()
                walletName.text = wallet.name
                walletAddress.text = wallet.address
                EtherScanApiManager
                        .singleAccountBalance(wallet.address)
                        .enqueue(object : Callback<Result<String>> {
                            override fun onFailure(call: Call<Result<String>>?, t: Throwable?) {}

                            override fun onResponse(call: Call<Result<String>>?, response: Response<Result<String>>?) {
                                amount.text = """Ξ ${response?.body()?.result?.toEther()}"""
                            }
                        })
            } else {
                startActivity(Intent(this@WalletFragment.requireContext(), LauncherActivity::class.java ))
            }
        }
    }

}
