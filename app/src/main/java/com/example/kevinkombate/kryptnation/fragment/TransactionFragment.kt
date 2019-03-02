package com.example.kevinkombate.kryptnation.fragment

import android.app.LauncherActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinkombate.kryptnation.App
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.adapter.TransactionRecyclerViewAdapter
import com.example.kevinkombate.kryptnation.api.EtherScanApiManager
import com.example.kevinkombate.kryptnation.model.Result
import com.example.kevinkombate.kryptnation.model.Transaction
import com.example.kevinkombate.kryptnation.model.Wallet
import com.example.kevinkombate.kryptnation.util.RxBus
import com.example.kevinkombate.kryptnation.util.toEther
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionFragment : BaseFragment() {

    private var listener: OnTransactionFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(context)
        updateUI()
        compositeDisposable.add(RxBus.listen(Wallet::class.java).subscribe { updateUI() })
    }

    fun updateUI() {
        launch(UI) {
            val query = async(CommonPool) {
                // Async stuff
                App.appDatabase.walletDao().getAll()
            }
            val wallets = query.await()
            if (wallets.isNotEmpty()) {
                val wallet = wallets[0]
                EtherScanApiManager
                        .transactions(wallet.address)
                        .enqueue(object : Callback<Result<List<Transaction>>> {
                            override fun onResponse(call: Call<Result<List<Transaction>>>?,
                                                    response: Response<Result<List<Transaction>>>?) {
                                response?.body()?.let {
                                    list.adapter = TransactionRecyclerViewAdapter(wallet.address, requireContext(),
                                            it.result, listener)
                                }
                            }

                            override fun onFailure(call: Call<Result<List<Transaction>>>?, t: Throwable?) {}
                        })
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTransactionFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTransactionFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnTransactionFragmentInteractionListener {
        fun onTransactionClick(item: Transaction?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}
