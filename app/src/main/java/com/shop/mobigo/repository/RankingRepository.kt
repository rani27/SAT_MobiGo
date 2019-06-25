package com.shop.mobigo.repository

import android.os.AsyncTask
import com.shop.mobigo.AppConstants
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.*

/**
 * Created by Rani Ugale on 6/20/2019.
 */
class RankingRepository(private val dataDao: DataDao) {

    fun getRankingList(listener: OnDBListener) {
        class InsertTask : AsyncTask<Void, Void, List<String>>() {
            override fun doInBackground(vararg params: Void): List<String> {
                return  dataDao.getRankingList()
            }
            override fun onPostExecute(result: List<String>) {
                super.onPostExecute(result)
                listener.onRecordRetrieve(result)
            }
        }
        InsertTask().execute()
    }


}