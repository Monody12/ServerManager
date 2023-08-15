package com.example.servermanager.ssh.websocket

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig (
    @Value("\${ssh.password}")
    val password:String
){
    @Bean
    fun sshTerminal(): SshTerminal {
        return SshTerminal("vm.local", 22, "root", password)
    }

    @Bean
    fun sshWebSocketHandler(sshTerminal: SshTerminal): SshWebSocketHandler {
        return SshWebSocketHandler(sshTerminal)
    }

    @Bean
    fun webSocketConfigurer(sshWebSocketHandler: SshWebSocketHandler): WebSocketConfigurer {
        return object : WebSocketConfigurer {
            override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
                registry.addHandler(sshWebSocketHandler, "/ssh-terminal").setAllowedOrigins("*")
            }
        }
    }
}