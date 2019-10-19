package com.yuyakaido.android.gaia.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.yuyakaido.android.gaia.core.java.AvailableEnvironment
import com.yuyakaido.android.gaia.core.java.Environment
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

class SelectEnvironmentDialog : DaggerAppCompatDialogFragment() {

  companion object {
    fun newInstance(): DialogFragment {
      return SelectEnvironmentDialog()
    }
  }

  interface OnDismissListener {
    fun onDismiss(environment: Environment)
  }

  private var listener: OnDismissListener? = null

  @Inject
  lateinit var available: AvailableEnvironment

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDismissListener) {
      listener = context
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val items = available.environments
      .map { it.title }
      .toTypedArray()
    return AlertDialog.Builder(requireContext())
      .setTitle("Select environment")
      .setItems(items) { _, which -> listener?.onDismiss(available.environments[which]) }
      .create()
  }

}