package com.heartbeat

import net.corda.core.contracts.*
import net.corda.core.flows.FlowLogicRefFactory
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.time.Instant

/**
 * Every Heartbeat state has a scheduled activity to start a flow to consume itself and produce a
 * new Heartbeat state on the ledger after five seconds.
 *
 * @property me The creator of the Heartbeat state.
 * @property nextActivityTime When the scheduled activity should be kicked off.
 */
@BelongsToContract(HeartContract::class)
data class HeartState(
        private var ownerId: String,
        private var count: Int,
        private val me: Party,
        private val nextActivityTime: Instant = Instant.now().plusSeconds(10),
        override val linearId: UniqueIdentifier = UniqueIdentifier()
) : SchedulableState, LinearState, QueryableState {

    override val participants get() = listOf(me)

    // Defines the scheduled activity to be conducted by the SchedulableState.
    override fun nextScheduledActivity(thisStateRef: StateRef, flowLogicRefFactory: FlowLogicRefFactory): ScheduledActivity? {
        // A heartbeat will be emitted every second.
        // We get the time when the scheduled activity will occur in the constructor rather than in this method. This is
        // because calling Instant.now() in nextScheduledActivity returns the time at which the function is called, rather
        // than the time at which the state was created.
        return ScheduledActivity(flowLogicRefFactory.create(HeartbeatFlow::class.java, thisStateRef), nextActivityTime)
    }



    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is BeatSchemaV1 -> BeatSchemaV1.Beat(
                    ownerId,
                    count,
                    this.linearId.id
            )
            else -> throw IllegalArgumentException("Unrecognised schema $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(BeatSchemaV1)

    fun beat() = copy(count = count.plus(1), nextActivityTime = Instant.now().plusSeconds(10))

    fun me() = ownerId
}