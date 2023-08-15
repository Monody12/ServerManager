package com.example.servermanager.ssh

import org.springframework.beans.factory.annotation.Value
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.connection.channel.direct.Session
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class SSHManager(
    @Value("\${ssh.password}")
    val password:String
) {
    private var sshClient: SSHClient? = null

//    @Volatile
//    private var isConnected = false

    private val logger = LoggerFactory.getLogger(SSHManager::class.java)
    private fun connect() {
        sshClient = SSHClient()
        sshClient?.loadKnownHosts()
        sshClient?.connect("vm.local", 22)
        sshClient?.authPassword("root", password)
    }

    private fun disconnect() {
        sshClient?.disconnect()
        sshClient = null
    }

    /**
     * 执行命令
     * @param command 命令
     * @return 命令输出 Map
     * Map中存放 success 和 output 两个键值对
     * success 为 true 表示命令执行成功，为 false 表示命令执行失败
     * output 为命令输出
     */
    fun executeCommand(command: String): CommandExecuteRes {
        // 建立 SSH 连接（仅在第一次调用时执行）
        if (sshClient == null || sshClient!!.isConnected && sshClient!!.isAuthenticated) {
            synchronized(this) {
                if (sshClient == null || sshClient!!.isConnected && sshClient!!.isAuthenticated) {
                    this.connect()
                }
            }
        }

        val result = CommandExecuteRes()

        sshClient?.let { client ->
            val session: Session = client.startSession()
            val cmd: Session.Command = session.exec(command)

            val output = cmd.inputStream.bufferedReader().use { it.readText() }
            val error = cmd.errorStream.bufferedReader().use { it.readText() }

            cmd.join()
            session.close()

            result.success = cmd.exitStatus == 0
            result.output = output.ifEmpty { error }

            logger.info("command: $command，output: ${result.output}")

            return result
        }

        return result
    }
    @PostConstruct
    fun postConstructConnect() {
        // 建立 SSH 连接（在应用启动时执行）
        this.connect()
    }

    @PreDestroy
    fun preDestroyDisconnect() {
        // 断开 SSH 连接（在应用关闭时执行）
        this.disconnect()
    }
}
