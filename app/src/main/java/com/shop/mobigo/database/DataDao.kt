package com.shop.mobigo.database

import android.arch.persistence.room.*
import com.shop.mobigo.models.*

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categoriesList: List<Categories>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRanking(rankingList: List<Ranking>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(categoriesList: List<Products>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVariants(categoriesList: List<Variants>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRankingProds(prodList: List<RankingProducts>)

    @Query("SELECT cat_name, sub_categories from Categories WHERE isParent = 1")
    fun getParentCatList(): List<ParentCategory>

    @Query("SELECT cat_name , sub_categories from Categories WHERE id IN(:subCatIds)")
    fun getSubCatList(subCatIds: List<Int>): List<ParentCategory>

    @Query("SELECT ranking from Ranking")
    fun getRankingList(): List<String>

    @Query("SELECT prod_name, date_added, product_price from Products WHERE cat_value LIKE :selectedCat")
    fun getProductsByCat(selectedCat: String): List<ProductValues>

    @Query("SELECT prod_name ,date_added, product_price from Products WHERE cat_value LIKE :selectedCat AND id IN(SELECT prod_id from RankingProducts WHERE ranking_name LIKE :selectedRanking)")
    fun getProductsByRanking(selectedCat: String, selectedRanking: String): List<ProductValues>

    @Query("SELECT * from Variants WHERE prod_id = (SELECT id from Products WHERE prod_name LIKE :prod_name)")
    fun getVariants(prod_name: String): List<Variants>

    @Query("UPDATE Products set product_price = (SELECT MIN(price) from Variants where Variants.prod_id == Products.id)")
    fun updateProductPrice()
}