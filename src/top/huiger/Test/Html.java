package top.huiger.Test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Administrator on 2018/8/25 0025.
 */
public class Html {

    /**
     * 根据网址返回网页内容
     * @param url
     * @return
     */
    public Document getHtmlTextByUrl(String url) {
        Document document = null;

        try {
            int i = (int) (Math.random() * 1000);// 随机延时
            while (i != 0) {
                i--;
            }

            document = Jsoup.connect(url).data("query", "java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(300000).post();

        } catch (IOException e) {
            e.printStackTrace();
            try {
                document = Jsoup.connect(url).timeout(5000000).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return document;
    }



}
