package org.example.download;

import org.example.driver.ChromeDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class DownloadTask implements Callable<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(DownloadTask.class);

    private int taskid;
    private ChromeDriver webDriver;
    private String url;

    private String outPutDir;


    public DownloadTask(int taskid, String url,String outPutDir) {
        webDriver = ChromeDriverFactory.get();
        this.taskid = taskid;
        this.url = url;
        this.outPutDir = outPutDir;
    }

    @Override
    public Boolean call() throws Exception {
        return startJob();
    }

    public boolean startJob() {
        try {
            Thread.sleep(new Random().nextInt(1000,5000));

            logger.info("任务：{},正在打开页面：{}", taskid, url);

            webDriver.get(url);

            logger.info("任务：{},打开页面：{}", taskid, url);


            DownloadInfo info = new DownloadInfo();



            int num = 0;

            while (!info.isFull()) {

                num++;

                logger.info("任务:{},准备获取页面元素：", taskid);

                //装载信息

                putInfo(info, webDriver);

                logger.info("任务:{},准备获取页面元素：num:{},name:{},url:{}", taskid, num, info.getName(), info.getMp3Url());


                if (num > 2) {
                    logger.error("任务：{},元素获取失败", taskid);
                    return false;
                }

                //休息2秒在找找
                try {

                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }


            String output = outPutDir + info.getName();

            return downMp3(output, info.getMp3Url());


        } catch (Exception e) {

            logger.error("报错了", e);
            return false;

        } finally {

            webDriver.quit();
            logger.info("关闭窗口:{}", taskid);


        }


    }


    private void putInfo(DownloadInfo info, ChromeDriver webDriver){

        String name = findNameByEle(webDriver);

       String mp4url = findUrlByEle(webDriver);

        info.setName(name);
        info.setMp3Url(mp4url);


    }

    private String findNameByEle(WebDriver webDriver) {
        WebElement element = new WebDriverWait(webDriver, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.className("p-h")));

        return element.getText().replace("正在播放：", "");
    }

    private String findUrlByEle(WebDriver webDriver) {
//        WebElement element = webDriver.findElement(By.id("jp_audio_0"));

        //等待
        WebElement element = new WebDriverWait(webDriver, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.id("jp_audio_0")));


        String mp4url = element.getAttribute("src");

        return mp4url;
    }

    private boolean downMp3(String output, String resUrl) {
        logger.info("任务:{},准备执行下载命令", taskid);

        ProcessBuilder pb = new ProcessBuilder("curl", "-o", output, resUrl);
        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                logger.info("任务:{},File downloaded successfully.", taskid);
                return true;
            } else {
                logger.info("任务:{},File download failed. errorCode:{},{}", taskid, exitCode, resUrl);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


}
