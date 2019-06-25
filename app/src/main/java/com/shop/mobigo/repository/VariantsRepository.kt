package com.shop.mobigo.repository

import android.os.AsyncTask
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.*

/**
 * Created by Rani Ugale on 6/20/2019.
 */
class VariantsRepository(private val dataDao: DataDao) {

    fun getVariants(prod_name: String,listener: OnDBListener) {
        class InsertTask : AsyncTask<Void, Void, List<Variants>>() {
            override fun doInBackground(vararg params: Void): List<Variants> {
                return dataDao.getVariants(prod_name)

            }
            override fun onPostExecute(result: List<Variants>) {
                super.onPostExecute(result)
                listener.onRecordRetrieve(result)
            }
        }
        InsertTask().execute()
    }

}