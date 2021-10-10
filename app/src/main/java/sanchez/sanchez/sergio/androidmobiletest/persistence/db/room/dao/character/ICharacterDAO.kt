package sanchez.sanchez.sergio.androidmobiletest.persistence.db.room.dao.character

import androidx.room.Query
import sanchez.sanchez.sergio.androidmobiletest.domain.models.Character
import sanchez.sanchez.sergio.androidmobiletest.persistence.db.room.dao.core.ISupportDAO
import sanchez.sanchez.sergio.androidmobiletest.persistence.db.room.entity.CharacterEntity

/**
 * Characters DAO
 */
interface ICharacterDAO: ISupportDAO<CharacterEntity> {

    @Query("SELECT * FROM characters")
    suspend fun findAll(): List<Character>

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun count(): Long

    @Query("SELECT * FROM characters ORDER BY name ASC")
    suspend fun findAllOrderByNameAsc(): List<CharacterEntity>

    @Query("DELETE FROM characters")
    fun deleteAll()
}