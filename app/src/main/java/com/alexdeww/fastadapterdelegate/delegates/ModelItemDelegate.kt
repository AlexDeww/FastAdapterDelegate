package com.alexdeww.fastadapterdelegate.delegates

import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem

/**
 * @param M Тип данных модели.
 */

interface ModelItemDelegate<M> {

    /**
     * Вызывается, чтобы определить, является ли этот Delegate ответственным за данную модель.
     *
     * @param model Модель
     * @return true, если модель соответсвует
     */
    fun isForViewType(model: M): Boolean

    /**
     * Вызывается, чтобы создать соответсвующий элемент для модели.
     *
     * @param model Модель
     * @param delegates Список всех делегатов(включая себя), если вдруг у элемента есть субэлементы,
     *
     * @return Элемент для этой модели.
     */
    fun intercept(
        model: M,
        delegates: List<ModelItemDelegate<M>>
    ): GenericDelegationModelItem<M>

}
