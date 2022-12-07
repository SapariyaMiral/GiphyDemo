package com.giphyapp.ui.giphyui


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.giphyapp.databinding.FragmentTrendingBinding
import com.giphyapp.ui.adapter.GifAdapterPagging
import com.giphyapp.ui.base.BaseFragment
import com.giphyapp.utils.JLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class TrendingPaggingFragment :  BaseFragment<FragmentTrendingBinding>(){

    private val viewModel: TrendingViewModelPagging by viewModels()

    @Inject
    lateinit var adapter: GifAdapterPagging
  // private var isConnectivityavailable : Boolean =false

    override fun getViewBinding() = FragmentTrendingBinding.inflate(layoutInflater)

    override fun listeners() {

        //set pagging adapter
          setAdapter("")

        //listner for search
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                 hideKeyboard()
                 setAdapter(binding.etSearch.text.toString().trim())
            }
            true
        }

        var isOnTextChanged = false
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isOnTextChanged = true;
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isOnTextChanged) {
                    isOnTextChanged = false;

                       if(s?.length == 0) {
                        hideKeyboard()
                        setAdapter(s.toString())
                    }

                }
            }
        })

      binding.swipeContainer.setOnRefreshListener(OnRefreshListener {
            binding.swipeContainer.setRefreshing(false)
            binding.etSearch.setText("")
            setAdapter("")
        })
    }


    override fun onResume() {
        super.onResume()
        (activity as GiphyMainActivity).liveDataInternet.observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected) {
                setvisibiltyOn()
                adapter.retry()

            } else {
                setVisibilityOff()

            }
        })

    }
    override fun removeObservers() {
    }

    override fun initSetup() {
    }

    private fun setAdapter(query : String) {

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        if(query.isEmpty()) {
            viewModel.getTrendingGif()
                .observe(viewLifecycleOwner, Observer {
                    lifecycleScope.launch {
                        adapter.submitData(it)
                    }
                })

        }else{
            viewModel.getTrendingGifSearch(query=query)
                .observe(viewLifecycleOwner, Observer {
                    lifecycleScope.launch {
                        adapter.submitData(it)
                    }
                })
        }
        // set up pagging3 adapter
        adapter.addLoadStateListener { loadState ->

            loadState.mediator?.let {
            if (it.refresh is LoadState.Error) {
                loadState.refresh as LoadState.Error
            }else {
               loadState.append
            }
        }

            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading) {

                binding.progressBar.isVisible = true
            }
            else {
                binding.progressBar.isVisible = false

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {

                }

            }
        }

    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
    }

    private fun setvisibiltyOn()
    {
        binding.tvNointernet.visibility=View.GONE
        binding.progressBar.visibility=View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.etSearch.visibility=View.VISIBLE
        binding.swipeContainer.visibility = View.VISIBLE
    }

    private fun setVisibilityOff()
    {
        binding.tvNointernet.visibility=View.GONE
        binding.progressBar.visibility=View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.etSearch.visibility=View.VISIBLE
        binding.swipeContainer.visibility = View.VISIBLE
    }

}