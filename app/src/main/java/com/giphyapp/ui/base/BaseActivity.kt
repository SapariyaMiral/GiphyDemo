/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 https://www.spaceotechnologies.com
 *
 * Permissions is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.giphyapp.ui.base

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.giphyapp.R
import com.google.android.material.snackbar.Snackbar



abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {


    protected abstract fun getViewBinding(): VB

    protected abstract fun listeners()

    protected abstract fun initSetup()

    protected lateinit var binding: VB

    protected open fun addObservers() {/*Add observer in this method*/
    }

    protected open fun removeObservers() {/*Remove observer in this method*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

        addObservers()
        initSetup()
        listeners()
    }

    override fun attachBaseContext(newBase: Context) {
    }


    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
