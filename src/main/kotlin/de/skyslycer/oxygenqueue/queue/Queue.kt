package de.skyslycer.oxygenqueue.queue

import java.util.*

open class Queue(private val timeout: Int) {

    private val players = LinkedList<Pair<UUID, Long>>()

    fun tick() {
        players.forEach {
            if (System.currentTimeMillis() - it.second >= timeout * 1000) {
                players.remove(it)
            }
        }
    }

    fun add(uuid: UUID) {
        players.add(uuid to System.currentTimeMillis())
    }

    fun remove(uuid: UUID) {
        players.remove(players.first { it.first == uuid })
    }

    fun isEmpty(): Boolean {
        return players.isEmpty()
    }

    fun size(): Int {
        return players.size
    }

    fun get(uuid: UUID): Optional<Pair<UUID, Long>> {
        return Optional.ofNullable(players.first { it.first == uuid })
    }

    fun get(index: Int): Optional<Pair<UUID, Long>> {
        return Optional.ofNullable(players[index])
    }

    fun getPosition(uuid: UUID): Int {
        return players.indexOfFirst { it.first == uuid }
    }

}