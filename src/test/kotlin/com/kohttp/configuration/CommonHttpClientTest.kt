package com.kohttp.configuration

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import org.junit.Test

/**
 * Created by Sergey Rybalkin on 08/08/2018.
 */
class CommonHttpClientTest {

//    val kohttp by object : PropertyGroup() {
//        val client by object: PropertyGroup() {
//
//        }
//    }

    @Test
    fun name() {
        val a = Key("kohttp.client.connection-pool", intType)
        val config = ConfigurationProperties.fromResource("kohttp.yaml")

        println(config[a])
    }
}