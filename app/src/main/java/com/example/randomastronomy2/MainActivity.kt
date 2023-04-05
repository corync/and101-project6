package com.example.randomastronomy2

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject





class MainActivity : AppCompatActivity() {
    private lateinit var astronomyImageList: MutableList<String>
    private lateinit var astronomyDateList: MutableList<String>
    private lateinit var astronomyTitleList: MutableList<String>
    private lateinit var rvAstronomy: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvAstronomy = findViewById(R.id.astronomy_list)
        astronomyImageList = mutableListOf()
        astronomyDateList = mutableListOf()
        astronomyTitleList = mutableListOf()

        getAstronomyData()

    }

    private fun getAstronomyData() {
        val client = AsyncHttpClient()

        val params = RequestParams()
        params["api_key"] = "fVv4g2w2qdrudjNjJbnbwgXsSrbaTjAxocWMhLbc"
        params["hd"] = "False"
        params["count"] = "20"

        client["https://api.nasa.gov/planetary/apod?", params, object : JsonHttpResponseHandler() {
            @SuppressLint("ResourceAsColor")
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Astronomy", "response successful $json")

                var jsonArray = json.jsonArray

                for (i in 0 until json.jsonArray.length()) {
                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    val url = jsonObject.getString("url")
                    val date = jsonObject.getString("date")
                    val title  = jsonObject.getString("title")
                    astronomyImageList.add(url)
                    astronomyDateList.add(date)
                    astronomyTitleList.add(title)
                }

                // Log.d("Astronomy", "$astronomyImageList;")

                val adapter = AstronomyAdapter(astronomyImageList, astronomyDateList, astronomyTitleList)
                rvAstronomy.adapter = adapter
                rvAstronomy.layoutManager = LinearLayoutManager(this@MainActivity)

                val mDividerItemDecoration = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
                mDividerItemDecoration.setDrawable(ColorDrawable(R.color.black))
                rvAstronomy.addItemDecoration(mDividerItemDecoration)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Astronomy Error", errorResponse)
            }
        }]
    }
}