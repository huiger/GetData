package top.huiger.AndroidApk;

/**
 * Author : huiGer
 * Time   : 2018/8/27 0027 下午 03:41.
 * Desc   :
 */
public class DataBean {

    /**
     * 版本号
     */
    private String versionName;
    /**
     * 评分
     */
    private double startCount;

    /**
     * 累计下载次数
     */
    private String downloadNum;

    /**
     * 下载地址
     */
    private String downloadUrl;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public double getStartCount() {
        return startCount;
    }

    public void setStartCount(double startCount) {
        this.startCount = startCount;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
