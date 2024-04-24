
package com.feup.coffee_order_application.domain.repository

import com.feup.coffee_order_application.core.network.ApiInterface
import com.feup.coffee_order_application.core.network.ApiResponse
import com.feup.coffee_order_application.domain.model.Category
import com.feup.coffee_order_application.domain.model.Product
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call

import org.mockito.kotlin.*
import retrofit2.Response

/**
 * This class is used to test the ProductRepository class.
 * It uses mock objects to simulate the behavior of the methods in the ProductRepository class.
 */
class ProductRepositoryTest {
    // Mock object for the ApiInterface
    private val mockApiInterface: ApiInterface = mock()

    /**
     * This test checks if the fetchProductsByCategory method in the ProductRepository class is working as expected.
     */
    @Test
    fun `fetchProductsByCategoryTest`() {
        // Mock objects for the Product and ApiResponse
        val mockProductList = listOf(
            Product("99999999", "Hot Coffee", 0.7, "coffee.jpg", "coffee"),
            Product("99999997", "Cold Coffee", 0.75, "coffee.jpg", "coffee"),
            Product("99999998", "Hot Tea", 1.0, "tea.jpg", "tea"),
            // Add more Product objects if needed
        )
        val mockSuccessApiResponse = ApiResponse(success = true, message = "Success", data = mockProductList)
        // Mock object for the Call
        val mockCall: Call<ApiResponse<List<Product>>> = mock()
        // Simulate the behavior of the getProductsByCategory method
        whenever(mockApiInterface.getProductsByCategory("coffee")).thenReturn(mockCall)
        doAnswer { invocation ->
            val callback = invocation.getArgument<retrofit2.Callback<ApiResponse<List<Product>>>>(0)
            callback.onResponse(mockCall, Response.success(mockSuccessApiResponse))
            null
        }.whenever(mockCall).enqueue(any())

        // Create Repository
        val repository = ProductRepository(mockApiInterface)

        // Execution & Assertion
        repository.fetchProductsByCategory("coffee") { products ->
            assertEquals(mockProductList, products)
        }
    }

    /**
     * This test checks if the fetchProductCategories method in the ProductRepository class is working as expected.
     */
    @Test
    fun `fetchProductCategoriesTest`() {
        // Mock objects for the Category and ApiResponse
        val mockCategoryList = listOf(
            Category("1", "coffee", "coffee", "coffee.jpg", 1, 120 ),
            Category("2", "tea", "tea", "tea.jpg", 2, 122 ),
            // Add more Category objects if needed
        )
        val mockSuccessApiResponse =
            ApiResponse(success = true, message = "Success", data = mockCategoryList)
        // Mock object for the Call
        val mockCall: Call<ApiResponse<List<Category>>> = mock()
        // Simulate the behavior of the getProductCategories method
        whenever(mockApiInterface.getProductCategories()).thenReturn(mockCall)
        doAnswer { invocation ->
            val callback =
                invocation.getArgument<retrofit2.Callback<ApiResponse<List<Category>>>>(0)
            callback.onResponse(mockCall, Response.success(mockSuccessApiResponse))
            null
        }.whenever(mockCall).enqueue(any())

        // Create Repository
        val repository = ProductRepository(mockApiInterface)

        // Execution & Assertion
        repository.fetchProductCategories { categories ->
            assertEquals(mockCategoryList, categories)
        }
    }
}