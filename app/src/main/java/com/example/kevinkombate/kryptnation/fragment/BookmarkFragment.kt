package com.example.kevinkombate.kryptnation.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinkombate.kryptnation.App
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.adapter.BookmarkRecyclerViewAdapter
import com.example.kevinkombate.kryptnation.adapter.TransactionRecyclerViewAdapter
import com.example.kevinkombate.kryptnation.model.Wallet
import com.example.kevinkombate.kryptnation.util.RxBus
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


class BookmarkFragment : BaseFragment() {

    private var listener: OnBookmarkFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(requireContext())
        updateUI()
        compositeDisposable.add(RxBus.listen(Wallet::class.java).subscribe { updateUI() })
    }

    fun updateUI() {
        launch(UI) {
            val query = async(CommonPool) {
                // Async stuff
                App.appDatabase.walletDao().getBookmarked()
            }
            val wallets = query.await().sortedBy { it.name }
            list.adapter = BookmarkRecyclerViewAdapter(wallets, listener)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBookmarkFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTransactionFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnBookmarkFragmentInteractionListener {
        fun onBookmarkClick(item: Wallet?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookmarkFragment()
    }
}
