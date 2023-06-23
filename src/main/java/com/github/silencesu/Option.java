package com.github.silencesu;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;

/**
 * 整体配置
 */
@Getter
@Setter
@CommandLine.Command(name = "optin",mixinStandardHelpOptions = false,helpCommand = false)
public class Option {

    /**
     * 下载目录
     */
    @CommandLine.Option(names = "-o",description = "下载目录",required = true)
    public static   String downloadPath;

    @CommandLine.Option(names = "-url", description = "页面url", required = true)
    public static String pageUrl;


    /**
     * 代理配置
     */
    @CommandLine.Option(names = "-proxy",description = "使用代理 ip:port")
    public  static String proxyAdd;

}
