import java.util.UUID;

public class Transaction {
	private final UUID	ID;
	private User		sender;
	private User		recipient;
	private int			transfer_amount;

	Transaction ()
	{
		ID = UUID.randomUUID();
	}

	Transaction (User sender, User recipient)
	{
		this.sender = sender;
		this.recipient = recipient;
		ID = UUID.randomUUID();
	}

	public UUID getID() {
		return ID;
	}

	public int getTransfer_amount() {
		return transfer_amount;
	}

	public boolean setTransfer_amount(int transfer_amount) {
		this.transfer_amount = transfer_amount;
		if (this.transfer_amount > 0)
			return debit();
		else if (this.transfer_amount < 0)
			return credit();
		return (false);
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	private boolean debit() {
		if (sender.getBalance() - this.transfer_amount >= 0)
		{
			sender.setBalance(sender.getBalance() - this.transfer_amount);
			recipient.setBalance(recipient.getBalance() + this.transfer_amount);
			System.out.format("%s --%d--> %s\n", sender.getName(), this.transfer_amount, recipient.getName());
			recipient.setTransfer_type("DEBIT");
			sender.setTransfer_type("CREDIT");
			recipient.getTransactions().addTransaction(this);
			sender.getTransactions().addTransaction(this);
			return false;
		}
		else
		{
			System.out.println("operation cannot be performed: insufficient funds\n");
			return true;
		}
	}

	private boolean credit()
	{
		if (Math.abs(recipient.getBalance()) - Math.abs(this.transfer_amount) > 0)
		{
			recipient.setBalance(recipient.getBalance() - this.transfer_amount);
			sender.setBalance(sender.getBalance() + this.transfer_amount);
			System.out.format("%s --(%d)--> %s\n", recipient.getName(), this.transfer_amount, sender.getName());
			recipient.setTransfer_type("CREDIT");
			sender.setTransfer_type("DEBIT");
			recipient.getTransactions().addTransaction(this);
			sender.getTransactions().addTransaction(this);
			return false;
		}
		else
		{
			System.out.println("operation cannot be performed: insufficient funds\n");
			return true;
		}
	}

	public void printInfo()
	{
		System.out.println("Transaction ID: " + this.ID + "\nTransfer amount: " + this.transfer_amount);
		sender.printInfo();
		recipient.printInfo();
	}

	@Override
	public String toString() {
		return ("ID: " + ID + "\nsender: " + sender.getName() + "\nrecipient: " + recipient.getName());
	}
}