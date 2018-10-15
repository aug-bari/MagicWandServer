plugins {
    application
    kotlin("jvm") version "1.2.61"
}

application {
    mainClassName = "org.augbari.magicwand.AppKt"
}

dependencies {
    compile(kotlin("stdlib"))

    compile("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.0")
}

repositories {
    jcenter()
}