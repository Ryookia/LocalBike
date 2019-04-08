package com.example.localbike.ui.fragment

import butterknife.Unbinder
import dagger.android.support.DaggerFragment

abstract class StandardFragment : DaggerFragment() {
    protected lateinit var unbinder: Unbinder

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}