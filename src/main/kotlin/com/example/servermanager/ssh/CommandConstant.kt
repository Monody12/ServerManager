package com.example.servermanager.ssh

/**
 * 以下命令在CentOS 7中运行
 */
class CommandConstant {
    companion object{
        /**
         * 检查是否安装了 Docker
         */
        const val DOCKER_CHECK_INSTALL: String = "docker -v"

        /**
         * 安装Docker依赖包
         */
        const val DOCKER_INSTALL_DEPENDENCIES: String = "yum install -y yum-utils device-mapper-persistent-data lvm2"

        /**
         * 设置Docker镜像源
         */
        const val DOCKER_SET_MIRROR: String =
            "yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo"

        /**
         * 安装Docker
         */
        const val DOCKER_INSTALL: String = "yum install -y docker-ce-18.03.1.ce"

        /**
         * 一行命令检测docker中是否安装mysql，有则输出1，没有则输出0
         */
        const val MYSQL_CHECK: String = "docker ps -a | grep mysql | wc -l"
        /**
         * Docker拉取MySQL镜像
         */
        const val MYSQL_PULL: String = "docker pull mysql:8.0.27"
        /**
         * Docker启动MySQL容器
         */
        const val MYSQL_START: String =
            "docker run -p 3306:3306 --name mysql -v /home/mysql/conf:/etc/mysql/conf.d -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=12345678 -d mysql:8.0.27"
        /**
         * Docker检测是否安装Redis
         */
        const val REDIS_CHECK: String = "docker ps -a | grep redis | wc -l"
        /**
         * Docker拉取Redis镜像
         */
        const val REDIS_PULL: String = "docker pull redis:6.2.6"
        /**
         * Docker启动Redis容器
         */
        const val REDIS_START: String = "docker run -p 6379:6379 --name redis -v /home/redis/data:/data -d redis:6.2.6"
        /**
         * Docker检测是否安装MongoDB
         */
        const val MONGODB_CHECK: String = "docker ps -a | grep mongo | wc -l"
        /**
         * Docker拉取MongoDB镜像
         */
        const val MONGODB_PULL: String = "docker pull mongo:5.0.3"
        /**
         * Docker启动MongoDB容器
         */
        const val MONGODB_START: String = "docker run -p 27017:27017 --name mongo -v /home/mongo/data:/data/db -d mongo:5.0.3"

    }

}