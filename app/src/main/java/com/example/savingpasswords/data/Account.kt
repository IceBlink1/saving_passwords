package com.example.savingpasswords.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Класс аккаунта, основная модель приложения
 *
 * @property uid идентификационный ключ
 * @property domain доменное имя аккаунта
 * @property login логин аккаунта
 * @property password пароль аккаунта
 */
@Entity
data class Account(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "domain") val domain: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String
)