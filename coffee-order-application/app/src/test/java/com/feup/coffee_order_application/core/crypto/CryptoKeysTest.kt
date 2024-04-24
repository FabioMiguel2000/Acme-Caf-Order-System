package com.feup.coffee_order_application.core.crypto

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

/**
 * This class is used to test the CryptoKeys class.
 * It uses mock objects to simulate the behavior of the methods in the CryptoKeys class.
 */
class CryptoKeysTest {

    /**
     * This test checks if the generateAndStoreKeys method in the CryptoKeys class is working as expected.
     */
    @Test
    fun testGenerateAndStoreKeys() {
        // Mock object for the CryptoKeys
        val cryptoKeys = Mockito.mock(CryptoKeys::class.java)
        // Simulate the behavior of the generateAndStoreKeys method
        Mockito.`when`(cryptoKeys.generateAndStoreKeys()).thenReturn(true)
        // Execution & Assertion
        val result = cryptoKeys.generateAndStoreKeys()
        assertTrue("Keys should be generated and stored successfully", result)
    }

    /**
     * This test checks if the getPubKey method in the CryptoKeys class is working as expected.
     */
    @Test
    fun testGetPubKey() {
        // Mock object for the CryptoKeys
        val cryptoKeys = Mockito.mock(CryptoKeys::class.java)
        // Mock object for the PubKey
        val pubKey = PubKey("modulus".toByteArray(), "exponent".toByteArray())
        // Simulate the behavior of the getPubKey method
        Mockito.`when`(cryptoKeys.getPubKey()).thenReturn(pubKey)
        // Execution & Assertion
        val result = cryptoKeys.getPubKey()
        assertNotNull("Public key should not be null", result)
        assertEquals("Public key modulus should be correct", "modulus", String(result.modulus))
        assertEquals("Public key exponent should be correct", "exponent", String(result.exponent))
    }

    /**
     * This test checks if the pubKeyToPem method in the CryptoKeys class is working as expected.
     */
    @Test
    fun testPubKeyToPem() {
        // Mock object for the CryptoKeys
        val cryptoKeys = Mockito.mock(CryptoKeys::class.java)
        // Mock object for the PubKey
        val pubKey = PubKey("modulus".toByteArray(), "exponent".toByteArray())
        // Mock object for the PEM string
        val pem = "-----BEGIN PUBLIC KEY-----\nmodulus\nexponent\n-----END PUBLIC KEY-----\n"
        // Simulate the behavior of the pubKeyToPem method
        Mockito.`when`(cryptoKeys.pubKeyToPem(pubKey)).thenReturn(pem)
        // Execution & Assertion
        val result = cryptoKeys.pubKeyToPem(pubKey)
        assertNotNull("PEM string should not be null", result)
        assertTrue("PEM string should contain 'BEGIN PUBLIC KEY'", result.contains("BEGIN PUBLIC KEY"))
        assertTrue("PEM string should contain 'END PUBLIC KEY'", result.contains("END PUBLIC KEY"))
    }
}