package com.example.savingpasswords.data

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Интерфейс для последующей генерации объекта для доступа к БД
 *
 */
@Dao
interface AccountDao {

    /**
     * Метод запроса всех существующих аккаунтов
     *
     * @return Все аккаунты из бд
     */
    @Query("SELECT * FROM account")
    fun getAll(): Flowable<List<Account>>

    /**
     * Метод для поиска аккаунтов по доменному имени
     *
     * @param domain запрос, поиск по подстроке
     * @return все аккаунты, удовлетворяющие запросу
     */
    @Query("SELECT * FROM account where domain like '%' || :domain || '%'")
    fun searchByName(domain: String): Flowable<List<Account>>

    /**
     * Метод для удаления аккаунта
     *
     * @param account аккаунт, который нужно удалить
     * @return
     */
    @Delete
    fun delete(account: Account): Completable

    /**
     * Метод для обновления аккаунта
     *
     * @param account аккаунт, который нужно обновить
     * @return
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(account: Account): Completable

    /**
     * Метод для вставки аккаунта в БД
     *
     * @param accounts переменное число аккаунтов для вставки
     * @return
     */
    @Insert
    fun insert(vararg accounts: Account): Completable
}