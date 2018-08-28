package top.huiger.AndroidApk;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : huiGer
 * Time   : 2018/8/27 0027 上午 09:49.
 * Desc   :
 */
public class Utils {

    public static Map<String, DataBean> dataMap = new HashMap<>();

    public static void welCome() {
        System.out.println("******************欢迎使用市场数据抓取工具******************");
    }

    public static void pleaseInput() {
        System.out.println("请输入要查询的app名称");
        System.out.println("输入\"0\"退出程序");
        System.out.print("请输入: ");
    }


    public static void print(String appName) {
        System.out.println("\n\n┌─────────────────────────────────┐"
                + "\n│\t查询到『" + appName + "』相关数据:");

        if (!dataMap.isEmpty()) {
            for (String key : dataMap.keySet()) {
                DataBean dataBean = dataMap.get(key);
                System.out.println("├─────────────────────────────────┤"
                        + "\n│\t来自" + key
                        + "\n│\t\t" + dataBean.getVersionName()
                        + "\n│\t\t" + dataBean.getDownloadNum()
                        + "\n│\t\t评分: " + dataBean.getStartCount() + "星"
                        + "\n│\t\t下载地址: " + dataBean.getDownloadUrl()
                );
            }
        }else{
            System.out.println("├─────────────────────────────────┤"
                    + "\n│\t\t搞到个毛线");
        }
        System.out.println("└─────────────────────────────────┘\n\n");
    }


    public static void bye() {
        System.out.println("谢谢使用~");
    }

}
