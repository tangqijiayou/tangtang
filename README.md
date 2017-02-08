项目运行方式：

1.在数据库中创建名为demo的数据库，然后根据自己的环境配置application-dev.properties配置文件，在application.properties中可以看到默认的配置为dev





项目涉及的技术：

1.shiro,网上较多的为xml配置的，所以这里我把它写成了java文件格式的体验下，有关内容在shiro这个包下这里有自己实现的realm，实现了用户权限控制，记住我，踢人，登出等功能，UserController实现登入登出，可用postman测试