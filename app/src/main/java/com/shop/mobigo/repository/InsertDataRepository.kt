package com.shop.mobigo.repository

import android.os.AsyncTask
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.listeners.OnDataInsertListener
import com.shop.mobigo.models.*
import com.shop.mobigo.viewModels.JsonAPIViewModel

class InsertDataRepository(private val dataDao: DataDao) {

    fun insertDataToDB(response: JsonResponseModel, listener: OnDBListener) {
        val category = response.categories
        val ranking = response.rankings
        val productList: MutableList<Products> = mutableListOf()
        val variantsList: MutableList<Variants> = mutableListOf()
        val rankingProductsList: MutableList<RankingProducts> = mutableListOf()


        val subCatList = getSubCatList(category)

        for (cat in category) {
            cat.isParent = !subCatList.contains(cat.id)

            if (!cat.child_categories.isEmpty()) {
                cat.sub_categories = cat.child_categories.contentToString()
            }
            for (prod in cat.products) {
                prod.cat_value = cat.name
                productList.add(prod)
                for (variant in prod.variants) {
                    variant.prod_id = prod.id
                    variantsList.add(variant)
                }
            }
        }

        for (rank in ranking) {
            for (prod in rank.products) {
                prod.ranking_name = rank.ranking
                rankingProductsList.add(prod)
            }
        }

        insertCat(category)
        insertRanking(ranking)
        insertProducts(productList)
        insertVariants(variantsList)
        insertRankingProducts(rankingProductsList)
        updateProdPrice()
        listener.onRecordRetrieve(getCatList(category))
    }

    private fun getSubCatList(catList: List<Categories>): List<Int> {
        val subCatList: ArrayList<Int> = mutableListOf<Int>() as ArrayList<Int>

        for (cat in catList) {
            if (!cat.child_categories.isEmpty()) {
                subCatList.addAll(cat.child_categories)
            }
        }
        return subCatList
    }
   private fun getCatList(categoryList: List<Categories>?): ArrayList<ParentCategory> {
        val tempCatList: ArrayList<ParentCategory> = mutableListOf<ParentCategory>() as ArrayList<ParentCategory>
        if (categoryList != null) {
            for (cat in categoryList) {
                if (cat.isParent) {
                    val parentCat = ParentCategory()
                    parentCat.cat_name = cat.name
                    parentCat.sub_categories = cat.sub_categories
                    tempCatList.add(parentCat)
                }
            }
        }
        return tempCatList
    }

    private fun insertCat(catList: List<Categories>) {
        AsyncTask.execute {
            dataDao.insertCategories(catList)
        }
    }

    private fun insertRanking(rankingList: List<Ranking>) {
        AsyncTask.execute {
            dataDao.insertRanking(rankingList)
        }
    }

    private fun insertProducts(prodList: List<Products>) {
        AsyncTask.execute {
            dataDao.insertProducts(prodList)
        }
    }

    private fun insertVariants(variantsList: List<Variants>) {
        AsyncTask.execute {
            dataDao.insertVariants(variantsList)
        }
    }

    private fun insertRankingProducts(prodList: List<RankingProducts>) {
        AsyncTask.execute {
            dataDao.insertRankingProds(prodList)
        }
    }

    private fun updateProdPrice() {
        AsyncTask.execute {
            dataDao.updateProductPrice()
        }
    }
}