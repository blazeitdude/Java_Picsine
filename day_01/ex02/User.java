public class User {
	private Integer	ID;
	private String name;
	private int		balance;
	private String  transfer_type;

	User(String name, int balance)
	{
		this.name = name;
		this.balance = balance;
		this.ID = UserIdsGenerator.getInstance().generateId();
	}

	public int getID() {
		return this.ID;
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

	@Override
	public String toString() {
		return ("ID: " + this.ID + "\nname: " + this.name + "\nbalance: " + this.balance);
	}
}