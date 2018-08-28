package top.huiger.Test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;

public class Main {


    private static final String userAgent = "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)";
    private static int conetntSize = 0;

    public static void main(String[] args) throws IOException {

//        text();
//        getBlog("https://diygod.me/");
        getBlog("http://blog.huiger.top");
//        getDianPing();

//        getZhiHu();


    }

    private static void getBlog(String url) throws IOException {

//        #posts > article:nth-child(1) > div > header > h1 > a

        Document document = Jsoup.connect(url)
                .userAgent(userAgent)
                .get();

//        #content > nav

        getBlogContent(document);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(300);
                    Elements elements = document.getElementById("content").select("nav").select("a");
                    for (Element element : elements) {
                        String nextLink =  element.getElementsByClass("extend next").attr("abs:href");
                        if (!nextLink.isEmpty()){
                            getBlog(nextLink);
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.run();

    }

    /**
     * 文章详细
     * @param document
     */
    private static void getBlogContent(Document document) {

        Element posts = document.getElementById("posts");

//#posts > article:nth-child(1) > div
//#posts > article:nth-child(2) > div > header > h1 > a
//#posts > article:nth-child(3) > div > header > h1 > a
        for (Element element : posts.getElementsByTag("article")) {
            Elements select1 = element
                    .select("div")
                    .select("header")
                    .select("h1")
                    .select("a");
            String titlt = select1.text();

            String link = select1.attr("abs:href");
            conetntSize++;
            System.out.println("第"+conetntSize+"篇文章\n名称: 《"+titlt
            +"》\n链接: "+link
            +"\n"
            );
        }
    }

    /**
     * 抓取知乎数据
     */
    private static void getZhiHu() throws IOException {
        String url = "https://www.zhihu.com/explore/recommendations";
        Document document = Jsoup.connect(url)
                .userAgent(userAgent)
                .get();

        Element main = document.getElementById("zh-recommend-list-full");
//        #zh-recommend-list-full > div > div:nth-child(1) > h2
//        #zh-recommend-list-full > div > div:nth-child(1)

        Elements div = main.select("div").select("div:nth-child(1)").select("h2");

        for (Element element : div) {
            String answerTitle = element.getElementsByTag("h2").text();
            String link = element.getElementsByTag("a").attr("abs:href");
            System.out.println("问题: " + answerTitle
                    + "\n链接地址: " + link
                    + "\n");
        }

    }

    /**
     * 点评数据
     */
    private static void getDianPing() {
        Document document = null;
        String url = "http://www.dianping.com/search/category/2/45";
        try {
            document = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取场馆的数据
        Element elementDiv = document.getElementById("shop-all-list");
        Elements elementsUl = elementDiv.getElementsByTag("ul");
        Elements elements = elementsUl.first().getElementsByTag("li");
        for (Element element : elements) {
            Elements elements1 = element.children();
            String targetUrl = elements1.get(0).getElementsByTag("a").attr("href");

            String img = elements1.get(0).getElementsByTag("img").first().attr("data-src");
            if (img.contains(".jpg")) {
                int a = img.indexOf(".jpg");
                img = img.substring(0, a + 4);
            }

            String radiumName = elements1.get(1).child(0).getElementsByTag("h4").text();
            String address0 = elements1.get(1).child(2).getElementsByTag("a").get(1).text();

            String address1 = elements1.get(1).child(2).getElementsByClass("addr").text();

            System.out.println("img=\t" + img);
            System.out.println("radiumName=\t" + radiumName);
            System.out.println("address=\t" + address0 + " " + address1);
        }
    }

    /**
     * 抓取文章名称
     */
    private static void text() {
        Document document = null;
        try {
            document = Jsoup.connect("http://blog.huiger.top")
                    .userAgent("Mozilla")
                    .get();
            Element content = document.getElementById("content");
            Elements elements = content.getElementById("posts").children();

            for (Element element : elements) {
                Elements children = element.children();
                String title = children.first().getElementsByTag("header").first().getElementsByTag("h1").first().getElementsByTag("a").text();
                System.out.println(title);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

