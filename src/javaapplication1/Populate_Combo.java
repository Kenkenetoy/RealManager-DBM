package javaapplication1;


public class Populate_Combo {

    private int ComID;
    private String ComName;

    public Populate_Combo(int comID, String comName) {
        this.ComID = comID;
        this.ComName = comName;
    }

    public int getComID() {
        return ComID;
    }

    public void setComID(int id) {
        this.ComID = id;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        this.ComName = comName;
    }
 
}
