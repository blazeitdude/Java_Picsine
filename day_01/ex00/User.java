

public class User {
    private Integer	ID;
    private String name;
    private int		balance;
    private String  transfer_type;

    User()
    {
        transfer_type = "NONE";
        name = "NONE";
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public void printInfo() {
        System.out.format("ID: %d\nname: %s\nbalance: %d\n", ID, name, balance);
        if (!transfer_type.isEmpty())
            System.out.println("transfer type: " + transfer_type);

    }
}
