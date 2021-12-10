package de.skyslycer.oxygenqueue.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val normal: Normal,
    val priority: Priority
)