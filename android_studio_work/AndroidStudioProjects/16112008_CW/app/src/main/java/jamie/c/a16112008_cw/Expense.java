package jamie.c.a16112008_cw;

public class Expense  {
    private int ID = 0;
    private String datePaid = "";
    private String dateAdded = "";
    private String dateIssued = "";
    private int paid = 0;
    private double amount = 0;
    private int VAT = 0;
    private double totalAmount = 0;
    private String description = "";
    private byte imageInByte[];


    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDatePaid() {
        return this.datePaid;
    }

    public void setDatePaid(String datePaid) { this.datePaid = datePaid; }

    public int getPaid() { return this.paid; }

    public void setPaid(int paid) { this.paid = paid; }

    public String getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateIssued() {
        return  this.dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getVAT() {
        return this.VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public double getTotalamount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return this.imageInByte;
    }

    public void setImage(byte[] image) {
        this.imageInByte = image;
    }

}
