package com.example.haber.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.haber.R
import com.example.haber.Realm.haberBilgileri
import com.example.haber.models.TopHeadlinesModel
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception


class NewsAdapter(val liste: TopHeadlinesModel, val context: Context) : BaseAdapter() {

    lateinit var realm: Realm
    lateinit var kitaplikListe: RealmResults<haberBilgileri>
    var sonuc: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {


        realmTanimla()
        kitaplikGetir()
        val view = LayoutInflater.from(context).inflate(R.layout.news_layout, p2, false)

        val txtbaslik = view.findViewById<TextView>(R.id.txtHaberBaslik)
        val txtsaat = view.findViewById<TextView>(R.id.txtSaat)
        val imgResim = view.findViewById<ImageView>(R.id.imgHaberResim)
        val btnKitaplik = view.findViewById<TextView>(R.id.btnKitaplik)

        try {


            var haberBaslik = liste.articles!!.get(p0)!!.title
            var haberUrl = liste.articles!!.get(p0)!!.url.toString()


            txtbaslik.setText(haberBaslik)

            val saat = liste.articles!!.get(p0)!!.publishedAt.toString().substring(11, 19)
            txtsaat.setText(saat)

            Picasso.get().load(liste.articles!!.get(p0)!!.urlToImage).into(imgResim)

            if (kitaplikKontrol(haberUrl)) {
                btnKitaplik.setText("Okuma Listemden Çıkar")
            } else {
                btnKitaplik.setText("Okuma Listeme Ekle")
            }

            btnKitaplik.setOnClickListener {
                kitaplikGetir()

                if (kitaplikKontrol(haberUrl)) {
                    kitapliktanCikar(haberUrl)

                    btnKitaplik.setText("Okuma Listeme Ekle")
                } else {

                    kitapligaEkle(haberUrl)

                    if (sonuc)
                        btnKitaplik.setText("Okuma Listemden Çıkar")
                    else
                        Toast.makeText(context, "Hata!", Toast.LENGTH_SHORT).show()
                }

            }
        } catch (e: Exception) {
            Log.i("sss", "Haberler Listelenirken hata:  " + e.localizedMessage)
        }

        return view
    }

    fun realmTanimla() {
        try {
            realm = Realm.getDefaultInstance()

        } catch (e: Exception) {
            Log.i("sss", "Realm Tanımlarken Hata:  " + e.localizedMessage)
        }
    }

    fun kitaplikGetir() {
        try {
            kitaplikListe = realm.where(haberBilgileri::class.java).findAll()
            Log.i("sss", "Kitaplık: " + kitaplikListe[0])
        } catch (e: Exception) {
            Log.i("sss", "Kitaplık getirilirken hata:  " + e.localizedMessage)
        }

    }

    fun kitaplikKontrol(link: String): Boolean {

        try {
            for (item in kitaplikListe) {
                if (item.haberLink == link) {
                    return true
                }
            }
        } catch (e: Exception) {
            Log.i("sss", "Kitaplık kontrol edilirken hata:  " + e.localizedMessage)
        }

        return false
    }

    fun kitapligaEkle(haberLink: String) {
        try {
            realm.executeTransactionAsync({ realm ->
                val kitap = realm.createObject(haberBilgileri::class.java)
                kitap.haberLink = haberLink
            }, {
                sonuc = true
            }
            ) {
                sonuc = false
            }
        } catch (e: Exception) {
            Log.i("sss", "Kitaplığa haber eklerken hata " + e.localizedMessage)
        }

    }

    fun kitapliktanCikar(haberLink: String) {
        try {

        } catch (e: Exception) {
            Log.i("sss", "Kitaplıktan haber silerken hata " + e.localizedMessage)
        }
    }


    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return liste.articles!!.count()
    }


}