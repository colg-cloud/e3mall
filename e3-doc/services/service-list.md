# 1. 服务列表

```ini
# 启动当前机器所有服务
/root/shell/e3-start.sh
# 关闭当前机器所有服务, 并关机
/root/shell/e3-stop.sh
```



## 1. 192.168.21.101 `root 123456`

```ini
# 安装软件: jdk, mysql, tomcat, redis, zookeeper

# tomcat
ps aux|grep tomcat
/usr/local/tomcats/apache-tomcat-8.5.28/bin/startup.sh
/usr/local/tomcats/apache-tomcat-8.5.28/bin/shutdown.sh

# zookeeper 注册中心
ps aux|grep zookeeper
/usr/local/zookeeper-3.4.6/bin/zkServer.sh start
/usr/local/zookeeper-3.4.6/bin/zkServer.sh stop
/usr/local/zookeeper-3.4.6/bin/zkServer.sh status

# dubbo-admin [root root]
http://192.168.21.101:8080/dubbo-admin/
```



## 2. 192.168.21.102 `root 123456`

```ini
# 安装软件: jdk, activeMQ

# activemq 消息队列
ps aux|grep activemq
/usr/local/activeMQ/apache-activemq-5.12.0/bin/activemq start
/usr/local/activeMQ/apache-activemq-5.12.0/bin/activemq stop
/usr/local/activeMQ/apache-activemq-5.12.0/bin/activemq status

# activemq [admin admin]
http://192.168.21.102:8161/
```



## 3. 192.168.21.103 `root 123456`

```ini
# 安装软件: jdk, tomcat, tomcat(集群), redis, redis(集群), solr, zookeeper, zookeeper(集群)

# redis 单机
ps aux|grep redis
/usr/local/redis3.0/bin/redis-server /usr/local/redis3.0/bin/redis.conf
/usr/local/redis3.0/bin/redis-cli -h 192.168.21.103 -p 6379
ping PONG 健康检查
/usr/local/redis3.0/bin/redis-cli shutdown

# redis 集群
ps aux|grep cluster
/usr/local/redis3.0/redis-cluster/start-redis-cluster-all.sh
/usr/local/redis3.0/redis-cluster/stop-redis-cluster-all.sh

# solr
ps aux|grep tomcat
/usr/local/solr/apache-tomcat-8.0.50/bin/startup.sh
/usr/local/solr/apache-tomcat-8.0.50/bin/shutdown.sh

# solr 单机[8080]
http://192.168.21.103:8080/solr

# zookeeper 集群
ps aux|grep solr-cloud/zookeeper-
/usr/local/solr-cloud/start-zookeeper-all.sh
/usr/local/solr-cloud/stop-zookeeper-all.sh

# tomcat 集群
ps aux|grep solr-cloud/tomcat-
/usr/local/solr-cloud/start-tomcat-all.sh
/usr/local/solr-cloud/stop-tomcat-all.sh

# solr 集群[8180,8280,8380,8480]
http://192.168.21.103:8180/solr
```



## 4. 192.168.21.110 `root 123456`

```ini
# 安装软件: nginx, fastDFS, nginx插件

# fastDFS
ps aux|grep tracker
/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart
killall fdfs_trackerd

ps aux|grep storage
/usr/bin/fdfs_storaged /etc/fdfs/storage.conf
/usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart
killall fdfs_storaged

# nginx
ps aux|grep nginx
/usr/local/nginx/sbin/nginx
/usr/local/nginx/sbin/nginx -s quit
/usr/local/nginx/sbin/nginx -s stop
/usr/local/nginx/sbin/nginx -s reload

# nginx
http://192.168.21.110/
```

## 5. 127.0.0.1

```ini
# nginx 配置html静态服务器
cd D:\workspace-all\iheima\e3mall\e3-doc\nginx-1.14.0

nginx
nginx -s quit
nginx -s reload

# nginx
http://127.0.0.1/item/{itemId}.html
```

