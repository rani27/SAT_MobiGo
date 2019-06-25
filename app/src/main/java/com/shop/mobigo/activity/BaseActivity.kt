package com.shop.mobigo.activity

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.shop.mobigo.R


open class BaseActivity : AppCompatActivity() {

    lateinit var mToast: Toast
    private val mProgressBar: AlertDialog by lazy {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_bar_layout)
        builder.create()
    }

    fun showProgressBar() {
        mProgressBar.show()
    }

    fun stopProgressBar() {
        mProgressBar.dismiss()
    }

    fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.lbl_ok)) { dialog, _ ->
            dialog.dismiss()
            this.finish()
        }
        builder.create().show()
    }

    fun showToastMessage(message: String) {
        if (::mToast.isInitialized) {
            mToast.cancel()
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        mToast.show()

    }

    fun setUpToolBar(toolbar: Toolbar, title: String) {
        toolbar.title = title
        toolbar.navigationIcon = getDrawable(R.drawable.white_back)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener {
            this.finish()
        }
    }
}