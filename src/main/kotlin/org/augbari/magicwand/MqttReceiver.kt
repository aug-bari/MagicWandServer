package org.augbari.magicwand

import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.awt.Robot
import java.awt.event.KeyEvent
import java.util.*

class MqttReceiver : IMqttMessageListener {

    private val r = Robot()
    private val egg = Random() // because why not

    init {
        r.autoDelay = 300
        r.isAutoWaitForIdle = true
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        println("Message arrived from $topic: $message")

        when (message.toString()) {
            "up" -> {
                println("Received up command.")
                r.keyPress(KeyEvent.VK_UP)
                r.keyRelease(KeyEvent.VK_UP)
            }
            "down" -> {
                println("Received down command.")
                r.keyPress(KeyEvent.VK_DOWN)
                r.keyRelease(KeyEvent.VK_DOWN)
            }
            "left" -> {
                println("Received left command.")
                r.keyPress(KeyEvent.VK_LEFT)
                r.keyRelease(KeyEvent.VK_LEFT)
            }
            "right" -> {
                println("Received right command.")
                r.keyPress(KeyEvent.VK_RIGHT)
                r.keyRelease(KeyEvent.VK_RIGHT)
            }
            "test" -> {
                println("Received test command.")

                if (egg.nextInt(10) == 3) {
                    println("I'm sorry Dave, I'm afraid I cannot do that.")
                    return
                }

                // Simulate Super key press. If on Mac, try to open Spotlight.
                r.keyPress(KeyEvent.VK_META)
                if (System.getProperty("os.name").contains("Mac")) {
                    r.keyPress(KeyEvent.VK_SPACE)
                    r.keyRelease(KeyEvent.VK_SPACE)

                }
                r.keyRelease(KeyEvent.VK_META)
            }
            "ping" -> {
                println("pong")
                if (topic != null && message != null)
                    Mqtt.publish(topic, "pong")
            }
        }
    }
}