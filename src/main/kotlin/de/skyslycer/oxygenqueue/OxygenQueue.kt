package de.skyslycer.oxygenqueue

import com.akuleshov7.ktoml.file.TomlFileReader
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import de.skyslycer.oxygenqueue.configuration.Config
import de.skyslycer.oxygenqueue.listeners.PlayerLoginListener
import de.skyslycer.oxygenqueue.queue.NormalQueue
import de.skyslycer.oxygenqueue.queue.PriorityQueue
import kotlinx.serialization.serializer
import org.slf4j.Logger
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.Duration
import javax.inject.Inject
import kotlin.io.path.notExists


@Plugin(
    id = "oxygenqueue",
    name = "OxygenQueue",
    version = "1.0.0",
    url = "https://oxygenmc.net",
    description = "The queue system for OxygenMC",
    authors = ["Skyslycer"]
)
class OxygenQueue @Inject constructor(private val server: ProxyServer, @DataDirectory dataDirectory: Path) {

    private val configFile: Path = dataDirectory.resolve("config.toml")
    private var config: Config

    init {
        if (configFile.notExists()) {
            Files.copy(this.javaClass.getResourceAsStream("config.toml")!!, configFile, StandardCopyOption.REPLACE_EXISTING)
        }

        config = TomlFileReader.decodeFromFile(serializer(), configFile.toString())
    }

    private val priorityQueue = PriorityQueue(config)
    private val normalQueue = NormalQueue(config)

    @Subscribe
    fun onInitialize() {
        server.scheduler.buildTask(this) {
            priorityQueue.tick()
            normalQueue.tick()
        }.repeat(Duration.ofSeconds(1))

        server.eventManager.register(this, PlayerLoginListener(server, config, Queues(priorityQueue, normalQueue)))
    }

}