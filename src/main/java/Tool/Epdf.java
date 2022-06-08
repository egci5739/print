package Tool;

import Entity.ConfigEntity;
import Entity.StaffEntity;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Epdf {
    private final Logger logger = LoggerFactory.getLogger(Epdf.class);

    /*
     * 生成PDF文件
     * */
    public String createPdf(ConfigEntity configEntity, StaffEntity staffEntity) {
        try {
            //模板路径
            String pdfTemplatePath = configEntity.getRootPath() + "\\resources\\template.pdf";
            //目标路径
            String createPath = configEntity.getRootPath() + "\\done\\";
            PdfReader reader = new PdfReader(pdfTemplatePath);
            String time = new SimpleDateFormat("dd-HH-mm-ss").format(new Date());
            // 创建生成报告名称
            File stampFile = new File(createPath, time + ".pdf");
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(stampFile));
            // 取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            //form.addSubstitutionFont(bf);
            setInfo(form, staffEntity, configEntity);
            //设置logo
            setImage(configEntity.getRootPath() + "\\resources\\logo.png", "logo", form, stamp);
            //设置人员图片
            setImage(staffEntity.getPicturePath(), "picture", form, stamp);
            //这里true表示pdf不可编辑
            stamp.setFormFlattening(true);
            stamp.close();
            reader.close();
            return configEntity.getRootPath() + "\\done\\" + time + ".pdf";//路径
        } catch (Exception e) {
            logger.error("生成PDF错误", e);
            return "error";
        }
    }

    /*
     * 设置人员信息
     * */
    private void setInfo(AcroFields form, StaffEntity staffEntity, ConfigEntity configEntity) throws DocumentException, IOException {
        String fontPath = configEntity.getRootPath() + "\\resources\\simsun.ttc,1";//字体路径
        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//设置字体
//        form.addSubstitutionFont(baseFont);
        //姓名
        form.setFieldProperty("name", "textfont", baseFont, null);
        form.setFieldProperty("name", "textsize", configEntity.getbFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("name", "姓名 " + staffEntity.getName());
        //公司
        form.setFieldProperty("company", "textfont", baseFont, null);
        form.setFieldProperty("company", "textsize", configEntity.getbFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("company", "公司 " + staffEntity.getCompany());
        //期限
        form.setFieldProperty("date", "textfont", baseFont, null);
        form.setFieldProperty("date", "textsize", configEntity.getbFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("date", "期限 " + staffEntity.getDate());
        //证件
        form.setFieldProperty("certificate", "textfont", baseFont, null);
        form.setFieldProperty("certificate", "textsize", configEntity.getbFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("certificate", "证件 " + staffEntity.getCertificate());
        //卡号
        form.setFieldProperty("card", "textfont", baseFont, null);
        form.setFieldProperty("card", "textsize", configEntity.getbFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("card", staffEntity.getCard());
        //备注1
        form.setFieldProperty("note1", "textfont", baseFont, null);
        form.setFieldProperty("note1", "textsize", configEntity.getsFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("note1", configEntity.getNote1());
        //备注2
        form.setFieldProperty("note2", "textfont", baseFont, null);
        form.setFieldProperty("note2", "textsize", configEntity.getsFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("note2", configEntity.getNote2());
        //备注3
        form.setFieldProperty("note3", "textfont", baseFont, null);
        form.setFieldProperty("note3", "textsize", configEntity.getsFont(), null);
        baseFont.setSubset(true);//字体嵌入子集
        form.setField("note3", configEntity.getNote3());
    }

    /*
     * 设置人员图片
     * */
    private void setImage(String imageUrl, String param, AcroFields form, PdfStamper stamp) throws DocumentException, IOException {
        // 插入人员图片
        int pageNo = form.getFieldPositions(param).get(0).page;
        Rectangle signRect = form.getFieldPositions(param).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        // 图片路径，url或绝对路径都可
        Image image;
        image = Image.getInstance(imageUrl);
        // 获取操作的页面
        PdfContentByte under = stamp.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
    }
}