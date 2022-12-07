package com.giphyapp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.giphyapp.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    open lateinit var binding: VB

    protected abstract fun getViewBinding(): VB

    protected abstract fun listeners()

    protected abstract fun initSetup()


    protected open fun addObservers() {/*Add observer in this method*/
    }

    protected open fun removeObservers() {/*Remove observer in this method*/
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding()
        super.onCreateView(inflater, container, savedInstanceState)

        initSetup()
        listeners()
        addObservers()
        return binding.root
    }

    fun setUpToolbar(toolbar: Toolbar, title: String) {
        toolbar.title = title
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    var snackbar: Snackbar? = null

       override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

}