package com.alexdeww.fastadapterdelegate.delegates

import com.mikepenz.fastadapter.GenericItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegateModelItem

/**
 * Получить из элемента [GenericItem] -> элемент [GenericDelegateModelItem]
 *
 * @param M Тип модели элемента.
 *
 * @return [GenericDelegateModelItem] если удалось преобразовать, иначе null
 */
@Suppress("UNCHECKED_CAST")
fun <M> GenericItem.asModelItem(): GenericDelegateModelItem<M>? =
    (this as? GenericDelegateModelItem<M>)

/**
 * Получить из элемента [GenericItem] -> элемент [I]
 *
 * @param I Тип элемента.
 *
 * @return [I] если удалось преобразовать, иначе null
 */
@Suppress("UNCHECKED_CAST")
fun <I : GenericDelegateModelItem<*>> GenericItem.asTypedModelItem(): I? = (this as? I)

/**
 * Получить из элемента [GenericItem] -> модель [M]
 *
 * @param M Тип модели.
 *
 * @return [M] если удалось преобразовать, иначе null
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified M> GenericItem.asModel(): M? = (this as? GenericDelegateModelItem<*>)?.let {
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
    (this is GenericDelegateModelItem<*> && this.model is M)
