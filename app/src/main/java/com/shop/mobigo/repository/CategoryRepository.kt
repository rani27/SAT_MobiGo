package com.shop.mobigo.repository

import android.os.AsyncTask
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.*

class CategoryRepository(private val dataDao: DataDao) {

    fun getCatList(listener: OnDBListener) {
        class InsertTask : AsyncTask<Void, Void, List<ParentCategory>>() {
            override fun doInBackground(vararg params: Void): List<ParentCategory> {
                return dataDao.getParentCatList()
            }

            override fun onPostExecute(result: List<ParentCategory>) {
                super.onPostExecute(result)
                listener.onRecordRetrieve(result)
            }
        }
        InsertTask().execute()
    }

}