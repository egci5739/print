package Tool;

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

public class EprintBack {
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
    public void print(String fileName, String printerName) {
        PDDocument document = null;
        try {
            File file = new File(fileName);
            if (file == null) {
                logger.info("传入的PDF文件为空");
            }
            if (!file.exists()) {
                logger.info("PDF文件不存在:" + file.getAbsolutePath());
            }
            PrintService printService = lookupPrinter(printerName);
            if (printService == null) {
                logger.info("未找到指定的打印机:" + printerName);
                return;
            }
            //加载待打印文件
            document = PDDocument.load(file);
            //设置打印参数
            PrintRequestAttributeSet aset = getPrintRequestAttributeSet(1);//打印一份
            //设置纸张及缩放
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
            //设置打印方向：0-横向；1-纵向
            PageFormat pageFormat = new PageFormat();
            pageFormat.setOrientation(1);
            //设置纸张参数
            pageFormat.setPaper(getPaper());
            //设置多页打印
            Book book = new Book();
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            //打印任务
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(file.getName());
            printJob.setPrintService(printService);
            printJob.setPageable(book);
            printJob.print(aset);
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
        aset.add(new Copies(copy)); //份数
        aset.add(MediaSizeName.ISO_A4); //纸张
        // aset.add(Finishings.STAPLE);//装订
        aset.add(Sides.ONE_SIDED);//单双面
        return aset;
    }

    /*
     * 设置纸张格式
     * */
    public Paper getPaper() {
        Paper paper = new Paper();
        // 默认为A4纸张，对应像素宽和高分别为 595, 842
        int width = 595;
        int height = 842;
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = 10;
        int marginRight = 0;
        int marginTop = 10;
        int marginBottom = 0;
        paper.setSize(width, height);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }
}