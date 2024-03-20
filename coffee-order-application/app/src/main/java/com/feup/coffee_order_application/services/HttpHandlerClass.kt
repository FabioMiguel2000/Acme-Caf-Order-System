package com.feup.coffee_order_application.services
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
//mport kotlinx.seria

class HttpHandlerClass private  constructor(baseUrl : String ){
    var _baseUrl: String = baseUrl
        get(){
            return field
        }
    fun testApiConnection(address: String){
        var url: URL? = null
        var urlConnection: HttpURLConnection? = null

        try {
            url = URL(address)
            urlConnection = (url.openConnection() as HttpURLConnection).apply {
                doInput = true
                setRequestProperty("Content-Type", "application/json")
                useCaches = false
                requestMethod = "GET"
                connectTimeout = 5000

                if(responseCode == 200){
                    val resonse = readStream(inputStream)
                    Log.e("response-message", "OK")
                } else {
                    Log.e("response-message", "NOK")
                }
            }
        } catch (e: Exception){
            Log.e("response-message", e.toString())
            throw e
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect()
        }
    }

    fun login(context: Context, baseUrl: String, username: String, password: String): Boolean{
        _baseUrl = baseUrl + "api/auth/login"
        var url = URL(_baseUrl)

        val jsonObj =  JSONObject();
        jsonObj.put("email", username)
        jsonObj.put("password", password)
        val jsonString = jsonObj.toString()

        var urlConnection: HttpURLConnection ? = null
        try {
            urlConnection = (url.openConnection() as HttpURLConnection).apply {
                doInput = true
                doOutput = true
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                useCaches = false
                connectTimeout = 5000


                with(outputStream) {
                    write(jsonString.toByteArray())
                    flush()
                    close()
                }

                if(responseCode == 200) {
                    val response = readStream(inputStream)
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                    return true
                } else{
                    Log.e("message", "NOK")
                    val response = readStream(inputStream)
                    return false
                }
            }
        } catch (e: Exception){
            //Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
            throw e
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect()
        }
    }

    fun register(context: Context, baseUrl: String, name: String, email: String, password: String): Boolean{
        _baseUrl = baseUrl + "api/auth/login"
        var url = URL(_baseUrl)

        val jsonObj =  JSONObject();
        jsonObj.put("email", username)
        jsonObj.put("password", password)
        val jsonString = jsonObj.toString()

        var urlConnection: HttpURLConnection ? = null
        try {
            urlConnection = (url.openConnection() as HttpURLConnection).apply {
                doInput = true
                doOutput = true
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                useCaches = false
                connectTimeout = 5000


                with(outputStream) {
                    write(jsonString.toByteArray())
                    flush()
                    close()
                }

                if(responseCode == 200) {
                    val response = readStream(inputStream)
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                    return true
                } else{
                    Log.e("message", "NOK")
                    val response = readStream(inputStream)
                    return false
                }
            }
        } catch (e: Exception){
            //Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
            throw e
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect()
        }
    }


    fun readStream(input: InputStream): String {
        var reader: BufferedReader? = null
        var line: String?
        var response = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(input))
            while (reader.readLine().also { line = it } != null)
                response.append(line)

        }
        catch (e: IOException){
            response.clear()
            response.append("readStream: ${e.message}")
        }
        reader?.close()
        return response.toString()
    }
    companion object {
        @Volatile private var instance: HttpHandlerClass? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance?:HttpHandlerClass("http://192.168.1.97:3000/").also {
                instance = it
            }
        }
    }
}
