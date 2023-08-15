package com.example.servermanager.ssh.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession

class SshWebSocketHandler(private val sshTerminal: SshTerminal) : WebSocketHandler {
    private lateinit var session: WebSocketSession

    override fun afterConnectionEstablished(session: WebSocketSession) {
        this.session = session
        sshTerminal.connect()
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val command = message.payload.toString()
        val result = sshTerminal.executeCommand(command)
        session.sendMessage(TextMessage(result))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sshTerminal.disconnect()
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        exception.printStackTrace()
    }

    override fun supportsPartialMessages(): Boolean {
        return false
    }
}