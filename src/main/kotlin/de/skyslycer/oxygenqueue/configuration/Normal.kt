package de.skyslycer.oxygenqueue.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Normal(val maxPlayers: Int, val queueMessage: String, val timeout: Int)