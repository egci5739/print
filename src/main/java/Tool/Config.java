package Tool;

import Entity.ConfigEntity;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class Config {
    private final Logger logger = LoggerFactory.getLogger(Config.class);

    /*
     * 加载配置文件
     * */
    public ConfigEntity getConfig() {
        try {
            ConfigEntity configEntity = new ConfigEntity();
            //获取根目录信息
            String rootPath = getRootPath();
            configEntity.setRootPath(rootPath);
            //加载logback.xml
            load(configEntity.getRootPath() + "\\resources\\logback.xml");
            logger.info("根目录：" + rootPath);
            //加载配置文件
            InputStream in = new BufferedInputStream(new FileInputStream(configEntity.getRootPath() + "\\resources\\config.txt"));
            Properties p = new Properties();
            p.load(in);
            configEntity.setNote1(new String(p.getProperty("note1").getBytes("ISO-8859-1"), "UTF-8"));
            configEntity.setNote2(new String(p.getProperty("note2").getBytes("ISO-8859-1"), "UTF-8"));
            configEntity.setNote3(new String(p.getProperty("note3").getBytes("ISO-8859-1"), "UTF-8"));
            configEntity.setPrinterName(new String(p.getProperty("printerName").getBytes("ISO-8859-1"), "UTF-8"));
            configEntity.setsFont(Float.parseFloat(new String(p.getProperty("sFont").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setbFont(Float.parseFloat(new String(p.getProperty("bFont").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setOrientation(Integer.parseInt(new String(p.getProperty("orientation").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setWidth(Integer.parseInt(new String(p.getProperty("width").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setHeight(Integer.parseInt(new String(p.getProperty("height").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setMarginLeft(Integer.parseInt(new String(p.getProperty("marginLeft").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setMarginRight(Integer.parseInt(new String(p.getProperty("marginRight").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setMarginTop(Integer.parseInt(new String(p.getProperty("marginTop").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setMarginBottom(Integer.parseInt(new String(p.getProperty("marginBottom").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setCopy(Integer.parseInt(new String(p.getProperty("copy").getBytes("ISO-8859-1"), "UTF-8")));
            configEntity.setStatus(Integer.parseInt(new String(p.getProperty("status").getBytes("ISO-8859-1"), "UTF-8")));
            logger.info("配置文件信息:" + configEntity.toString());
            return configEntity;
        } catch (Exception e) {
            logger.error("加载配置文件错误", e);
            return null;
        }
    }

    /*
     * 获取根目录
     * */
    private String getRootPath() {
        URL url = Config.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");//转化为utf-8编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar")) {//可执行jar包运行的结果里包含".jar"
            //截取路径中的jar包名
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        File file = new File(filePath);
        filePath = file.getAbsolutePath();//得到windows下的正确路径
        return filePath;
    }

    /*
     * 设置logback路径
     * */
    public void load(String path) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            configurator.doConfigure(fileInputStream);
            StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoranException e) {
            e.printStackTrace();
        }
    }

}
