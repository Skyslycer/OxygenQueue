package de.skyslycer.oxygenqueue.listeners

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import de.skyslycer.oxygenqueue.Queues
import de.skyslycer.oxygenqueue.configuration.Config
import de.skyslycer.oxygenqueue.queue.Queue
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class PlayerLoginListener(private val server: ProxyServer, private val config: Config, private val queues: Queues) {

    @Subscribe(order = PostOrder.FIRST)
    fun onPlayerLogin(event: LoginEvent) {
        if (event.player.hasPermission("oxygenqueue.bypass")) {
            return
        }

        if (event.player.hasPermission("oxygenqueue.priority")) {
            checkAndAdd(config.priority.timeout, event.player, queues.priorityQueue, MiniMessage.get().parse(config.priority.queueMessage))
        } else {
            checkAndAdd(config.normal.timeout, event.player, queues.normalQueue, MiniMessage.get().parse(config.normal.queueMessage))
        }
    }

    private fun checkAndAdd(maxPlayers: Int, player: Player, queue: Queue, message: Component) {
        if (server.playerCount < maxPlayers && queue.isEmpty()) {
            return
        }

        if (queue.get(player.uniqueId).isEmpty) {
            queue.add(player.uniqueId)
        }

        player.disconnect(message.replaceText {
            it.matchLiteral("%position%")
            it.replacement(queue.getPosition(player.uniqueId).toString())
        }.replaceText {
            it.matchLiteral("%size%")
            it.replacement(queue.size().toString())
        })
    }

}