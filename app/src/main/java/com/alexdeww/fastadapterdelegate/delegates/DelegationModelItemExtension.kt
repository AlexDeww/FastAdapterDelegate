package com.alexdeww.fastadapterdelegate.delegates

import com.mikepenz.fastadapter.GenericItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem

/**
 * Получить из элемента [GenericItem] -> элемент [GenericDelegationModelItem]
 *
 * @param M Тип модели элемента.
 *
 * @return [GenericDelegationModelItem] если удалось преобразовать, иначе null
 */
@Suppress("UNCHECKED_CAST")
fun <M> GenericItem.asModelItem(): GenericDelegationModelItem<M>? =
    (this as? GenericDelegationModelItem<M>)

/**
 * Получить из элемента [GenericItem] -> элемент [I]
 *
 * @param I Тип элемента.
 *
 * @return [I] если удалось преобразовать, иначе null
 */
@Suppress("UNCHECKED_CAST")
fun <I : GenericDelegationModelItem<*>> GenericItem.asTypedModelItem(): I? = (this as? I)

/**
 * Получить из элемента [GenericItem] -> модель [M]
 *
 * @param M Тип модели.
 *
 * @return [M] если удалось преобразовать, иначе null
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified M> GenericItem.asModel(): M? = (this as? GenericDelegationModelItem<*>)?.let {
    if (it.model is M) it.model as M else null
}

fun GenericItem.asAnyModel(): Any? = asModel<Any>()

/**
 * Проверка является ли элемент [GenericItem] элементом с моделью типа [M]
 *
 * @param M Тип модели.
 *
 * @return true является, иначе false
 */
inline fun <reified M> GenericItem.isModelItem(): Boolean =
    (this is GenericDelegationModelItem<*> && this.model is M)
