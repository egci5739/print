package Entity;

public class StaffEntity {
    private String name;
    private String company;
    private String date;
    private String certificate;
    private String card;
    private String picturePath;//图片URL地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "StaffEntity{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", date='" + date + '\'' +
                ", certificate='" + certificate + '\'' +
                ", card='" + card + '\'' +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }
}
