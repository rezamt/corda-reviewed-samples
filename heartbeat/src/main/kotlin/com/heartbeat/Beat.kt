package com.heartbeat

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * The family of schemas for InvoiceState.
 */
object BeatSchema

/**
 * An Beat schema.
 */
object BeatSchemaV1 : MappedSchema(
        schemaFamily = BeatSchema.javaClass,
        version = 1,
        mappedTypes = listOf(Beat::class.java)) {
    @Entity
    @Table(name = "beats")   // namespace: telco _ table: invoice
    class Beat(
            @Column(name = "BeatID")
            var ownerID: String,

            @Column(name = "Beat_Counts")
            var counts : Int,

            @Column(name = "linear_id")
            var linearId: UUID
    ) : PersistentState() {
        // Default constructor required by hibernate.
        constructor(): this("UNP--".plus(UUID.randomUUID().toString()), 0, UUID.randomUUID())
    }
}