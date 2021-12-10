package de.skyslycer.oxygenqueue.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Priority(val maxPlayers: Int, val queueMessage: String, val timeout: Int)