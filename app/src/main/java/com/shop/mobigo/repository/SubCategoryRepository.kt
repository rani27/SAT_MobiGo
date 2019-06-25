package com.shop.mobigo.repository

import android.os.AsyncTask
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.*

/**
 * Created by Rani Ugale on 6/20/2019.
 */
class SubCategoryRepository(private val dataDao: DataDao) {

    fun getSubCatList(subCatId: List<Int>,listener: OnDBListener) {
        class InsertTask : AsyncTask<Void, Void, List<ParentCategory>>() {
            override fun doInBackground(vararg params: Void): List<ParentCategory> {
                  return  dataDao.getSubCatList(subCatId)
            }
            override fun onPostExecute(result: List<ParentCategory>) {
                super.onPostExecute(result)
                listener.onRecordRetrieve(result)
            }
        }
        InsertTask().execute()
    }

}