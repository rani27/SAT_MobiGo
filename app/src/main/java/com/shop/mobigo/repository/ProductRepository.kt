package com.shop.mobigo.repository

import android.os.AsyncTask
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.ProductValues

class ProductRepository(private val dataDao: DataDao) {

    fun getProductsBySelectedCat(selectedValue: String, listener: OnDBListener) {
        class InsertTask : AsyncTask<Void, Void, List<ProductValues>>() {
            override fun doInBackground(vararg params: Void): List<ProductValues> {
                return dataDao.getProductsByCat(selectedValue)

            }

            override fun onPostExecute(result: List<ProductValues>) {
                super.onPostExecute(result)
                listener.onRecordRetrieve(result)
            }
        }
        InsertTask().execute()
    }

    fun getProductsByRanking(selectedCat: String, selectedRanking: String, listener: OnDBListener) {
        class InsertTask : AsyncTask<Void, Void, List<ProductValues>>() {
            override fun doInBackground(vararg params: Void): List<ProductValues> {
                return dataDao.getProductsByRanking(selectedCat, selectedRanking)

            }

            override fun onPostExecute(result: List<ProductValues>) {
                super.onPostExecute(result)
                listener.onRecordRetrieve(result)
            }
        }
        InsertTask().execute()
    }
}
