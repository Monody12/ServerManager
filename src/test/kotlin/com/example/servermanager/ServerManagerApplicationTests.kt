package com.example.servermanager

import com.example.servermanager.ssh.CommandConstant
import com.example.servermanager.ssh.SSHManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ServerManagerApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Autowired
    lateinit var sshManager: SSHManager

    @Test
    fun testCommandExecute() {
        val command = CommandConstant.DOCKER_CHECK_MYSQL
        println(sshManager.executeCommand(command))
    }
}
