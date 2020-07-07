package com.example.haber

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haber.adapter.SourcesAdapter
import com.example.haber.models.SourcesItem
import com.example.haber.models.SourcesModel
import com.example.haber.restApi.RestApi
import com.example.haber.restApi.RestApiClient
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var liste: ListView
    lateinit var tutulanListe: SourcesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        tanimla()
        kaynakCek()
        kaynakSec()


    }

    fun kaynakCek() {

        RestApiClient.getClient().create(RestApi::class.java)
            .kaynaklarCagir(getString(R.string.apiKey))
            .enqueue(object : retrofit2.Callback<SourcesModel> {
                override fun onResponse(
                    call: Call<SourcesModel>?,
                    response: Response<SourcesModel>?
                ) {
                    if (response!!.isSuccessful) {

                        val adapter: SourcesAdapter =
                            SourcesAdapter(response.body(), applicationContext)
                        liste.adapter = adapter
                        tutulanListe = response.body()
                        Log.i("sss", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<SourcesModel>?, t: Throwable?) {
                    Log.i("sss", t!!.localizedMessage.toString())
                }
            })
    }

    fun tanimla() {
        liste = findViewById<ListView>(R.id.liste)
    }

    fun kaynakSec() {
        liste.setOnItemClickListener() { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            intent = Intent(this, news::class.java)
            intent.putExtra("sourceId", tutulanListe.sources?.get(position)?.id)
            startActivity(intent)
        }
    }
}