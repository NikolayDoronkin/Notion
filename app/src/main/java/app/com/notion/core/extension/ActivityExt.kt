package app.com.notion.core.extension

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

fun Activity.hideSoftKeyboardExt() {
    window.setSoftInputMode(SOFT_INPUT_STATE_HIDDEN)
    currentFocus?.apply {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Activity.showInfoSnackbarExt(
    @StringRes resId: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    maxLines: Int = 2
) {
    Snackbar.make(findViewById(android.R.id.content), resId, duration).apply {
        if (maxLines > 2) {
            //todo: in future android updates check R.id.snackbar_text
            val textView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.maxLines = maxLines
        }
        show()
    }
}

fun Activity.showErrorSnackbarExt(
    @StringRes messageResId: Int,
    @StringRes actionResId: Int,
    listener: (v: View) -> Unit
) {
    Snackbar.make(findViewById(android.R.id.content), messageResId, Snackbar.LENGTH_INDEFINITE)
        .apply {
            setAction(actionResId, View.OnClickListener(listener::invoke))
            show()
        }
}

fun Activity.showAlertDialogExt(
    @StringRes messageId: Int,
    listener: () -> Unit
): AlertDialog {
    val dialog = AlertDialog.Builder(this)
        .setMessage(messageId)
        .setPositiveButton(android.R.string.ok) { _, _ ->
            listener.invoke()
        }
        .setNegativeButton(android.R.string.cancel) { _, _ -> }
        .create()
    dialog.show()
    return dialog
}



