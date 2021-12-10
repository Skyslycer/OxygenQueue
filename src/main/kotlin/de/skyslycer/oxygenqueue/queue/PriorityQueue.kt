package de.skyslycer.oxygenqueue.queue

import de.skyslycer.oxygenqueue.configuration.Config

class PriorityQueue(config: Config) : Queue(config.priority.timeout)