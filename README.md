# e3mall
- 开发环境：sts, maven, git, jdk1.8
- 软件架构：ssm, mysql, redis, solr, activemq
- 项目描述:
	- 宜立方商城是一个综合性的B2C平台，类似京东商城，天猫商城。会员可以在商城浏览商品，加入购物车，下单，参加各种活动。
	- 宜立方商城采用soa架构（面向服务的架构）。也就是把工程拆分成服务层，表现层两个工程。服务层中包含业务逻辑，只需要对外提供服务即可。变现层只需要处理和页面的交互，业务逻辑都是调用服务层的服务来实现。

- 系统架构图
![系统架构图](https://img-blog.csdn.net/20180404010221510?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05vclJpbkluVGhlU2t5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

- 工程架构：
	- 管理系统：管理商品，用户，内容发布，同步solr索引等功能。
	- 商城门户：用户可以在前台系统中注册，登录，浏览商品，添加购物车，下单等功能。
	- 搜索系统：使用solr提供高效的商品搜索。
	- 单点登录系统：将用户的登录映射到所有子系统中，为多个系统之间提供用户登录凭证。
	- 购物车系统：用户未登录时，存入cookie中。用户登录后，合并cookie的数据，存入redis中。
	- 订单系统：提供下单，查询订单，修改订单状态吗，定时处理订单等。

- 网络拓扑图
![网络拓扑图](https://img-blog.csdn.net/20180404010326124?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05vclJpbkluVGhlU2t5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

- 相关框架：
1. `dubbo`: 使用rpc协议进行远程调用，直接使用socket通信。传输效率高，并且可以统计出系统之间的调用关系，调用次数。
当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需要增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于提高机器利用率的`资源调度和资源治理中心（SOA）`是关键。
2. `zookeeper`: 官方推荐使用zookeeper注册中心。注册中心负责服务地址的注册和查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不转发请求，压力较小。
3. `FastDFS`: c语言编写的一款开源的分布式文件系统。FastDFS为互联网量身定制，充分考虑了`冗余备份、负载均衡、线性扩容等机智，并注重高可用、高性能`等指标，使用FastDFS很容易搭建一套高性能的文件服务器集群提供文件上传，下载等。
4. `redis`: redis集群，用于缓存热数据（经常会被查询，但是不经常被修改或删除的数据）。[redis应用场景](https://www.cnblogs.com/NiceCui/p/7794659.html)
5. `solr`: solrCloud，为商品建立索引，实现搜索功能，快速高效。
6. `activemq`: 使用Activemq发布订阅消息，通过消息队列实现前后台商品同步。两段提交协议来实现分布式事务。
