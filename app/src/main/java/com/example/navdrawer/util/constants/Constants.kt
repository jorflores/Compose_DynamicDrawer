package com.example.navdrawer.util.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    //Data Store Name
    const val DATASTORE = "my_datastore"

    // Keys to store values in data store
     val TOKEN = stringPreferencesKey("TOKEN")
     val ISADMIN = booleanPreferencesKey("ISADMIN")


}