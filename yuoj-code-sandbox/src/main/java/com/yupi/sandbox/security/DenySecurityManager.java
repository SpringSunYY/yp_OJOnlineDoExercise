package com.yupi.sandbox.security;

import java.security.Permission;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox.security
 * @Author: YY
 * @CreateTime: 2024-09-15  16:06
 * @Description: DenySecurityManager 禁用所有权限安全管理器
 * @Version: 1.0
 */
public class DenySecurityManager extends SecurityManager {
    // 检查所有的权限
    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限异常：" + perm.toString());
    }
}
