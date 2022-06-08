import Entity.StaffEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) {
        try {
//            Eprint eprint = new Eprint();
//            ConfigEntity configEntity = new ConfigEntity();
//            configEntity.setPrinterName("hp laserjet pro mfp m225-m226 pcl 6");
//            eprint.print("D:\\program\\print\\done\\01-09-54-36.pdf", configEntity);

            StaffEntity staffEntity = new StaffEntity();
            staffEntity.setName(" 林志强");
            staffEntity.setCard(" 351127");
            staffEntity.setCertificate(" 350321199402183911");
            staffEntity.setCompany(" 深圳蜂格科技有限公司");
            staffEntity.setDate(" 2022年6月1日至2022年6月1日");
            staffEntity.setPicturePath(" D:\\program\\print\\test\\resources\\picture.png");

            String cmd = staffEntity.getName() + staffEntity.getCompany() + staffEntity.getDate() + staffEntity.getCertificate() + staffEntity.getCard() + staffEntity.getPicturePath();
            Process pro = Runtime.getRuntime().exec("cmd /c java -jar C:\\software\\print\\print.jar " + cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream())); //虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。
            String msg = null;
            while ((msg = br.readLine()) != null) {
                System.out.println(msg);
            }
//
//
        } catch (Exception exception) {
        }
    }
}
