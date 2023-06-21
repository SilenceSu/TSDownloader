package org.example.driver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class ChromeDriverFactory {


    private boolean openWindows = false;






    public static ChromeDriver get(){
        ChromeOptions options = new ChromeOptions();
        String proxyAddress = "10.42.0.66:7890";
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress)
                .setFtpProxy(proxyAddress)
                .setSslProxy(proxyAddress);

        options.setCapability(CapabilityType.PROXY, proxy);

        //不开启浏览器窗口
        options.addArguments("headless");
        return new ChromeDriver(options);


    }
}
