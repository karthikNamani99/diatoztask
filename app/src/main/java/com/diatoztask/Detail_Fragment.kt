package com.diatoztask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Float.parseFloat

class Detail_Fragment : Fragment() {

    var cityContent: CityContent? = null
    var textView: TextView? = null
    var textViewCityName: TextView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_detail, container, false)
        textView = v.findViewById(R.id.cityTemp)
        textViewCityName = v.findViewById(R.id.cityName)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cityContent = arguments?.getParcelable("cityContent")

        sendGet(cityContent)

    }

    fun sendGet(cityContentObj: CityContent?) {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url = cityContentObj?.apiUrl

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    Log.v("TAG", response);
                    var jsonObj = JSONObject(response);
                    var coordObj = jsonObj.getJSONObject("coord")
                    var mainObj = jsonObj.getJSONObject("main")
                    var nameObj = jsonObj.getString("name")

                    Log.v("TAG", mainObj.getString("temp"));

                    textView!!.text = "%.2f".format(((parseFloat(mainObj.getString("temp")) - 273.15))) + " \u2103"
                    textViewCityName!!.text = nameObj


                },
                Response.ErrorListener {
                    //textView.text = "That didn't work!"
                })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}