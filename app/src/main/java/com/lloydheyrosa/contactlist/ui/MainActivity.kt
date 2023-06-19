package com.lloydheyrosa.contactlist.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lloydheyrosa.commons.utils.ConnectivityFailure
import com.lloydheyrosa.commons.utils.Failure
import com.lloydheyrosa.contactlist.BR
import com.lloydheyrosa.contactlist.R
import com.lloydheyrosa.contactlist.databinding.ActivityMainBinding
import com.lloydheyrosa.contactlist.databinding.ModalBottomContactDetailsBinding
import com.lloydheyrosa.contactlist.ui.adapter.ContactsAdapter
import com.lloydheyrosa.contactlist.viewmodel.MainViewModel
import com.lloydheyrosa.domain.model.Contact
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val listAdapter = ContactsAdapter {
        showContactDetails(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        viewDataBinding.swipeRefreshLayoutContacts.setOnRefreshListener {
            viewModel.initialize()
        }

        setupAdapter()
        subscribeUI()
        viewModel.initialize()
    }

    private fun setupAdapter() {
        viewDataBinding.recyclerViewContacts.adapter = listAdapter
    }

    private fun subscribeUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch { viewModel.errorRelay.collect(::getErrorMessage) }
                launch {
                    viewModel.isLoading.collect {
                        viewDataBinding.swipeRefreshLayoutContacts.isRefreshing = it
                    }
                }
                launch { viewModel.contacts.collect(listAdapter::setData) }
            }
        }
    }

    private fun showContactDetails(contact: Contact) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val dialogBinding = ModalBottomContactDetailsBinding.inflate(layoutInflater, null, false)

        dialogBinding.contact = contact

        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
    }

    private fun getErrorMessage(failure: Failure<*>): String {
        return when (failure) {
            ConnectivityFailure -> {
                "No Internet Connection\n" +
                        "Please connect to the internet to continue using this app."
            }

            else -> {
                "There's an error while loading all contacts.\n" +
                        "Please try again later."
            }
        }
    }
}