package com.example.servermanager.ssh

import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.connection.channel.direct.Session
import org.springframework.stereotype.Component

@Component
class SSHManager {
    private var sshClient: SSHClient? = null

    fun connect(host: String, port: Int, username: String, password: String) {
        sshClient = SSHClient()
        sshClient?.loadKnownHosts()
        sshClient?.connect(host, port)
        sshClient?.authPassword(username, password)
    }

    fun disconnect() {
        sshClient?.disconnect()
    }

    fun executeCommand(command: String): String {
        sshClient?.let { client ->
            val session: Session = client.startSession()
            val cmd: Session.Command = session.exec(command)
            val output: String = cmd.inputStream.bufferedReader().readText()

            cmd.join()
            session.close()

            return output
        }

        return ""
    }
}
