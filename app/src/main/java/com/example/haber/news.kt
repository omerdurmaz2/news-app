package com.example.haber

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haber.Realm.haberBilgileri
import com.example.haber.adapter.NewsAdapter
import com.example.haber.models.TopHeadlinesModel
import com.example.haber.restApi.RestApi
import com.example.haber.restApi.RestApiClient
import io.realm.Realm
import io.realm.RealmResults
import retrofit2.Call
import retrofit2.Response

class news : AppCompatActivity() {

    lateinit var liste: ListView
    lateinit var tutulanListe: TopHeadlinesModel
    var ilkData: Boolean = false
    lateinit var adapter: NewsAdapter
    lateinit var id: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val bundle: Bundle? = intent.extras
        id = bundle?.get("sourceId").toString()

        tanimla()
        haberCek()
        haberAc()
        haberGuncelle()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish();
        }
        return super.onOptionsItemSelected(item)
    }

    fun tanimla() {
        liste = findViewById(R.id.listeHaber)

    }


    fun haberCek() {

        Log.i("sss", "Fonmsiyona Gelen id: " + id)

        RestApiClient.getClient().create(RestApi::class.java)
            .haberCagir(id, getString(R.string.apiKey))
            .enqueue(object : retrofit2.Callback<TopHeadlinesModel> {
                override fun onResponse(
                    call: Call<TopHeadlinesModel>?,
                    response: Response<TopHeadlinesModel>?
                ) {
                    if (response!!.isSuccessful) {

                        if (ilkData) {
                            if (tutulanListe.articles?.get(0)?.url != response.body().articles?.get(
                                    0
                                )?.url
                            ) {

                                val kalinanPosizyon: Int = liste.getFirstVisiblePosition()
                                adapter = NewsAdapter(response.body(), applicationContext)
                                liste.adapter = adapter
                                liste.invalidateViews()

                                liste.smoothScrollToPosition(kalinanPosizyon)



                                tutulanListe = response.body()
                                Toast.makeText(
                                    applicationContext,
                                    "Yeni Haberler Eklendi",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.i("sss", "yeni haber geldi")
                            } else
                                Log.i("sss", "yeni haber yok")

                        } else {
                            adapter = NewsAdapter(response.body(), applicationContext)
                            liste.adapter = adapter
                            tutulanListe = response.body()
                            ilkData = true
                        }

                    }
                }

                override fun onFailure(call: Call<TopHeadlinesModel>?, t: Throwable?) {
                    Log.i("sss", t?.localizedMessage.toString())
                }
            })
    }


    fun haberAc() {
        liste.setOnItemClickListener() { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            var intentHaber = Intent(android.content.Intent.ACTION_VIEW)
            intentHaber.data = Uri.parse(tutulanListe.articles?.get(position)?.url)
            startActivity(intentHaber)

        }
    }


    fun haberGuncelle() {
        val timer = object : CountDownTimer(Long.MAX_VALUE, 60000) {
            override fun onTick(millisUntilFinished: Long) {
                haberCek()
            }

            override fun onFinish() {
            }
        }
        timer.start()
    }


}