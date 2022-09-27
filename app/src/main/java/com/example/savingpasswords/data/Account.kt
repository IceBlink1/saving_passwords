package com.example.savingpasswords.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Account class, main project model
 *
 * @property uid unique identifier
 * @property domain domain name of a site or an app
 * @property login login of the account
 * @property password password of the account
 */
@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "domain") val domain: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String
)