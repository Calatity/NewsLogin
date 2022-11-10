package com.lin.newslogin.util

import com.alibaba.fastjson.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest

object JsonTools {
    fun getRequestJsonString(httpServletRequest: HttpServletRequest): String {
        var inputStream: InputStream? = null
        var reader: BufferedReader? = null
        val stringBuilder = StringBuilder()
        try {
            inputStream = httpServletRequest.inputStream
            var stringMessage: String? = ""
            reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            while (reader.readLine().also { stringMessage = it } != null) {
                stringBuilder.append(stringMessage)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            println(e.message)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return stringBuilder.toString()
    }

    fun doJson(httpServletRequest: HttpServletRequest): JSONObject? {
        val contentType = httpServletRequest.getHeader("content-type")
        if (contentType != null && contentType.startsWith("application/json")) {
            val result = getRequestJsonString(httpServletRequest)
            return JSONObject.parseObject(result)
        }
        return null
    }
}