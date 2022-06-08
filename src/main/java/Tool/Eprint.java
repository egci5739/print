package Tool;

import Entity.ConfigEntity;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Eprint {
    private final Logger logger = LoggerFactory.getLogger(Eprint.class);

    /*
     * 寻找指定的打印机
     * */
    public PrintService lookupPrinter(String printerName) {
//        String printerName = "hp laserjet pro mfp m225-m226 pcl 6";
        PrintService service = null;
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        if (printServices == null || printServices.length == 0) {
            logger.info("没有发现打印机");
        } else {
            List<String> printerNames = Arrays.stream(printServices).map(p -> p.getName()).collect(Collectors.toList());
            logger.info("可选的打印机:" + printerNames);
            for (int i = 0; i < printServices.length; i++) {
                String name = printServices[i].getName().toLowerCase();
                logger.info(i + "-打印机名称-" + name);
                if (name.contains(printerName.toLowerCase())) {
                    service = printServices[i];
                    break;
                }
            }
            if (service == null) {
                logger.info("未找到指定的打印机:" + printerName + ",可选打印服务:" + printerNames);
            }
        }
        return service;
    }

    /*
     * 打印指定文件
     * */
    public void print(String pdfPath, ConfigEntity configEntity) {
        PDDocument document = null;
        try {
            File file = new File(pdfPath);
            if (!file.exists()) {
                logger.info("PDF文件不存在:" + file.getAbsolutePath());
                return;
            }
            PrintService printService = lookupPrinter(configEntity.getPrinterName());
            if (printService == null) {
                logger.info("未找到指定的打印机:" + configEntity.getPrinterName());
                return;
            }
            //加载待打印文件
            document = PDDocument.load(file);
            //设置打印参数
            PrintRequestAttributeSet aset = getPrintRequestAttributeSet(configEntity.getCopy());//打印份数
            //设置纸张及缩放
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.SCALE_TO_FIT);//这里
            //设置打印方向：0-横向；1-纵向
            PageFormat pageFormat = new PageFormat();
            pageFormat.setOrientation(configEntity.getOrientation());
            //设置纸张参数
            pageFormat.setPaper(getPaper(configEntity));
            //设置多页打印
            Book book = new Book();
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            //打印任务
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(file.getName());
            printJob.setPrintService(printService);
            printJob.setPageable(book);
            //是否打印
            if (configEntity.getStatus() == 1) {
                printJob.print(aset);
            } else {
                logger.info("不打印");
            }
        } catch (Exception e) {
            logger.error("打印错误", e);
        } finally {
            IOUtils.closeQuietly(document);
        }
    }

    /*
     * 设置打印参数
     * */
    public PrintRequestAttributeSet getPrintRequestAttributeSet(int copy) {
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        //份数
        aset.add(new Copies(copy));
        //纸张
        aset.add(MediaSizeName.ISO_A4);
        // aset.add(Finishings.STAPLE);//装订
        //单双面
        aset.add(Sides.ONE_SIDED);
        return aset;
    }

    /*
     * 设置纸张大小格式，注意单位是dpi
     * 这个很重要，一定要根据实际纸张大小设置
     * 像素大小按照72dpi换算，注意是72dpi
     * 可以不用管：MediaSizeName.ISO_A4
     * 厘米到像素做换算
     * */
    public Paper getPaper(ConfigEntity configEntity) {
        Paper paper = new Paper();
        // 默认为A4纸张，对应像素宽和高分别为 595, 842
        int width = (int) Math.round(configEntity.getWidth() / 25.4 * 72);
        int height = (int) Math.round(configEntity.getHeight() / 25.4 * 72);
        logger.info("纸张大小：" + width + "*" + height);
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = (int) Math.round(configEntity.getMarginLeft() / 25.4 * 72);
        int marginRight = (int) Math.round(configEntity.getMarginRight() / 25.4 * 72);
        int marginTop = (int) Math.round(configEntity.getMarginTop() / 25.4 * 72);
        int marginBottom = (int) Math.round(configEntity.getMarginBottom() / 25.4 * 72);
        paper.setSize(width, height);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }
}