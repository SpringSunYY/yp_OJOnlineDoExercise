package com.yupi.sandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.yupi.sandbox.model.ExecuteMessage;

import java.io.*;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox.utils
 * @Author: YY
 * @CreateTime: 2024-09-12  22:27
 * @Description: ProcessUtils 进程工具类
 * @Version: 1.0
 */
public class ProcessUtils {
    /**
     * @description: 执行进程并获取信息
     * @author: YY
     * @method: runProcessAndGetMessage
     * @date: 2024/9/12 22:36
     * @param:
     * @param: runProcess
     * @return: com.yupi.sandbox.model.ExecuteMessage
     **/
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            //正常退出
            if (exitValue == 0) {
                System.out.println(opName + ",编译成功:" + exitValue);
                //正常输出流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                //逐行读取
                StringBuffer compileOutputStringBuilder = new StringBuffer();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
            } else {
                System.out.println(opName + ",编译失败，错误码：" + exitValue);
                //错误输出流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                //逐行读取
                StringBuffer compileOutputStringBuilder = new StringBuffer();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
                //逐行读取
                StringBuffer errorCompileOutputStringBuilder = new StringBuffer();
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = bufferedReader.readLine()) != null) {
                    errorCompileOutputStringBuilder.append(errorCompileOutputLine);
                }
                executeMessage.setErrorMessage(errorCompileOutputStringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }

    /**
     * @description: 执行交互式进程并获取信息
     * @author: YY
     * @method: runInteractProcessAndGetMessage
     * @date: 2024/9/12 23:04
     * @param:
     * @param: runProcess
     * @param: opName
     * @return: com.yupi.sandbox.model.ExecuteMessage
     **/
    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            // 向控制台输入程序
            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] s = args.split(" ");
            String join = StrUtil.join("\n", s) + "\n";
            outputStreamWriter.write(join);
            // 相当于按了回车，执行输入的发送
            outputStreamWriter.flush();

            // 分批获取进程的正常输出
            InputStream inputStream = runProcess.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            // 逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            // 记得资源的释放，否则会卡死
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            runProcess.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }
}
