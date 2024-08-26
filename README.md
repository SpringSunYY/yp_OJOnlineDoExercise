# OJ在线判题系统

基于 Vue 3 + Spring Boot + Spring Cloud 微服务 + Docker 的 **编程题目在线评测系统** （简称 OJ）。

在系统前台，管理员可以创建、管理题目；用户可以自由搜索题目、阅读题目、编写并提交代码。

在系统后端，能够根据管理员设定的题目测试用例在 **代码\**\**沙箱** 中对代码进行编译、运行、判断输出是否正确。

其中，代码沙箱可以作为独立服务，提供给其他开发者使用。

## 技术选型

### 前端

- Vue 3
- Vue-CLI 脚手架
- Vuex 状态管理
- Arco Design 组件库
- 前端工程化：ESLint + Prettier + TypeScript
- ⭐️ 手写前端项目模板（通用布局、权限管理、状态管理、菜单生成）
- ⭐️ Markdown 富文本编辑器
- ⭐️ Monaco Editor 代码编辑器
- ⭐️ OpenAPI 前端代码生成

### 后端

- ⭐️ Java Spring Cloud + Spring Cloud Alibaba 微服务
  - Nacos 注册中心
  - OpenFeign 客户端调用
  - GateWay 网关
  - 聚合接口文档
- Java Spring Boot（万用后端模板）
- Java 进程控制
- ⭐️ Java 安全管理器
- ⭐️ Docker 代码沙箱实现
- ⭐️ 虚拟机 + 远程开发
- MySQL 数据库
- MyBatis-Plus 及 MyBatis X 自动生成
- Redis 分布式 Session
- ⭐️ RabbitMQ 消息队列
- ⭐️ 多种设计模式
  - 策略模式
  - 工厂模式
  - 代理模式
  - 模板方法模式
- 其他：部分并发编程、JVM 小知识

## 第一章：项目诞生

### 项目介绍 | OJ 系统常用概念

用于在线评测编程题的系统，能够根据用户提交的代码、出题人预先设置的题目输入和输出用例，进行编译代码、运行代码、判断代码运行结果是否正确。



### 项目介绍 | 企业项目开发流程

1. 项目介绍、项目调研、需求分析
2. 核心业务流程
3. 项目要做的功能（功能模块）
4. 技术选型（技术预研）
5. 项目初始化
6. 项目开发
7. 测试
8. 优化
9. 代码提交、代码审核
10. 产品验收
11. 上线



### 项目介绍 | 主流 OJ 系统调研

github上面找一些OJ的系统看看，可以参照参照他们的功能。

比如判题，他们的逻辑，有哪些特点，功能等等。



### 项目介绍 | 核心实现模块介绍

#### 实现核心

##### 1）权限校验

谁能提代码，谁不能提代码

##### **2）代码沙箱（安全沙箱）**

用户代码藏毒：写个木马文件、修改系统权限

沙箱：隔离的、安全的环境，用户的代码不会影响到沙箱之外的系统的运行

资源分配：系统的内存就 2 个 G，用户疯狂占用资源占满你的内存，其他人就用不了了。所以要限制用户程序的占用资源。

##### 3）判题规则

题目用例的比对，结果的验证

##### 4）任务调度

服务器资源有限，用户要排队，按照顺序去依次执行判题，而不是直接拒绝



### 项目介绍 | 核心业务流程（2 种作图）

![img](./assets/3f45029c-a826-48da-a878-9253faec7583.png)

![img](./assets/c18837f1-139d-4cd3-ab2c-c5f06843636e.png)

判题服务：获取题目信息、预计的输入输出结果，返回给主业务后端：用户的答案是否正确

代码沙箱：只负责运行代码，给出结果，不管什么结果是正确的。



### 项目介绍 | 系统功能梳理

##### 功能

1. 题目模块
   1. 创建题目（管理员）
   2. 删除题目（管理员）
   3. 修改题目（管理员）
   4. 搜索题目（用户）
   5. 在线做题
   6. 提交题目代码
