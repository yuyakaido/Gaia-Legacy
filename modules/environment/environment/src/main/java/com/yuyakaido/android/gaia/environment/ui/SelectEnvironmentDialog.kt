package com.yuyakaido.android.gaia.environment.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.yuyakaido.android.gaia.core.java.AppDispatcher
import com.yuyakaido.android.gaia.core.java.AppSignal
import com.yuyakaido.android.gaia.core.java.AvailableEnvironment
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

class SelectEnvironmentDialog : DaggerAppCompatDialogFragment() {

    companion object {
        fun newInstance(): DialogFragment {
            return SelectEnvironmentDialog()
        }
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    private var listener: OnDismissListener? = null

    @Inject
    lateinit var available: AvailableEnvironment

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDismissListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = available.environments
            .map { it.githubApiEndpoint }
            .toTypedArray()
        return AlertDialog.Builder(requireContext())
            .setTitle("Select environment")
            .setItems(items) { _, which ->
                AppDispatcher.dispatch(
                    AppSignal.OpenSession(
                        environment = available.environments[which]
                    )
                )
            }
            .create()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        listener?.onDismiss()
    }

}