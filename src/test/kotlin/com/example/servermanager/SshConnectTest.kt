package com.example.servermanager
//
//import com.jcraft.jsch.ChannelExec
//import com.jcraft.jsch.JSch
//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.time.LocalDateTime
//
//class SshConnectTest {
//
//}
//
//fun main() {
//    val host = "vm.local" // 服务器主机名或 IP 地址
//    val username = "root" // 登录用户名
//    val password = "1234" // 登录密码
//    val command = "pwd" // 要执行的命令
//
//    try {
//        println(LocalDateTime.now())
//        val jsch = JSch()
//
//        // 创建会话
//        val session = jsch.getSession(username, host, 22)
//        session.setPassword(password)
//
//        // 配置 StrictHostKeyChecking 为 no，避免 UnknownHostKey 异常
//        val config = java.util.Properties()
//        config["StrictHostKeyChecking"] = "no"
//        session.setConfig(config)
//
//        // 开启会话连接
//        session.connect()
//
//        // 创建通道
//        val channel = session.openChannel("exec") as ChannelExec
//
//        // 设置命令
//        channel.setCommand(command)
//
//        // 获取命令输出流
//        val commandOutput = channel.inputStream
//
//        // 连接通道
//        channel.connect()
//
//        // 读取命令输出
//        val reader = BufferedReader(InputStreamReader(commandOutput))
//        var line: String?
//        while (reader.readLine().also { line = it } != null) {
//            println(line)
//        }
//
//        // 关闭连接
//        reader.close()
//        channel.disconnect()
//        session.disconnect()
//        println(LocalDateTime.now())
//
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
