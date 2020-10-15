package com.cbellmont.sources

import android.util.Log
import com.cbellmont.datamodel.EventFactory
import com.cbellmont.datamodel.User
import com.cbellmont.workshopui.MainActivityViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class GetAllUsers {
    companion object {
        private const val URL = "https://randomuser.me/api/?results=10"

        fun send(viewModel: MainActivityViewModel) {
            val client = OkHttpClient()
            val request = Request.Builder().url(URL).build()
            val call = client.newCall(request)
            viewModel.downloadStarted()
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    Log.e(GetAllUsers::class.simpleName, call.toString())
                    viewModel.downloadCancelled()
                }

                override fun onResponse(call: Call, response: Response) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val bodyInString = response.body?.string()
                        bodyInString?.let { it ->
                            Log.w(GetAllUsers::class.simpleName, it)
                            try {
                                val jsonObject = JSONObject(it)

                                val results = jsonObject.optJSONArray("results")
                                results?.let {
                                    Log.d(GetAllUsers::class.simpleName, results.toString())
                                    val gson = Gson()
                                    val itemType = object : TypeToken<List<User>>() {}.type
                                    val userList = gson.fromJson<List<User>>(results.toString(), itemType)
                                    userList.forEach{
                                        it.events = EventFactory.getRandom()
                                        Log.d(GetAllUsers::class.simpleName,  it.toString())

                                    }
                                    viewModel.downloadFinished(userList)
                                }
                            } catch (e : Exception) {
                                Log.e(GetAllUsers::class.simpleName, "La página web no ha respondido bien. Reintentamos la descarga")
                                e.printStackTrace()
                                viewModel.downloadData()
                            }
                        }
                    }
                }
            })
        }
    }
}