2. 用户模块
   1. 注册
   2. 登录
3. 判题模块
   1. 提交判题（结果是否正确与错误）
   2. 错误处理（内存溢出、安全性、超时）
   3. 自主实现 代码沙箱（安全沙箱）
   4. 开放接口（提供一个独立的新服务）



### 项目介绍 | 系统架构设计（架构设计图）

![img](./assets/e4dd0d06-1167-4d01-ab5c-ee77994ff931.png)



### OJ 系统实现方案（5 种方案讲解）

#### 主流的 OJ 系统实现方案

开发原则：能用别人现成的，就不要自己写

##### 1、用现成的 OJ 系统

网上有很多开源的 OJ 项目，比如青岛 OJ、HustOJ 等，可以直接下载开源代码自己部署。

比较推荐的是 judge0，这是一个非常成熟的商业 OJ 项目，支持 60 多种编程语言！

> 代码：https://github.com/judge0/judge0

##### 2、用现成的服务

如果你不希望完整部署一套大而全的代码，只是想复用他人已经实现的、最复杂的判题逻辑，那么可以直接使用现成的 **判题 API** 、或者现成的 **代码沙箱** 等服务。

比如 judge0 提供的判题 API，非常方便易用。只需要通过 HTTP 调用 submissions 判题接口，把用户的代码、输入值、预期的执行结果作为请求参数发送给 judge0 的服务器，它就能自动帮你编译执行程序，并且返回程序的运行结果。

如下图，发送了一段打印 "hello world" 的程序，得到了程序执行的时间、状态等：

![img](./assets/image-20230729125632846.png)

> API 的作用：接受代码、返回执行结果

Judge0 API 地址：https://rapidapi.com/judge0-official/api/judge0-ce

官方文档：https://ce.judge0.com/#submissions-submission-post

###### 流程

1. 先注册
2. 再开通订阅
3. 然后测试 language 接口
4. 测试执行代码接口 submissions

示例接口参数：

```json
{
  "source_code": "#include <stdio.h>\n\nint main(void) {\n  char name[10];\n  scanf(\"%s\", name);\n  printf(\"hello, %s\n\", name);\n  return 0;\n}",
  "language_id": "4",
  "stdin": "Judge0",
  "expected_output": "hello, Judge0"
}
复制代码
```

预期返回：

