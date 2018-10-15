package org.augbari.magicwand

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

object Mqtt {
    private var client: MqttClient? = null
    private val memory: MemoryPersistence = MemoryPersistence()
    private val connOptions: MqttConnectOptions = MqttConnectOptions()

    private var username: String
        get() {
            return connOptions.userName
        }
        set(value) {
            connOptions.userName = value
        }

    private var password: String
        get() {
            return connOptions.password.toString()
        }
        set(value) {
            connOptions.password = value.toCharArray()
        }

    var clientName: String = MqttClient.generateClientId()

    fun connect(broker: String, username: String?, password: String?) {
        try {
            if (username != null)
                Mqtt.username = username
            if (password != null)
                Mqtt.password = password

            client = MqttClient(broker, clientName, memory)
            connOptions.isCleanSession = true

            println("Trying to connect to $broker")
            client!!.connect(connOptions)

            if (client!!.isConnected)
                println("Client connected successfully.")
        } catch (e: MqttException) {
            println("Unable to connect to $broker")
            println("Reason: ${e.reasonCode}")
            println("Message: ${e.message}")
            println("Localized: ${e.localizedMessage}")
            println("Cause: ${e.cause}")
            e.printStackTrace()
        }
    }

    fun disconnect() {
        if (client == null) {
            println("Client is not connected yet!")
            return
        }

        client!!.disconnect()

        if (!client!!.isConnected)
            println("Client disconnected successfully.")
    }

    fun subscribe(callback: IMqttMessageListener, vararg topics: String) {
        if (client == null) {
            println("Client is not connected yet!")
            return
        }

        for (topic in topics) {
            client!!.subscribe(topic, callback)
        }
    }

    fun publish(topic: String, message: String) {
        println("Sending to $topic: $message")

        if (client == null) {
            println("Client is not connected yet!")
            return
        }

        client!!.publish(topic, MqttMessage(message.toByteArray()))
    }
}