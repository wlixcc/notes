# ssh连接
1. [使用SSH密钥对连接Linux实例](https://help.aliyun.com/document_detail/51798.html?spm=a2c4g.11186623.4.1.155011c87si1RC)

> mac ssh 目录 `~/.ssh`,可配置config文件简化登录

2. [非root用户登录](https://blog.csdn.net/jincheng2817/article/details/86660078)


# nginx
- [location](http://nginx.org/en/docs/http/ngx_http_core_module.html#location)
- [root vs alias](https://stackoverflow.com/questions/10631933/nginx-static-file-serving-confusion-with-root-alias)
- [ssl](https://help.aliyun.com/knowledge_detail/95491.html?spm=5176.2020520154.cas.27.5c97l1kUl1kUES)
- [解决vuejs或react应用在nginx非根目录下部署时访问404的问题
](https://blog.csdn.net/weixin_33868027/article/details/92139392)

	1. 关注@rewrites

### mac
- 安装路径`/usr/local/etc/nginx/nginx.conf`
- `sudo nginx -s stop` && `sudo nginx`
- mac nginx 部署路径`/usr/local/var/www` ,build文件传放在此路径才能访问

### centos (部署web项目)
- [how-to-install-nginx-on-centos-7](https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-centos-7)
- [React项目从创建到打包到云服务器指南](https://segmentfault.com/a/1190000011085024)

- centos nginx 路径 `/etc/nginx/`
- `npm run build` 将build文件夹传到服务器对应路径
- `nginx -s reload`
- [ssl优化](http://nginx.org/en/docs/http/configuring_https_servers.html#optimization)

### react

- [Deployment](https://facebook.github.io/create-react-app/docs/deployment);

# JAVA
1. [安装](https://tecadmin.net/install-java-8-on-centos-rhel-and-fedora/)
2. [JAVA运行环境配置](https://stackoverflow.com/questions/16271316/setting-java-home-classpath-in-centos-6)

# 文件上传
1. 使用webstrom
2. 使用[filezilla](https://filezilla-project.org/download.php?type=client)

# mysql

- [安装](https://dev.mysql.com/doc/mysql-yum-repo-quick-guide/en/#repo-qg-yum-installing)
- [外网连接用户设置](https://stackoverflow.com/questions/1559955/host-xxx-xx-xxx-xxx-is-not-allowed-to-connect-to-this-mysql-server)
- [备份](https://www.linode.com/docs/databases/mysql/use-mysqldump-to-back-up-mysql-or-mariadb/)
	
		mysqldump -u robchef_test -p intelligent_kitchen_test --single-transaction --quick --lock-tables=false > /var/intelligentkitchen/mysqlbackup/intelligent_kitchen_test-backup-$(date +%F).sql

# Spring-boot部署
- [spring-boot-application-as-a-service](https://stackoverflow.com/questions/21503883/spring-boot-application-as-a-service)

# MYSQL备份
	
	#!/bin/bash

	######备份######
	username="root"
	password="password"
	db_name="dbname"
	bak_dir="/backup/"
	time="$(date +"%Y%m%d%H%M%S")"
	
	mysqldump -u${username} -p${password} ${db_name} --single-transaction --quick --lock-tables=false > ${bak_dir}${db_name}-backup-${time}.sql
	
	#remove out-date backup
	find ${bak_dir} -name "$db_name*.sql" -type f -mtime +1 -exec rm -rf {} \; > /dev/null 2>&1

1. `chmod 777 	/var/intelligentkitchen/server/test/backup/backup.sh`
2. `crontab -e` 设置定时任务	`00 3 * * * /var/intelligentkitchen/server/test/backup/backup.sh` `:wq保存`

# 定期执行脚本
1. 编写sh脚本
2. `chmod 777 /path/test.sh`
3. `/var/spool/cron` 中添加定时任务


# nginx 范例
	
			# For more information on configuration, see:
	#   * Official English Documentation: http://nginx.org/en/docs/
	#   * Official Russian Documentation: http://nginx.org/ru/docs/
	
	user nginx;
	worker_processes auto;
	error_log /var/log/nginx/error.log;
	pid /run/nginx.pid;
	
	# Load dynamic modules. See /usr/share/nginx/README.dynamic.
	include /usr/share/nginx/modules/*.conf;
	
	events {
	    worker_connections 1024;
	}
	
	http {
	    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
	                      '$status $body_bytes_sent "$http_referer" '
	                      '"$http_user_agent" "$http_x_forwarded_for"';
	
	    access_log  /var/log/nginx/access.log  main;
	
	    sendfile            on;
	    tcp_nopush          on;
	    tcp_nodelay         on;
	    keepalive_timeout   65;
	    types_hash_max_size 2048;
	
	    include             /etc/nginx/mime.types;
	    default_type        application/octet-stream;
	
	    # Load modular configuration files from the /etc/nginx/conf.d directory.
	    # See http://nginx.org/en/docs/ngx_core_module.html#include
	    # for more information.
	    include /etc/nginx/conf.d/*.conf;
	
	    server {
	        server_name robchef.com www.robchef.com;
	
	        location / {
	            return 301 https://$host$request_uri;
	        }
	    }
	
	    #web控制台
	    server {
	        server_name robchef.cn www.robchef.cn;
	        location / {
	            root      /var/intelligentkitchen/web/cashier/;
	            try_files  $uri $uri/ /index.html;
	        }
	    }
	
	    ssl_session_cache   shared:SSL:10m;
	    ssl_session_timeout 10m;
	
	    #HTTPS server
	    server {
	        listen 443;
	        server_name localhost;
	        keepalive_timeout   70;
	        ssl on;
	        ssl_certificate cert/robchef.com.pem;
	        ssl_certificate_key cert/robchef.com.key;
	        ssl_session_timeout 5m;
	        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
	        ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
	        ssl_prefer_server_ciphers on;
	
	         location @rewrites {
	                                rewrite ^/(.+?)/(.+)$ /$1/index.html last;
	                        }
	
	        location  / {
	            root      /var/intelligentkitchen/web/homepage;
	            try_files  $uri $uri/ /index.html;
	        }
	
	        location  /privacy {
	            root      /var/intelligentkitchen/web;
	            try_files  $uri $uri/ @rewrites;
	        }
	
	        location /admin {
	            root      /var/intelligentkitchen/web;
	            try_files  $uri $uri/ @rewrites;
	        }
	
	        location /admin-test {
	            root      /var/intelligentkitchen/web;
	            try_files  $uri $uri/ @rewrites;
	        }
	
	        location /api {
	            proxy_set_header Host $host;
	            proxy_set_header X-Real-IP $remote_addr;
	            proxy_pass http://localhost:8080;
	        }
	
	        location /api-test {
	            proxy_set_header Host $host;
	            proxy_set_header X-Real-IP $remote_addr;
	            proxy_pass http://localhost:8079;
	        }
	    }
	
	}




# 1.springboot项目部署
1. 使用`alibaba Cloud Toolkit`插件部署
	
	1. 选择正确的ecs
	2. 设置部署路径 eg: `/var/intelligentkitchen/server/test`
	3. 设置上传后执行的命名 command: `systemctl restart intelligentkitchen_test`
		
		1. 	`systemctl`详情参见[spring-boot-application-as-a-service](https://stackoverflow.com/questions/21503883/spring-boot-application-as-a-service)
			

# 2.Web项目部署

1. build后生成`dist文件目录`

	1. 若是`ant design pro`项目,在`package.json`中修改环境变为`prod`,如何配置环境变量参考`ant deign`实战文档
	2. 通过[filezilla](https://filezilla-project.org/download.php?type=client)上传文件到对应文件夹下