```json
{
  "source_code": "includestdiohintmainvoidcharname10scanfsnameprintfhellosname\nreturn0=\n",
  "language_id": 76,
  "stdin": "Judgew==\n",
  "expected_output": "helloJudge0=\n",
  "stdout": null,
  "status_id": 6,
  "created_at": "2023-07-27T13:50:30.433Z",
  "finished_at": "2023-07-27T13:50:31.022Z",
  "time": null,
  "memory": null,
  "stderr": null,
  "token": "8be000ad-2edb-4262-b367-7095a694e028",
  "number_of_runs": 1,
  "cpu_time_limit": "5.0",
  "cpu_extra_time": "1.0",
  "wall_time_limit": "10.0",
  "memory_limit": 128000,
  "stack_limit": 64000,
  "max_processes_and_or_threads": 60,
  "enable_per_process_and_thread_time_limit": false,
  "enable_per_process_and_thread_memory_limit": false,
  "max_file_size": 1024,
  "compile_output": "bWFpbi5jcHA6MToxOiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlk\nIFVURi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxC\nRT48ODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAw\nMUM+anfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxB\nOT48OUU+PEFEPjxFQj5uPEFFPn0KXgptYWluLmNwcDoxOjI6IGVycm9yOiB1\nbmtub3duIHR5cGUgbmFtZSAndycKPDhBPnclPEI5PjxVKzA1RUM+PEI1Ptio\nPDg2Pintmag8QTc+PEJFPjw4OD48OUQ+cjxVKzAwMTY+PEFCPjw5RD48QTk+\nPDlFPjxENz5LPFUrMDAxQz5qd+ydqTw5RT48QTY+PEI4PjxBNz48QjU+PEY4\nPl48OTY+Wiw8OUQ+PEE5Pjw5RT48QUQ+PEVCPm48QUU+fQogICAgXgptYWlu\nLmNwcDoxOjM6IGVycm9yOiBleHBlY3RlZCB1bnF1YWxpZmllZC1pZAo8OEE+\ndyU8Qjk+PFUrMDVFQz48QjU+2Kg8ODY+Ke2ZqDxBNz48QkU+PDg4Pjw5RD5y\nPFUrMDAxNj48QUI+PDlEPjxBOT48OUU+PEQ3Pks8VSswMDFDPmp37J2pPDlF\nPjxBNj48Qjg+PEE3PjxCNT48Rjg+Xjw5Nj5aLDw5RD48QTk+PDlFPjxBRD48\nRUI+bjxBRT59CiAgICAgXgptYWluLmNwcDoxOjQ6IGVycm9yOiBzb3VyY2Ug\nZmlsZSBpcyBub3QgdmFsaWQgVVRGLTgKPDhBPnclPEI5PjxVKzA1RUM+PEI1\nPtioPDg2Pintmag8QTc+PEJFPjw4OD48OUQ+cjxVKzAwMTY+PEFCPjw5RD48\nQTk+PDlFPjxENz5LPFUrMDAxQz5qd+ydqTw5RT48QTY+PEI4PjxBNz48QjU+\nPEY4Pl48OTY+Wiw8OUQ+PEE5Pjw5RT48QUQ+PEVCPm48QUU+fQogICAgICBe\nCm1haW4uY3BwOjE6NzogZXJyb3I6IHNvdXJjZSBmaWxlIGlzIG5vdCB2YWxp\nZCBVVEYtOAo8OEE+dyU8Qjk+PFUrMDVFQz48QjU+2Kg8ODY+Ke2ZqDxBNz48\nQkU+PDg4Pjw5RD5yPFUrMDAxNj48QUI+PDlEPjxBOT48OUU+PEQ3Pks8VSsw\nMDFDPmp37J2pPDlFPjxBNj48Qjg+PEE3PjxCNT48Rjg+Xjw5Nj5aLDw5RD48\nQTk+PDlFPjxBRD48RUI+bjxBRT59CiAgICAgICAgICAgICAgICAgIF4KbWFp\nbi5jcHA6MToxMDogZXJyb3I6IHNvdXJjZSBmaWxlIGlzIG5vdCB2YWxpZCBV\nVEYtOAo8OEE+dyU8Qjk+PFUrMDVFQz48QjU+2Kg8ODY+Ke2ZqDxBNz48QkU+\nPDg4Pjw5RD5yPFUrMDAxNj48QUI+PDlEPjxBOT48OUU+PEQ3Pks8VSswMDFD\nPmp37J2pPDlFPjxBNj48Qjg+PEE3PjxCNT48Rjg+Xjw5Nj5aLDw5RD48QTk+\nPDlFPjxBRD48RUI+bjxBRT59CiAgICAgICAgICAgICAgICAgICAgICAgXgpt\nYWluLmNwcDoxOjE1OiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlk\nIFVURi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxC\nRT48ODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAw\nMUM+anfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxB\nOT48OUU+PEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgXgptYWluLmNwcDoxOjE2OiBlcnJvcjogc291cmNlIGZpbGUgaXMg\nbm90IHZhbGlkIFVURi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p\n7ZmoPEE3PjxCRT48ODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48\nRDc+SzxVKzAwMUM+anfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2\nPlosPDlEPjxBOT48OUU+PEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgIF4KbWFpbi5jcHA6MToxNzogZXJyb3I6IHNv\ndXJjZSBmaWxlIGlzIG5vdCB2YWxpZCBVVEYtOAo8OEE+dyU8Qjk+PFUrMDVF\nQz48QjU+2Kg8ODY+Ke2ZqDxBNz48QkU+PDg4Pjw5RD5yPFUrMDAxNj48QUI+\nPDlEPjxBOT48OUU+PEQ3Pks8VSswMDFDPmp37J2pPDlFPjxBNj48Qjg+PEE3\nPjxCNT48Rjg+Xjw5Nj5aLDw5RD48QTk+PDlFPjxBRD48RUI+bjxBRT59CiAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXgptYWluLmNw\ncDoxOjE4OiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlkIFVURi04\nCjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxCRT48ODg+\nPDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAwMUM+anfs\nnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxBOT48OUU+\nPEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgXgptYWluLmNwcDoxOjIxOiBlcnJvcjogc291cmNlIGZp\nbGUgaXMgbm90IHZhbGlkIFVURi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7Y\nqDw4Nj4p7ZmoPEE3PjxCRT48ODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5\nPjw5RT48RDc+SzxVKzAwMUM+anfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxG\nOD5ePDk2PlosPDlEPjxBOT48OUU+PEFEPjxFQj5uPEFFPn0KICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nIF4KbWFpbi5jcHA6MToyMjogZXJyb3I6IHNvdXJjZSBmaWxlIGlzIG5vdCB2\nYWxpZCBVVEYtOAo8OEE+dyU8Qjk+PFUrMDVFQz48QjU+2Kg8ODY+Ke2ZqDxB\nNz48QkU+PDg4Pjw5RD5yPFUrMDAxNj48QUI+PDlEPjxBOT48OUU+PEQ3Pks8\nVSswMDFDPmp37J2pPDlFPjxBNj48Qjg+PEE3PjxCNT48Rjg+Xjw5Nj5aLDw5\nRD48QTk+PDlFPjxBRD48RUI+bjxBRT59CiAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXgptYWlu\nLmNwcDoxOjIzOiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlkIFVU\nRi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxCRT48\nODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAwMUM+\nanfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxBOT48\nOUU+PEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXgptYWluLmNw\ncDoxOjI0OiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlkIFVURi04\nCjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxCRT48ODg+\nPDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAwMUM+anfs\nnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxBOT48OUU+\nPEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIF4KbWFpbi5j\ncHA6MToyNTogZXJyb3I6IHNvdXJjZSBmaWxlIGlzIG5vdCB2YWxpZCBVVEYt\nOAo8OEE+dyU8Qjk+PFUrMDVFQz48QjU+2Kg8ODY+Ke2ZqDxBNz48QkU+PDg4\nPjw5RD5yPFUrMDAxNj48QUI+PDlEPjxBOT48OUU+PEQ3Pks8VSswMDFDPmp3\n7J2pPDlFPjxBNj48Qjg+PEE3PjxCNT48Rjg+Xjw5Nj5aLDw5RD48QTk+PDlF\nPjxBRD48RUI+bjxBRT59CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXgpt\nYWluLmNwcDoxOjMzOiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlk\nIFVURi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxC\nRT48ODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAw\nMUM+anfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxB\nOT48OUU+PEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgIF4KbWFpbi5jcHA6MTozNDogZXJyb3I6IHNv\ndXJjZSBmaWxlIGlzIG5vdCB2YWxpZCBVVEYtOAo8OEE+dyU8Qjk+PFUrMDVF\nQz48QjU+2Kg8ODY+Ke2ZqDxBNz48QkU+PDg4Pjw5RD5yPFUrMDAxNj48QUI+\nPDlEPjxBOT48OUU+PEQ3Pks8VSswMDFDPmp37J2pPDlFPjxBNj48Qjg+PEE3\nPjxCNT48Rjg+Xjw5Nj5aLDw5RD48QTk+PDlFPjxBRD48RUI+bjxBRT59CiAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nXgptYWluLmNwcDoxOjM1OiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZh\nbGlkIFVURi04Cjw4QT53JTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3\nPjxCRT48ODg+PDlEPnI8VSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxV\nKzAwMUM+anfsnak8OUU+PEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlE\nPjxBOT48OUU+PEFEPjxFQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXgptYWluLmNwcDoxOjM2\nOiBlcnJvcjogc291cmNlIGZpbGUgaXMgbm90IHZhbGlkIFVURi04Cjw4QT53\nJTxCOT48VSswNUVDPjxCNT7YqDw4Nj4p7ZmoPEE3PjxCRT48ODg+PDlEPnI8\nVSswMDE2PjxBQj48OUQ+PEE5Pjw5RT48RDc+SzxVKzAwMUM+anfsnak8OUU+\nPEE2PjxCOD48QTc+PEI1PjxGOD5ePDk2PlosPDlEPjxBOT48OUU+PEFEPjxF\nQj5uPEFFPn0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\nICAgICAgICAgICAgICAgICAgIF4KZmF0YWwgZXJyb3I6IHRvbyBtYW55IGVy\ncm9ycyBlbWl0dGVkLCBzdG9wcGluZyBub3cgWy1mZXJyb3ItbGltaXQ9XQoy\nMCBlcnJvcnMgZ2VuZXJhdGVkLgo=\n",
  "exit_code": null,
  "exit_signal": null,
  "message": null,
  "wall_time": null,
  "compiler_options": null,
  "command_line_arguments": null,
  "redirect_stderr_to_stdout": false,
  "callback_url": null,
  "additional_files": null,
  "enable_network": false,
  "status": {
    "id": 6,
    "description": "Compilation Error"
  },
  "language": {
    "id": 76,
    "name": "C++ (Clang 7.0.1)"
  }
}
复制代码
```

