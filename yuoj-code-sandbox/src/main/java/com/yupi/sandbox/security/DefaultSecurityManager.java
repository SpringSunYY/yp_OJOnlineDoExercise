package com.yupi.sandbox.security;

import java.security.Permission;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox.security
 * @Author: YY
 * @CreateTime: 2024-09-15  15:57
 * @Description: DefaultSecurityManager
 * 默认安全管理器
 * @Version: 1.0
 */
public class DefaultSecurityManager extends SecurityManager {
    // 检查所有的权限
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何限制");
        System.out.println(perm);
//        super.checkPermission(perm);
//        throw new SecurityException("权限不足：" + perm.getActions());
    }
}
