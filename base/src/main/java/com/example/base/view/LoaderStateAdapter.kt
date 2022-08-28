package com.example.base.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.base.databinding.ItemNetworkStateBinding


/**
 * Pagination list load state adapter.
 *
 * Managing (loading, error & success) states of [PagingDataAdapter].
 * @param retry the block to execute on retry.
 * @see BasePagedListAdapter
 */
class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    /**
     * View holder class for footer loader and error state handling.
     */
    class LoaderViewHolder(val binding: ItemNetworkStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retry()
            }
        }

        companion object {

            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val binding = ItemNetworkStateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LoaderViewHolder(binding, retry)
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}