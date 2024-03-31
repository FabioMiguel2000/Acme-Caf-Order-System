package com.feup.coffee_order_application.services

import com.feup.coffee_order_application.models.CategoryItem
import java.util.LinkedList

class CategoriesHolder private constructor() {
    var categories = LinkedList<CategoryItem>()

    fun populateCategoryList(category: CategoryItem){
        categories.add(category)
    }
    companion object {
        @Volatile
        private var instance: CategoriesHolder? =  null

        fun getInstance() = instance ?: synchronized(this){
            instance ?: CategoriesHolder().also { instance = it }
        }
    }
}