import Entity.ConfigEntity;
import Entity.StaffEntity;
import Tool.Config;
import Tool.Epdf;
import Tool.Eprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class Egci {
    private static final Logger logger = LoggerFactory.getLogger(Epdf.class);

    public static void main(String[] args) {
        //加载配置信息
        Config config = new Config();
        ConfigEntity configEntity = config.getConfig();
        if (configEntity == null) {
            logger.info("加载配置信息错误");
            return;
        }
        //创建done文件夹
        File folder = new File(configEntity.getRootPath() + "\\done");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
            logger.info("创建done文件夹");
        }
        /*
         * 0-姓名
         * 1-公司
         * 2-期限
         * 3-证件
         * 4-卡号
         * 5-图片路径
         * */
        //获取参数
        logger.info("param:" + Arrays.toString(args));
        StaffEntity staffEntity = new StaffEntity();
        staffEntity.setName(args[0]);
        staffEntity.setCompany(args[1]);
        staffEntity.setDate(args[2]);
        staffEntity.setCertificate(args[3]);
        staffEntity.setCard(args[4]);
        staffEntity.setPicturePath(args[5]);
        //生成pdf
        Epdf epdf = new Epdf();
        String pdfPath = epdf.createPdf(configEntity, staffEntity);
        if (Objects.equals(pdfPath, "error")) {
            logger.info("生成pdf错误");
            return;
        }
        //打印pdf
        Eprint eprint = new Eprint();
        eprint.print(pdfPath, configEntity);
    }
}

