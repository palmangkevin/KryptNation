package com.example.kevinkombate.kryptnation.fragment

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment: Fragment() {

    protected val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }

}