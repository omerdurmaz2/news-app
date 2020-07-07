package com.example.haber.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.haber.R
import com.example.haber.models.SourcesModel

class SourcesAdapter(val liste: SourcesModel, val context: Context) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.resources_layout, p2, false)

        val txtbaslik = view.findViewById<TextView>(R.id.txtTitle)
        val txticerik = view.findViewById<TextView>(R.id.txtDescp)

        txtbaslik.setText(liste.sources!!.get(p0)!!.name)
        txticerik.setText(liste.sources!!.get(p0)!!.description)
        return view
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return liste.sources!!.count()
    }
}