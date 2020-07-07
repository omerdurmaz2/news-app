package com.example.haber.Realm

import io.realm.RealmObject

open class haberBilgileri : RealmObject() {
    lateinit var haberLink: String

    override fun toString(): String {
        return "haberBilgileri(haberLink=$haberLink)"
    }

}