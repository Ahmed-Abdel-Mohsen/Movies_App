package com.example.base.view

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.base.R
import com.example.base.view.BaseViewModel.ErrorMessage.StringErrorMessage
import com.example.base.view.BaseViewModel.ErrorMessage.StringResErrorMessage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * Parent of all activities.
 * The generic [DB] is used to use data binding of the desired type directly.
 */
abstract class BaseActivity<DB : ViewDataBinding, STATE : UiState, VM : BaseViewModel<STATE>> :
    AppCompatActivity() {

    lateinit var viewModel: VM


    private var _binding: DB? = null
    protected val binding: DB
        get() {
            return _binding ?: error("data binding should not be null")
        }

    protected open var onSingleEvent: ((SingleEvent) -> Unit)? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getVM(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, getLayoutRes()) as DB
        viewModel = getVM()

        setGenericToolBar()
        subscribeBaseEvents()
        onViewAttach()
        subscribeUiState()
    }

    private fun subscribeUiState() {
        viewModel.uiStateFlow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                renderState(it)
            }
            .launchIn(lifecycleScope)
    }

    abstract fun renderState(uiState: STATE)

    private fun subscribeBaseEvents() {

        viewModel.errorMsgEventFlow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                // applicationContext not activity context to avoid activity leak on back pressed while toast is still showing
                when (it) {
                    is StringErrorMessage -> Toast.makeText(
                        applicationContext,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    is StringResErrorMessage -> Toast.makeText(
                        applicationContext,
                        it.messageResId,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .launchIn(lifecycleScope)

        onSingleEvent?.let {
            viewModel.singleEventFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { event ->
                    it(event)
                }.launchIn(lifecycleScope)
        }
    }


    override fun onStart() {
        super.onStart()
        onViewRefresh()
    }

    /**
     * called in onStart
     */
    open fun onViewRefresh() {
    }

    /**
     * called in onCreate
     */
    open fun onViewAttach() {
    }

    abstract fun getToolbarTitle(): Any?

    private fun setGenericToolBar() {

        val title = getToolbarTitle()

        title?.let {

            val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)

            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val titleTxt = when (it) {
                is String -> it
                is Int -> resources.getString(it)
                else -> throw UnsupportedOperationException(
                    "Title must be a string resource or a plain string"
                )
            }

            supportActionBar?.title = titleTxt
        }
    }

}