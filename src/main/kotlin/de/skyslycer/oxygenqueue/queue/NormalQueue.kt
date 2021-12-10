package de.skyslycer.oxygenqueue.queue

import de.skyslycer.oxygenqueue.configuration.Config

class NormalQueue(config: Config) : Queue(config.normal.timeout)