/**
 * This class is used to test the UserRepository class.
 * It uses mock objects to simulate the behavior of the methods in the UserRepository class.
 */
package com.feup.coffee_order_application.domain.repository

import com.feup.coffee_order_application.core.network.ApiInterface
import com.feup.coffee_order_application.core.network.ApiResponse
import com.feup.coffee_order_application.domain.model.User
import com.feup.coffee_order_application.domain.model.Voucher
import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*
import retrofit2.Call
import retrofit2.Response
import java.util.Date

class UserRepositoryTest {

    // Mock objects for the User, Date, Voucher, and ApiResponse
    private val mockUser = User(1, "8888888888888888", 0.0, 0.0, "user@user.com", "antónio",
        "275275275"
    )
    private val mockDate = Date()
    private val mockVoucherList = listOf(
        Voucher("8888888888888889", "Discount", mockUser, false, mockDate.toString(), false),
        Voucher("8888888888888890", "FreeCoffee", mockUser, false, mockDate.toString(), false),
        Voucher("8888888888888891", "Discount", mockUser, true, mockDate.toString(), false),
        // Add more Voucher objects if needed
    )
    private val mockSuccessApiResponse = ApiResponse(success = true, message = "Success", data = mockUser)

    // Mock object for the ApiInterface
    private val mockApiInterface: ApiInterface = mock()
    private val mockSuccessVApiResponse = ApiResponse(success = true, message = "Success", data = mockVoucherList)

    /**
     * This test checks if the getUserById method in the UserRepository class is working as expected.
     */
    @Test
    fun `testGetUserId Success`() {

        // Mock object for the Call
        val mockCall: Call<ApiResponse<User>> = mock()
        // Simulate the behavior of the getUserById method
        whenever(mockApiInterface.getUserById(anyString())).thenReturn(mockCall)
        whenever(mockCall.enqueue(any())).then { invocation ->
            val callback = invocation.getArgument<retrofit2.Callback<ApiResponse<User>>>(0)
            callback.onResponse(mockCall, Response.success(mockSuccessApiResponse))
        }

        // Create Repository
        val repository = UserRepository(mockApiInterface)

        // Execution & Assertion
        repository.getUserById("8888888888888888") { user ->
            assertEquals(mockUser, user)
        }

    }

    /**
     * This test checks if the getUserVouchers method in the UserRepository class is working as expected.
     */
    @Test
    fun getUserVouchersTest(){
        // Mock object for the Call
        val mockCall: Call<ApiResponse<List<Voucher>>> = mock()
        // Simulate the behavior of the getUserVouchers method
        whenever(mockApiInterface.getUserVouchers(anyString())).thenReturn(mockCall)
        doAnswer { invocation ->
            val callback = invocation.getArgument<retrofit2.Callback<ApiResponse<List<Voucher>>>>(0)
            callback.onResponse(mockCall, Response.success(mockSuccessVApiResponse))
            null
        }.whenever(mockCall).enqueue(any())

        // Create Repository
        val repository = UserRepository(mockApiInterface)

        // Execution & Assertion
        repository.getUserVouchers("8888888888888888") { vouchers ->
            assertEquals(mockVoucherList, vouchers)
        }
    }
}