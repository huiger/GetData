package top.huiger.AndroidApk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Author : huiGer
 * Time   : 2018/8/27 0027 上午 09:48.
 * Desc   : 获取上架信息
 */
public class GetPutAwayInfo {

    private static final String userAgent = "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)";

    /**
     * 获取安智市场数据
     */
    public void getAnzhiData(String appName, GetDataResult result) {
        try {
            Document document = Jsoup.connect("http://www.anzhi.com/search.php?keyword=" + appName)
                    .userAgent(userAgent)
                    .timeout(30000)
                    .get();

            Elements byClass = document.getElementsByClass("app_list border_three");
            if (!byClass.text().isEmpty()) {

                Elements ul = byClass.first().select("ul");
                for (Element element : ul) {
                    //body > div.content > div.content_left > div.app_list.border_three > ul > li:nth-child(1) > div.app_info > span > a
                    Elements elements = element.select("li:nth-child(1)").select("div.app_info");
                    String currName = elements.select("span").select("a").text();
                    if (currName.toLowerCase().equals(appName)) {
                        //                    版本号
                        //                    body > div.content > div.content_left > div.app_list.border_three > ul > li:nth-child(1) > div.app_info > div > span.app_version.l
                        String versionName = elements.select("div").select("span.app_version.l").text();
                        //                    下载次数
                        //                    body > div.content > div.content_left > div.app_list.border_three > ul > li:nth-child(1) > div.app_info > div > span.app_downnum.l
                        String downloadCount = elements.select("div").select("span.app_downnum.l").text();
                        //                    评分
                        //                    body > div.content > div.content_left > div.app_list.border_three > ul > li:nth-child(1) > div.app_info > div > span.app_star.l > span > span.stars.center
                        String startBg = element.select("div").select("span.app_star.l").select("span.stars.center").attr("style");
                        String[] split = startBg.split("-");
                        if (split.length == 2) {
                            startBg = "0";
                        } else {
                            startBg = split[2].replace("px;", "");
                        }
                        int start = Integer.parseInt(startBg) / 24;
                        //                    下载地址
                        //                    body > div.content > div.content_left > div.app_list.border_three > ul > li:nth-child(1) > div.app_down > a
                        String id = element.select("div.app_down").select("a").attr("onclick");
                        id = id.substring(id.indexOf("(") + 1, id.indexOf(")"));
                        String downloadUrl = "http://www.anzhi.com/dl_app.php?s=" + id + "&n=5";
                        DataBean bean = new DataBean();
                        bean.setVersionName(versionName);
                        bean.setStartCount(start);
                        bean.setDownloadNum(downloadCount);
                        bean.setDownloadUrl(downloadUrl);
                        Utils.dataMap.put("安智市场", bean);
                        result.onResult(true);
                        return;
                    } else {
                        result.onResult(false);
                    }
                }
            } else {
                result.onResult(false);
            }
        } catch (IOException e) {
            result.onResult(false);
        }
    }

    /**
     * 获取baidu应用市场数据
     */
    public void getBaiduData(String appName, GetDataResult result) {

        try {
            Document document = Jsoup.connect("https://shouji.baidu.com/s?wd=" + appName + "&data_type=app&f=header_app%40input&from=landing")
                    .userAgent(userAgent)
                    .get();
//       #doc > div.yui3-g > div > div > ul > li:nth-child(1) > div > div.info > div.top > a

            Elements base = document.getElementById("doc")
                    .select("div.yui3-g")
                    .select("div")
                    .select("div")
                    .select("ul")
                    .select("li:nth-child(1)")
                    .select("div")
                    .select("div.info")
                    .select("div.top")
                    .select("a");
            String currentName = base.text();
            if (currentName.toLowerCase().equals(appName)) {
                String link = base.attr("abs:href");
                Document document1 = Jsoup.connect(link)
                        .userAgent(userAgent)
                        .get();

//        #doc > div.yui3-g > div > div.app-intro > div > div.content-right
                Elements elements = document1.getElementById("doc")
                        .select("div.yui3-g")
                        .select("div")
                        .select("div.app-intro")
                        .select("div");
                // 版本号 #doc > div.yui3-g > div > div.app-intro > div > div.content-right > div.detail > span.version
                String versionName = elements
                        .select("div.content-right")
                        .select("div.detail")
                        .select("span.version").text();
                // 评分#doc > div.yui3-g > div > div.app-intro > div > div.content-right > div.app-feature > span.star-xbig > span
                double start = Double.parseDouble(elements
                        .select("div.content-right")
                        .select("div.app-feature")
                        .select("span.star-xbig")
                        .select("span").attr("style").replace("width:", "").replace("%", "")) / 100 * 5;
                // 下载次数#doc > div.yui3-g > div > div.app-intro > div > div.content-right > div.detail > span.download-num
                String downloadNum = elements
                        .select("div.content-right")
                        .select("div.detail")
                        .select("span.download-num").text();
                // 下载地址#doc > div.yui3-g > div > div.app-intro > div > div.area-download > a
                String downloadUrl = elements.select("div.area-download")
                        .select("a")
                        .attr("href");
                DataBean bean = new DataBean();
                bean.setVersionName(versionName);
                bean.setStartCount(start);
                bean.setDownloadNum(downloadNum);
                bean.setDownloadUrl(downloadUrl);
                Utils.dataMap.put("百度应用市场", bean);
                result.onResult(true);
            } else {
                result.onResult(false);
            }
        } catch (IOException e) {
            result.onResult(false);
        }
    }
}
