package top.huiger.AndroidApk;

import java.io.IOException;
import java.util.Scanner;

/**
 * Author : huiGer
 * Time   : 2018/8/27 0027 上午 09:53.
 * Desc   : 程序入口
 */
public class main {

    private static boolean flag = true;
    private static GetPutAwayInfo getPutAwayInfo;
    private static Scanner scanner;
    // 要获取数据数量
    private static int sum = 2;
    // 当前执行了几个
    private static int current = 0;
    private static String keyword;

    public static void main(String[] s) {

        Utils.welCome();
        getPutAwayInfo = new GetPutAwayInfo();
        scanner = new Scanner(System.in);
        go();
    }

    private static void go() {
        try {
            while (flag) {
                Utils.pleaseInput();
                keyword = scanner.nextLine().trim();
                if (keyword.equals("0")) {
                    flag = false;
                    Utils.bye();
                    scanner.close();
                    return;
                }
                getData(keyword, new GetDataResult() {
                    @Override
                    public void onResult(boolean flag) {
                        //        全部抓取完成
                        if (sum == current){
                            Utils.print(keyword);
                            current = 0;
                            Utils.dataMap.clear();
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("输入有误! ");
            go();
        }
    }

    private static void getData(String keyword, GetDataResult result) throws IOException {
        // 安智市场
        getPutAwayInfo.getAnzhiData(keyword, new GetDataResult() {
            @Override
            public void onResult(boolean flag) {
                current++;
                result.onResult(flag);
            }
        });
        // 百度应用市场
        getPutAwayInfo.getBaiduData(keyword, new GetDataResult() {
            @Override
            public void onResult(boolean flag) {
                current++;
                result.onResult(flag);
            }
        });


    }


}
