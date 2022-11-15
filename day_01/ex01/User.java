public class User {
	private Integer	ID;
	private String	name;
	private int		balance;

	public User (String name, int balance) {
		this.ID = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		this.balance = balance;
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

	public Integer getID() {
		return ID;
	}

	public void	printInfo() {
		System.out.format("ID: %d\nname: %s\nbalance: %d\n", ID, name, balance);
	}
}
