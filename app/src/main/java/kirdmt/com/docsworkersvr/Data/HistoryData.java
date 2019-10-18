package kirdmt.com.docsworkersvr.Data;

public class HistoryData {

    public String resident = null;
    public String house = null;
    public String author = null;
    public String need = null;
    public String date = null;
    public String method = null;
    public int number;


    public HistoryData(String resident, String house, String author, String need, String date, String method, int number)
    {
        this.resident = resident;
        this.house = house;
        this.author = author;
        this.need = need;
        this.date = date;
        this.method = method;
        this.number = number;
    }

    public HistoryData(String resident, String house, String author, String need, String date, String method) {

        this.resident = resident;
        this.house = house;
        this.author = author;
        this.need = need;
        this.date = date;
        this.method = method;
    }

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
