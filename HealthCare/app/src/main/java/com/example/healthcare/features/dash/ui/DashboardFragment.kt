package com.example.healthcare.features.dash.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.databinding.FragmentDashboardBinding
import com.example.healthcare.features.dash.ui.adapter.GlossaryAdapter
import com.example.healthcare.features.dash.ui.viewmodel.GlossaryViewModel
import com.example.healthcare.features.dash.ui.viewmodel.GlossaryViewModel.GlossaryUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val mViewModel: GlossaryViewModel by viewModels()

    private var _viewBinding: FragmentDashboardBinding? = null
    private val viewBinding: FragmentDashboardBinding
        get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        setPaddingAnimation()
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        observer()
    }

    private fun observer() {
        observeState()
        observeGlossary()
    }

    private fun observeGlossary() {
        val layoutManager = LinearLayoutManager(context)
        val recyclerView = _viewBinding?.recyclerview
        recyclerView?.layoutManager = layoutManager

        var itemsScrolled = 0

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                itemsScrolled += dy
                if (itemsScrolled >= recyclerView.height * 5 && dy > 0) {
                    _viewBinding?.dashQuote?.visibility = View.GONE
                    itemsScrolled = 0
                }

                if (dy < 0) {
                    _viewBinding?.dashQuote?.visibility = View.VISIBLE
                }
            }
        })
        mViewModel.mGlossaryDetails
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { glossary ->
                if (glossary != null) {
                    if (glossary.glossary.isNotEmpty()) {
                        val adapter = GlossaryAdapter(glossary.glossary, requireContext())
                        recyclerView?.adapter = adapter
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeState() {
        mViewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: GlossaryUiState) {
        when (state) {
            is GlossaryUiState.IsLoading -> handleLoading(state.isLoading)
            is GlossaryUiState.Init -> Unit
            is GlossaryUiState.Error -> requireActivity().showToast(state.message)
        }
    }

    private fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            viewBinding.loadingProgressBar.visibility = View.VISIBLE
        } else {
            viewBinding.loadingProgressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun setPaddingAnimation() {
        val duration = 1000
        Handler().postDelayed({
            _viewBinding?.recyclerview?.visibility = View.VISIBLE
        }, duration.toLong())

    }
}
