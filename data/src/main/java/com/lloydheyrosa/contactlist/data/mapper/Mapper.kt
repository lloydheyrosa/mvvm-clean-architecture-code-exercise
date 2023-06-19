package com.lloydheyrosa.contactlist.data.mapper

/**
 * Interface for mapping data models.
 *  T object to be mapped
 *  E Result of mapping
 */
interface Mapper<T, E> {

    /**
     * Map object from T to E.
     *
     * @param from object to be mapped.
     *
     * @return E result of mapping
     */
    fun mapFrom(from: T): E

    /**
     * Map List<T> to List<E>.
     *
     * @param from List to be mapped
     *
     * @return List<E> result of mapping
     */
    fun mapListFrom(from: List<T>): List<E> = from.map { mapFrom(it) }
}