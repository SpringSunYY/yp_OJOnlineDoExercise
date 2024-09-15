package com.yupi.sandbox.security;

import cn.hutool.core.io.FileUtil;

import java.nio.charset.Charset;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox.security
 * @Author: YY
 * @CreateTime: 2024-09-15  16:08
 * @Description: TestSecurityManager 测试安全管理器
 * @Version: 1.0
 */
public class TestSecurityManager {

    public static void main(String[] args) {
        System.setSecurityManager(new MySecurityManager());
        FileUtil.writeString("aa", "aaa", Charset.defaultCharset());
    }
}