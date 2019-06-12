# ssh连接
1. [使用SSH密钥对连接Linux实例](https://help.aliyun.com/document_detail/51798.html?spm=a2c4g.11186623.4.1.155011c87si1RC)

> mac ssh 目录 `~/.ssh`,可配置config文件简化登录

2. [非root用户登录](https://blog.csdn.net/jincheng2817/article/details/86660078)


# nginx
- [location](http://nginx.org/en/docs/http/ngx_http_core_module.html#location)
- [root vs alias](https://stackoverflow.com/questions/10631933/nginx-static-file-serving-confusion-with-root-alias)
- [ssl](https://help.aliyun.com/knowledge_detail/95491.html?spm=5176.2020520154.cas.27.5c97l1kUl1kUES)

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
