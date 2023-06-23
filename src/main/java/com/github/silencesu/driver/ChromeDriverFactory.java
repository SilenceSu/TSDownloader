package com.github.silencesu.driver;

import com.github.silencesu.Option;
import lombok.Builder;
import org.apache.logging.log4j.util.Strings;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class ChromeDriverFactory {


    //不开启浏览器窗口
    private boolean openWindows = false;


    @Builder
    public ChromeDriverFactory(boolean openWindows) {
        this.openWindows = openWindows;
    }


    public ChromeDriver instance(){

        ChromeDriverService service = new ChromeDriverService.Builder()
                .withLogOutput(System.out)
                .build();

        ChromeOptions options = new ChromeOptions();


        if (!Strings.isBlank(Option.proxyAdd)) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(Option.proxyAdd)
                    .setFtpProxy(Option.proxyAdd)
                    .setSslProxy(Option.proxyAdd);
            options.setCapability(CapabilityType.PROXY, proxy);

        }

        //默认设置


        // 添加 --incognito 参数
        options.addArguments("--incognito");


        if (!openWindows) {
        options.addArguments("headless");
        }



        return new ChromeDriver(service,options);

    }






}
