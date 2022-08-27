package com.example.base.view


/**
 * Base data item class that is used in [BasePagedListAdapter] for the Diff callback id comparison
 */
interface BaseEntity {
    fun entityId(): String
}