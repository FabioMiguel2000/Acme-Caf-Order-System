package com.feup.coffee_order_application.domain.repository

import com.feup.coffee_order_application.core.network.ApiInterface
import com.feup.coffee_order_application.core.network.ApiResponse
import com.feup.coffee_order_application.domain.model.CartProduct
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.domain.model.OrderRequest
import com.feup.coffee_order_application.domain.model.Product
import com.feup.coffee_order_application.domain.model.User
import com.feup.coffee_order_application.domain.model.Voucher
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import org.mockito.kotlin.*
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

/**
 * This class is used to test the OrderRepository class.
 * It uses mock objects to simulate the behavior of the methods in the OrderRepository class.
 */
class OrderRepositoryTest {
    // Mock object for the ApiInterface
    private val mockApiInterface: ApiInterface = mock()
    // Mock object for the User
    private val mockUser = User(
        1, "8888888888888888", 0.0, 0.0, "user@user.com", "antónio",
        "275275275"
    )

    /**
     * This test checks if the createOrder method in the OrderRepository class is working as expected.
     */
    @Test
    fun `createOrderTest`() {
        // Mock object for the OrderRequest
        val mockOrderRequest = OrderRequest("1", mutableListOf(), null, null)
        // Mock object for the Call
        val mockCall: Call<Void> = mock()
        // Simulate the behavior of the createOrder method
        whenever(mockApiInterface.createOrder(mockOrderRequest)).thenReturn(mockCall)
        doAnswer { invocation ->
            val callback = invocation.getArgument<Callback<Void>>(0)
            callback.onResponse(mockCall, Response.success(null))
            null
        }.whenever(mockCall).enqueue(any())

        // Create Repository
        val repository = OrderRepository(mockApiInterface)

        // Execution & Assertion
        repository.createOrder(mockOrderRequest) { success, code ->
            assertTrue(success)
            assertEquals(200, code)
        }
    }

    /**
     * This test checks if the getOrdersByClientId method in the OrderRepository class is working as expected.
     */
    @Test
    fun `getOrdersByClientIdTest`() {
        // Mock objects for the CartProduct, Date, and Voucher
        val mockCartProduct =
            CartProduct(Product("1", "Hot Coffee", 0.7, "coffee.jpg", "coffee"), 1)
        val mockDate = Date()
        val mockVoucher =
            Voucher("8888888888888889", "Discount", mockUser, false, mockDate.toString(), false)
        // Mock object for the Order
        val mockOrderList = listOf(
            Order(
                "1",
                mockUser,
                mutableListOf(mockCartProduct),
                mockVoucher,
                mockVoucher,
                Order.STATUS_PENDING,
                1.0,
                0.0,
                1.0,
                "2023-01-01"
            ),

            )
        // Mock object for the ApiResponse
        val mockSuccessApiResponse =
            ApiResponse(success = true, message = "Success", data = mockOrderList)
        // Mock object for the Call
        val mockCall: Call<ApiResponse<List<Order>>> = mock()
        // Simulate the behavior of the getClientOrders method
        whenever(mockApiInterface.getClientOrders("8888888888888888")).thenReturn(mockCall)
        doAnswer { invocation ->
            val callback = invocation.getArgument<Callback<ApiResponse<List<Order>>>>(0)
            callback.onResponse(mockCall, Response.success(mockSuccessApiResponse))
            null
        }.whenever(mockCall).enqueue(any())

        // Create Repository
        val repository = OrderRepository(mockApiInterface)

        // Execution & Assertion
        repository.getOrdersByClientId("8888888888888888") { orders ->
            assertEquals(mockOrderList, orders)
        }
    }
}