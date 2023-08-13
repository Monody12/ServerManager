package com.example.servermanager.ssh


import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.annotation.PreDestroy

/**
 * TODO 仅开发调试使用，上线时删除！！！
 */
@Slf4j
@RestController
class ShellController {

    @Volatile
    private var isConnected = false

    @Autowired
    private lateinit var sshManager: SSHManager

    private val logger = LoggerFactory.getLogger(ShellController::class.java)

    @PostMapping("/execute")
    fun executeCommand(@RequestBody command: String): String {
        // 建立 SSH 连接（仅在第一次调用时执行）
        if (!isConnected) {
            synchronized(this) {
                if (!isConnected) {
                    sshManager.connect("vm.local", 22, "root", "1234")
                    isConnected = true
                }
            }
        }
        // 执行命令
        val output: String = sshManager.executeCommand(command)
        // TODO 输出日志，打印命令和输出
        logger.info("command: $command，output: $output")
        // 返回命令输出
        return output
    }

    @PreDestroy
    fun disconnect() {
        // 断开 SSH 连接（在应用关闭时执行）
        sshManager.disconnect()
        isConnected = false
    }
}
