### 一、RabbitMQ安装准备(Windows)
>下载资源
RabbitMQ依赖erlang,所以先安装erlang，然后再安装RabbitMQ;
>
> erlang，下载地址：http://www.erlang.org/download
>
> RabbitMQ,下载地址： http://www.rabbitmq.com/releases/rabbitmq-server
先安装erlang,双击erlang的安装文件即可，然后配置环境变量(一般安装完成就默认配置好了)：
> 
>ERLANG_HOME=D:\Program Files\erl7.1
> 
>追加到path=;%ERLANG_HOME%\bin;

> 验证erlang是否安装成功， 打开cmd命令窗口，进入erlang的bin路径，输入erl命令，如果出现如下提示，则说明erlang安装成功：
> D:\Program Files\erl7.1\bin>erl
> Eshell V7.1 (abort with ^G)

> 再安装RabbitMQ，双击安装文件即可，安装完毕后， 设置环境变量：
   RABBITMQ_SERVER=D:\Program Files\RabbitMQ Server\rabbitmq_server-3.5.6
   追加到path=%RABBITMQ_SERVER%\sbin;

> 验证RabbitMQ是否安装成功，在CMD命令窗口输入：

> C:\Windows\system32>rabbitmq-service


*********************
Service control usage
*********************

rabbitmq-service help    - Display this help
rabbitmq-service install - Install the RabbitMQ service
rabbitmq-service remove  - Remove the RabbitMQ service

The following actions can also be accomplished by using
Windows Services Management Console (services.msc):

rabbitmq-service start   - Start the RabbitMQ service
rabbitmq-service stop    - Stop the RabbitMQ service
rabbitmq-service disable - Disable the RabbitMQ service
rabbitmq-service enable  - Enable the RabbitMQ service
复制代码
     如出现以上命令的解释，则说明安装成功；

> 然后安装服务，打开cmd窗体，进入MQ的安装目录 D:\Program Files\RabbitMQ Server\rabbitmq_server-3.5.6\sbin路径，然后执行 rabbitmq-service start启动：

>> D:\Program Files\RabbitMQ Server\rabbitmq_server-3.5.6\sbin>rabbitmq-service start

>> D:\Program Files\erl7.1\erts-7.1\bin\erlsrv: Service RabbitMQ started. 提示启动成功；

> 然后安装web管理插件，执行命令如下：


> D:\Program Files\RabbitMQ Server\rabbitmq_server-3.5.6\sbin>rabbitmq-plugins enable rabbitmq_management
>
>>The following plugins have been enabled:
  mochiweb
  webmachine
  rabbitmq_web_dispatch
  amqp_client
  rabbitmq_management_agent
  rabbitmq_management

>> Applying plugin configuration to rabbit@GUOY... started 6 plugins.

> 出现以上提示，说明安装成功，可以通过访问http://localhost:15672进行测试，默认的登陆账号为：guest，密码为：guest。