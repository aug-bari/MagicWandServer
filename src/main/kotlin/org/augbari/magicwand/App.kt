package org.augbari.magicwand

import kotlin.system.exitProcess


fun main(args: Array<String>) {
    var running = true

    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutdown!")
        running = false
        Mqtt.disconnect()
    })

    Mqtt.connect("tcp://broker.shiftr.io", "try", "try")
    Mqtt.subscribe(MqttReceiver(), "/org/augbari/keysim")

    // loop
    while (running) {
        Thread.sleep(42)
    }

    exitProcess(0)
}