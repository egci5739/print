package Entity;

public class ConfigEntity {
    private String note1;//备注1
    private String note2;//备注2
    private String note3;//备注3
    private float sFont;//小字号
    private float bFont;//大字号
    private String printerName;//打印机名称
    private String rootPath;//程序根目录
    private int orientation;//打印方向：0-横向；1-纵向
    private int width;//纸张宽度
    private int height;//纸张高度
    //页边距
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;
    private int copy;//份数

    @Override
    public String toString() {
        return "ConfigEntity{" +
                "note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                ", note3='" + note3 + '\'' +
                ", sFont=" + sFont +
                ", bFont=" + bFont +
                ", printerName='" + printerName + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", orientation=" + orientation +
                ", width=" + width +
                ", height=" + height +
                ", marginLeft=" + marginLeft +
                ", marginRight=" + marginRight +
                ", marginTop=" + marginTop +
                ", marginBottom=" + marginBottom +
                ", copy=" + copy +
                ", status=" + status +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;//是否打印：0-不打印；1-打印


    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public String getNote3() {
        return note3;
    }

    public void setNote3(String note3) {
        this.note3 = note3;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public float getsFont() {
        return sFont;
    }

    public void setsFont(float sFont) {
        this.sFont = sFont;
    }

    public float getbFont() {
        return bFont;
    }

    public void setbFont(float bFont) {
        this.bFont = bFont;
    }

    public String getPrinterName() {
        return printerName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

}