##### 3、自主开发

这种方式就不多说了，判题服务和代码沙箱都要自己实现，适合学习，但不适用于商业项目。我这次带大家做的 OJ 系统，就选择了自主开发，主打一个学习。

##### 4、把 AI 来当做代码沙箱

现在 AI 的能力已经十分强大了，我们可以把各种本来很复杂的功能直接交给 AI 来实现。

比如把 AI 当做代码沙箱，我们直接扔给他一段代码、输入参数，问他能否得到预期的结果，就实现了在线判题逻辑！

如下图：

![img](./assets/82f2a6a1-3945-42a1-90cf-3bcf2ed875eb.png)

之前带大家做的 [智能 BI 项目](https://www.code-nav.cn/course/1790980531403927553) ，就是把 AI 当做了智能数据分析师，来生成图表和分析结论。

只要你脑洞够大，AI + 编程 = 无限的可能~

##### 5、移花接木

这种方式最有意思、也最 “缺德”。很多同学估计想不到。

那就是可以通过让程序来操作模拟浏览器的方式，用别人已经开发好的 OJ 系统来帮咱们判题。

比如使用 Puppeteer + 无头浏览器，把咱们系统用户提交的代码，像人一样输入到别人的 OJ 网页中，让程序点击提交按钮，并且等别人的 OJ 系统返回判题结果后，再把这个结果返回给我们自己的用户。

这种方式的缺点就是把核心流程交给了别人，如果别人服务挂了，你的服务也就挂了；而且别人 OJ 系统不支持的题目，可能你也支持不了。



### 前端项目初始化 | Vue-CLI 初始项目搭建

#### 确认环境

nodeJS 版本：v18.16.0 或 16

检测命令：

```shell
node -v
复制代码
```

切换和管理 node 版本的工具：https://github.com/nvm-sh/nvm

npm 版本：9.5.1

```shell
npm -v
复制代码
```

#### 初始化

使用 vue-cli 脚手架：https://cli.vuejs.org/zh/

安装脚手架工具：

```shell
npm install -g @vue/cli
```

检测是否安装成功：

```shell
vue -V
```

如果找不到命令，那么建议去重新到安装 npm，重新帮你配置环境变量。

创建项目：

```shell
vue create yuoj-frontend
```

运行项目，能运行就成功了

#### 前端工程化配置

脚手架已经帮我们配置了代码美化、自动校验、格式化插件等，无需再自行配置

但是需要在 webstorm 里开启代码美化插件：

![image.png](./assets/9313f6db-067b-45e8-afc1-cd953efee39b.png)

在 vue 文件中执行格式化快捷键，不报错，表示配置工程化成功

脚手架自动整合了 vue-router

#### 自己整合

代码规范：https://eslint.org/docs/latest/use/getting-started

代码美化：https://prettier.io/docs/en/install.html

直接整合：https://github.com/prettier/eslint-plugin-prettier#recommended-configuration（包括了 https://github.com/prettier/eslint-config-prettier#installation）



### 前端项目初始化 | 组件库引入

#### 引入组件

> Vue Router 路由组件已自动引入，无需再引入：https://router.vuejs.org/zh/introduction.html

组件库：https://arco.design/vue

快速上手：https://arco.design/vue/docs/start

执行安装：

```shell
npm install --save-dev @arco-design/web-vue
```

改变 main.ts：

```typescript
import { createApp } from "vue";
import App from "./App.vue";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import router from "./router";
import store from "./store";

createApp(App).use(ArcoVue).use(store).use(router).mount("#app");
```

引入一个组件，如果显示出来，就表示引入成功

#### 项目通用布局

新建一个布局， 在 app.vue 中引入

app.vue 代码如下：

```typescript
<div id="app">
  <BasicLayout />
</div>
复制代码
```

选用 arco design 的 layout 组件（https://arco.design/vue/component/layout）

先把上中下布局编排好，然后再填充内容：

![image.png](./assets/37dc8844-0572-4e3d-a50a-da13f6e6cc0c-1724646476709-1.png)

#### 实现通用路由菜单

菜单组件：https://arco.design/vue/component/menu

目标：根据路由配置信息，自动生成菜单内容。实现更通用、更自动的菜单配置。

步骤：

1）提取通用路由文件

2）菜单组件读取路由，动态渲染菜单项

3）绑定跳转事件

4）同步路由的更新到菜单项高亮

同步高亮原理：首先点击菜单项 => 触发点击事件，跳转更新路由 => 更新路由后，同步去更新菜单栏的高亮状态。

使用 Vue Router 的 afterEach 路由钩子实现：

```javascript
const router = useRouter();

// 默认主页
const selectedKeys = ref(["/"]);

// 路由跳转后，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});
复制代码
```

#### 全局状态管理

vuex：https://vuex.vuejs.org/zh/guide/（vue-cli 脚手架已自动引入）

什么是全局状态管理？

所有页面全局共享的变量，而不是局限在某一个页面中。

适合作为全局状态的数据：已登录用户信息（每个页面几乎都要用）

Vuex 的本质：给你提供了一套增删改查全局变量的 API，只不过可能多了一些功能（比如时间旅行）

![img](./assets/119402cc-a78c-4576-8c71-26609d7c9f8c.png)

可以直接参考购物车示例：https://github.com/vuejs/vuex/tree/main/examples/classic/shopping-cart

state：存储的状态信息，比如用户信息

mutation（尽量同步）：定义了对变量进行增删改（更新）的方法

actions（支持异步）：执行异步操作，并且触发 mutation 的更改（actions 调用 mutation）

modules（模块）：把一个大的 state（全局变量）划分为多个小模块，比如 user 专门存用户的状态信息

##### 实现

先在 store 目录下定义 user 模块，存储用户信息：

```typescript
// initial state
import { StoreOptions } from "vuex";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "未登录",
    },
  }),
  actions: {
    getLoginUser({ commit, state }, payload) {
      commit("updateUser", { userName: "鱼皮" });
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;

复制代码
```

然后在 store 目录下定义 index.ts 文件，导入 user 模块：

```typescript
import { createStore } from "vuex";
import user from "./user";

export default createStore({
  mutations: {},
  actions: {},
  modules: {
    user,
  },
});
```

在 Vue 页面中可以获取已存储的状态变量：

```tsx
const store = useStore();
store.state.user?.loginUser
```

在 Vue 页面中可以修改状态变量：

> 使用 dispatch 来调用之前定义好的 actions

```typescript
store.dispatch("user/getLoginUser", {
  userName: "鱼皮",
});
```

#### 全局权限管理

目标：能够直接以一套通用的机制，去定义哪个页面需要那些权限。而不用每个页面独立去判断权限，提高效率。

思路：

1. 在路由配置文件， 定义某个路由的访问权限
2. 在全局页面组件 app.vue 中，绑定一个全局路由监听。每次访问页面时，根据用户要访问页面的路由信息，先判断用户是否有对应的访问权限。
3. 如果有，跳转到原页面；如果没有，拦截或跳转到 401 鉴权或登录页

示例代码如下：

```typescript
const router = useRouter();
const store = useStore();

router.beforeEach((to, from, next) => {
  // 仅管理员可见，判断当前用户是否有权限
  if (to.meta?.access === "canAdmin") {
    if (store.state.user.loginUser?.role !== "admin") {
      next("/noAuth");
      return;
    }
  }
  next();
});
```

#### 优化页面布局

1、底部 footer 布局优化

2、优化 content、globalHeader 的样式

3、优化导航栏用户名称的换行

##### 通用导航栏组件 - 根据配置控制菜单的显隐

1）routes.ts 给路由新增一个标志位，用于判断路由是否显隐

```javascript
  {
    path: "/hide",
    name: "隐藏页面",
    component: HomeView,
    meta: {
      hideInMenu: true,
    },
  },
```

2）不要用 v-for + v-if 去条件渲染元素，这样会先循环所有的元素，导致性能的浪费

推荐：先过滤只需要展示的元素数组

```typescript
// 展示在菜单的路由数组
const visibleRoutes = routes.filter((item, index) => {
  if (item.meta?.hideInMenu) {
    return false;
  }
  return true;
});
```

#### 根据权限隐藏菜单

需求：只有具有权限的菜单，才对用户可见

原理：类似上面的控制路由显示隐藏，只要判断用户没有这个权限，就直接过滤掉

1）新建 access 目录，专门用一个文件来定义权限

```typescript
/**
 * 权限定义
 */
const ACCESS_ENUM = {
  NOT_LOGIN: "notLogin",
  USER: "user",
  ADMIN: "admin",
};

export default ACCESS_ENUM;
```

2）定义一个公用的权限校验方法

为什么？因为菜单组件中要判断权限、权限拦截也要用到权限判断功能，所以抽离成公共方法

创建 checkAccess.ts 文件，专门定义检测权限的函数：

```typescript
import ACCESS_ENUM from "@/access/accessEnum";

/**
 * 检查权限（判断当前登录用户是否具有某个权限）
 * @param loginUser 当前登录用户
 * @param needAccess 需要有的权限
 * @return boolean 有无权限
 */
const checkAccess = (loginUser: any, needAccess = ACCESS_ENUM.NOT_LOGIN) => {
  // 获取当前登录用户具有的权限（如果没有 loginUser，则表示未登录）
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN;
  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  // 如果用户登录才能访问
  if (needAccess === ACCESS_ENUM.USER) {
    // 如果用户没登录，那么表示无权限
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
  }
  // 如果需要管理员权限
  if (needAccess === ACCESS_ENUM.ADMIN) {
    // 如果不为管理员，表示无权限
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false;
    }
  }
  return true;
};

export default checkAccess;
```

3）修改 GlobalHeader 动态菜单组件，根据权限来过滤菜单

注意，这里使用计算属性，是为了当登录用户信息发生变更时，触发菜单栏的重新渲染，展示新增权限的菜单项

```typescript
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});
```

#### 全局项目入口

app.vue 中预留一个可以编写全局初始化逻辑的代码：

```typescript
/**
 * 全局初始化函数，有全局单次调用的代码，都可以写到这里
 */
const doInit = () => {
  console.log("hello 欢迎来到我的项目");
};

onMounted(() => {
  doInit();
});
```

1. 前端项目初始化 | 项目通用布局开发及优化
2. 前端项目初始化 | 全局状态管理
3. 前端项目初始化 | 全局权限管理
4. 前端项目初始化 | 通用菜单组件开发1792126029868675074_0.4013141990248197
5. 前端项目初始化 | 全局项目入口
6. 前端项目初始化 | 多套布局支持
7. 后端项目初始化（Spring Boot 万用模板讲解）1792126029868675074_0.47910861742417676
8. 前后端联调 | 用户登录页面开发
9. 前后端联调 | 前端请求代码生成
10. 前后端联调 | 用户自动登录1792126029868675074_0.6937294814307828

## 第二章：单体项目开发

1. 后端接口开发 | 库表设计
2. 后端接口开发 | 数据库索引知识
3. 后端接口开发 | 后端开发流程讲解1792126029868675074_0.5919477918290763
4. 后端接口开发 | 代码自动生成
5. 后端接口开发 | 开发题目相关接口
6. 前端页面开发 | 整合 Markdown 编辑器1792126029868675074_0.9040804717327677
7. 前端页面开发 | 整合 Monaco Editor 代码编辑器
8. 前端页面开发 | 题目页面开发（自定义代码模板）
9. 前端页面开发 | 题目管理页面开发1792126029868675074_0.6141363297356519
10. 前端页面开发 | 题目更新页面开发
11. 前端页面开发 | 题目列表搜索页面开发
12. 前端页面开发 | 在线做题页面开发1792126029868675074_0.21130696512741376
13. 判题机架构 | 判题机模块划分
14. 判题机架构 | 判题服务开发
15. 判题机架构 | 工厂模式优化1792126029868675074_0.4217116538586747
16. 判题机架构 | 代理模式优化
17. 判题机架构 | 策略模式优化

## 第三章：代码沙箱实现

1. 代码沙箱 Java 原生实现 | 执行原理
2. 代码沙箱 Java 原生实现 | 核心流程开发
3. 代码沙箱 Java 原生实现 | Java 程序漏洞讲解（6 种）1792126029868675074_0.5884965412749805
4. Java 程序安全控制 | 超时控制
5. Java 程序安全控制 | 资源控制
6. Java 程序安全控制 | 权限控制1792126029868675074_0.052430622393497606
7. Java 程序安全控制 | 安全管理器
8. Java 程序安全控制 | 环境隔离
9. Docker 从入门到实战 | Docker 入门讲解1792126029868675074_0.6615329024240846
10. Docker 从入门到实战 | 虚拟机 + 远程开发环境搭建
11. Docker 从入门到实战 | Docker 命令实操
12. Docker 从入门到实战 | Java 操作 Docker1792126029868675074_0.22564454281685564
13. 代码沙箱 Docker 实现 | 核心流程实现
14. 代码沙箱 Docker 实现 | Docker 容器安全性
15. 代码沙箱优化 | 模板方法模式1792126029868675074_0.3642563585422103
16. 代码沙箱开放 API（API 安全性）
17. 1792126029868675074_0.25354232083944694

### 第四章：项目微服务化

1. 微服务入门 | 基本概念
2. 微服务入门 | 微服务实现技术
3. 微服务入门 | Spring Cloud Alibaba 入门1792126029868675074_0.9738830200070288
4. 微服务改造 | Redis Session 分布式登录
5. 微服务改造 | 服务划分
6. 微服务改造 | 路由划分1792126029868675074_0.04387696949488884
7. 微服务改造 | Nacos 注册中心
8. 微服务改造 | Maven 子父工程生成
9. 微服务改造 | 代码依赖同步1792126029868675074_0.4807395383143567
10. 微服务改造 | Open Feign 服务内部调用
11. Gateway 微服务网关 | 接口路由
12. Gateway 微服务网关 | 聚合文档1792126029868675074_0.49257791703635956
13. Gateway 微服务网关 | 跨域解决
14. Gateway 微服务网关 | 权限校验
15. 消息队列解耦 | RabbitMQ 项目异步化改造