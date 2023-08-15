package com.example.servermanager.ssh.websocket

import com.jcraft.jsch.ChannelShell
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.springframework.stereotype.Component

class SshTerminal(private val host: String, private val port: Int, private val username: String, private val password: String) {
    private lateinit var session: Session
    private lateinit var channel: ChannelShell

    fun connect() {
        val jsch = JSch()
        session = jsch.getSession(username, host, port)
        session.setPassword(password)
        session.setConfig("StrictHostKeyChecking", "no")
        session.connect()

        channel = session.openChannel("shell") as ChannelShell
        channel.connect()
    }

    fun executeCommand(command: String): String {
        val outputStream = channel.outputStream
        val inputStream = channel.inputStream

        outputStream.write(command.toByteArray())
        outputStream.flush()

        val buffer = ByteArray(1024)
        val stringBuilder = StringBuilder()

        while (true) {
            while (inputStream.available() > 0) {
                val readLength = inputStream.read(buffer, 0, 1024)
                if (readLength < 0) break
                stringBuilder.append(String(buffer, 0, readLength))
            }

            if (channel.isClosed) {
                if (inputStream.available() > 0) continue
                stringBuilder.append("\nExit status: ${channel.exitStatus}")
                break
            }
        }

        return stringBuilder.toString()
    }

    fun disconnect() {
        channel.disconnect()
        session.disconnect()
    }
}