package com.fox.util;

import com.fox.Application;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

/**
 * @author 离狐千慕
 * @version 1.0
 * @date 2023/5/19 9:34
 */
public class SystemUtil {
    private static final OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static DecimalFormat format = new DecimalFormat("00.00");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    //public static final String FILE_SEPARATOR = File.separator;

    /**
     * 获取系统真实路径
     * @param path
     * @return
     */
    public static String getRealFilePath(String path) {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    public static String getHttpURLPath(String path) {
        return path.replace("\\", "/");
    }
    /**
     * 获取系统运行路径
     */
    public static String absolutePath(){
        // 创建ApplicationHome对象
        ApplicationHome home = new ApplicationHome(Application.class);
        // 获取项目根路径
        String absolutePath = home.getDir().getAbsolutePath();
//        编译环境下，回退
        absolutePath=absolutePath.endsWith("\\classes")?absolutePath.replace("\\classes",""):absolutePath;
        absolutePath=absolutePath.endsWith("\\target")?absolutePath.replace("\\target",""):absolutePath;
        absolutePath=absolutePath.endsWith("\\")?absolutePath:absolutePath+"\\";
        return absolutePath;
    }
    /**
     * 获取cpu负载
     */
    public static String getCPURate(){
        double processCpuLoad = osmxb.getProcessCpuLoad();
        processCpuLoad=processCpuLoad*100;
        return format.format(processCpuLoad)+"%";
    }
    /**
     * 获取操作系统的架构基本信息
     */
    public static String getBaseInfo(){
        StringBuilder builder=new StringBuilder();
        builder.append("操作系统名称:");
        builder.append(System.getProperty("os.name"));
        builder.append(",操作系统版本:").append(System.getProperty("os.version"));
        builder.append(",架构:");
        builder.append(System.getProperty("os.arch"));
        builder.append(",核心数:");
        builder.append(Runtime.getRuntime().availableProcessors());
        return builder.toString();
    }
    /**
     * 获取本机IP
     */
    public static String hostIP() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            //获取本机ip
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            return "找不到主机IP";
        }
    }

    /**
     * 获取本机物理内存比
     * @return /
     */
    public static String phyMemory(){
        Double totalPhysicalMemorySize = (double) (osmxb.getTotalPhysicalMemorySize() / 1024);
        Double freePhysicalMemorySize = (double) (osmxb.getFreePhysicalMemorySize()/1024);
        return format.format((freePhysicalMemorySize/totalPhysicalMemorySize)*100)+"%";
    }
    /**
     * 获取JVM内存比
     */
    public static String jvmMemory(){
        long vmFree = 0;
        long vmUse = 0;
        long vmTotal = 0;
        long vmMax = 0;
        int byteToMb = 1024 * 1024;
        Runtime rt = Runtime.getRuntime();
        vmTotal = rt.totalMemory() / byteToMb;
        vmFree = rt.freeMemory() / byteToMb;
        vmMax = rt.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        return format.format(vmUse*100/vmTotal)+"%";
    }
    /**
     * 获取系统的运行目录
     */
    public static String getFolderPath() {
        try {
            ClassPathResource resource = new ClassPathResource("");
            String folderPath = resource.getFile().getAbsolutePath();
            int i = folderPath.lastIndexOf(FILE_SEPARATOR);
            folderPath=folderPath.substring(0,i);
            i = folderPath.lastIndexOf(FILE_SEPARATOR);
            folderPath=folderPath.substring(0,i+1);
            return folderPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getForeignFolder(String projectPath){
        if (projectPath==null){
            return getFolderPath();
        }
        projectPath=projectPath.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
//        已经带前缀了，给去掉
        if (projectPath.startsWith(FILE_SEPARATOR)){
            projectPath=projectPath.substring(1);
        }
        return getFolderPath()+projectPath;
    }

    public static void main(String[] args) {
        System.out.println(getFolderPath());
        System.out.println(getForeignFolder("/genImgs/driver/chromedriver.exe"));
//        E:\FactorProject\qianmo-master\genImgs\driver\chromedriver.exe
//        String x = folderPath.replaceAll(FILE_SEPARATOR, "/");
//        System.out.println(x);
    }
}
