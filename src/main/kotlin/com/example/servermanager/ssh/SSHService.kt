package com.example.servermanager.ssh

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class SSHService {
    @Autowired
    private lateinit var sshManager: SSHManager

    fun executeCommand(command: String): CommandExecuteRes {
        return sshManager.executeCommand(command)
    }
}