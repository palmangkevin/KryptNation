package com.example.kevinkombate.kryptnation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.kevinkombate.kryptnation.fragment.BookmarkFragment
import com.example.kevinkombate.kryptnation.fragment.TransactionFragment
import com.example.kevinkombate.kryptnation.fragment.WalletFragment

class MainPagerAdapter(fragementManager: FragmentManager): FragmentPagerAdapter(fragementManager) {

    override fun getItem(position: Int) = when(position) {
        1 -> TransactionFragment.newInstance()
        2 -> BookmarkFragment.newInstance()
        else -> WalletFragment.newInstance()
    }

    override fun getCount() = 3
}