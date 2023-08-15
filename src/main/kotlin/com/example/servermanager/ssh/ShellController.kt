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

    @Autowired
    private lateinit var sshManager: SSHManager

    private val logger = LoggerFactory.getLogger(ShellController::class.java)


    data class CommandStrings(val commands: List<String>)
    @PostMapping("/execute")
    fun executeCommand(@RequestBody commands: CommandStrings): List<CommandExecuteRes> {
        // 执行命令
        val res = mutableListOf<CommandExecuteRes>()
        for (command in commands.commands) {
            val output = sshManager.executeCommand(command)
            res.add(output)
        }
        // 返回命令输出
        return res
    }


}
