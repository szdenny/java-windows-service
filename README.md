# java-windows-service

## 安装步骤
1. maven package
2. copy hbc-1.0.jar to installed
3. copy src/main/script/service.bat to installed
4. modify service.bat to real port and address
```
set server_port=8081
set server_ip=localhost
set rdo_process=rdo
```
5. run service.bat install to install service

## 注意事项
1. windows对服务程序所在的目录不支持"-"字符，安装的时候需要注意

## 
see also 
1. http://commons.apache.org/proper/commons-daemon/index.html
2. https://dzone.com/articles/java-programs-as-windows-services
