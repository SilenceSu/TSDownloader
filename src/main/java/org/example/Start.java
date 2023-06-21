package org.example;

import org.example.download.DownloadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class Start {
    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    private static final int BOOK_ID_MIN = 100997;
    private static final int BOOK_ID_MAX = 147879;

    private static final int BOOK_ID = 19508;

    private static Map<Integer, Future<Boolean>> task = new ConcurrentHashMap<>();


    public static void main(String[] args) {
//        if (args.length < 3) {
//            logger.error("参数不够");
//            return;
//        }
        logger.info("准备解析参数");




        logger.info("准备开始执行任务");


        int startid = 905;
        int downNum = 95;
        String outPutDir = "C:\\Users\\silence\\zhen\\";



        ExecutorService service = Executors.newFixedThreadPool(10);


        for (int i = startid; i < startid + downNum; i++) {
            String pageUrl = pageUrl(i);
            Future<Boolean> submit = service.submit(new DownloadTask(i, pageUrl, outPutDir));
            task.put(i, submit);
        }


        /**
         * 主线程中红检查任务是否结束
         */
        while (true){

            List<Integer> successIds = new ArrayList<>();
            for (Map.Entry<Integer, Future<Boolean>> entry : task.entrySet()) {
                Future<Boolean> value = entry.getValue();
                if (!value.isDone()) {
                    continue;
                }

                try {
                    Integer key = entry.getKey();
                    //成功
                    if (value.get()) {
                        successIds.add(key);
                    }else {
                        //失败
                        String pageUrl = pageUrl(key);
                        Future<Boolean> submit = service.submit(new DownloadTask(key, pageUrl, outPutDir));
                        task.put(key, submit);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }


            }
            for (Integer id : successIds) {
                task.remove(id);
            }

            if (task.size() == 0) {
                logger.info("全部任务消耗完毕，成功");
                service.shutdown();
                return;
            }

            //5s检测一次

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.error("-------------------------------------");

            logger.error("当前还有执行中的任务：{}", task.size());

            logger.error("-------------------------------------");

        }



    }


    /**
     * 根据id章节获取url
     *
     * @param id 开始id 100997  结束id 147879
     *           开始https://www.ting13.com/play/19508_1_100997.html
     *           结束
     * @return
     */
    private static String pageUrl(int id) {

        String url = "https://www.ting13.com/play/19508_1_100997.html";
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.ting13.com/play/");


        int playId = BOOK_ID_MIN + id - 1;

        return String.format("https://www.ting13.com/play/%d_1_%d.html", BOOK_ID, playId);

    }
}
