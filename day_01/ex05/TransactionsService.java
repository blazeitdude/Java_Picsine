import java.util.UUID;

public class TransactionsService {
	private UserList	users;

	public TransactionsService() {
		users = new UserArrayList();
	}

	public void addUser(User instance) {
		users.addUser(instance);
	}

	public User retrieveUserBalance(User user) {
		User userData = users.getUserByID(user.getID());
		return userData;
	}

	public int	retrieveUserBalanceByID( int ID) { return users.getUserByID(ID).getBalance(); }

	public int	getUserBalanceByIndex(int index) {
		return users.getUserByIndex(index).getBalance();
	}
	public UserList	getUserList() { return users; }

	public boolean	transferTransaction(int sender_ID, int recipient_ID, int amount) {
		if (users.getUserByID(sender_ID).getBalance() - amount < 0)
			throw new IllegalTransactionException();
		Transaction trans = new Transaction(users.getUserByID(sender_ID), users.getUserByID(recipient_ID));
		if (trans.setTransfer_amount(amount)) {
			System.out.println("Transaction completed successfully");
			return true;
		}
		else {
			return false;
		}
	}

	public TransactionList	retrieveUserTransaction(User user) {
		User user_data = users.getUserByID(user.getID());
		return user_data.getTransactions();
	}

	public void removeTransactionFromUser(int user_ID, UUID transcation_ID) {
		users.getUserByID(user_ID).getTransactions().removeTransactionByID(transcation_ID);
	}

	public Transaction[] checkValidityTransactions() {
		TransactionList	unpaired_transcations = new TransactionLinkedLIst();

		for (int i = 0; i < users.getCountOfUsers(); i++) {
			Transaction[]	user_unpaired_transactions;
			user_unpaired_transactions = (getUnpairedTransactionsOfUser(users.getUserByIndex(i)));
			if (user_unpaired_transactions != null) {
				for (Transaction user_unpaired_transaction : user_unpaired_transactions)
					unpaired_transcations.addTransaction(user_unpaired_transaction);
			}
		}
		return unpaired_transcations.toArray();
	}

	public TransactionList	retrieveUserTransactionByID(int ID) {
		User user_data = users.getUserByID(ID);
		return user_data.getTransactions();
	}

	private Transaction[]	getUnpairedTransactionsOfUser(User user) {
		Transaction[]	user_transcations = user.getTransactions().toArray();
		TransactionList	unpaired = new TransactionLinkedLIst();
		for (int count = 0; count < user_transcations.length; count++) {
			boolean notFound = checkUnpairedTransaction(user, user_transcations, count);
			if (notFound) {
				unpaired.addTransaction(user_transcations[count]);
				return unpaired.toArray();
			}
		}
		return null;
	}

	private	boolean	checkUnpairedTransaction(User user, Transaction[] user_transactions, int count) {
		Transaction[]	partnerTransactions;
		if (user_transactions[count].getRecipient().getID() == user.getID()) {
			partnerTransactions = users.getUserByID(user_transactions[count].getSender().getID()).getTransactions().toArray();
		} else {
			partnerTransactions = users.getUserByID(user_transactions[count].getRecipient().getID()).getTransactions().toArray();
		}
		boolean notFound = true;
		for (int i = 0; i < partnerTransactions.length; i++) {
			if (user_transactions[count].getID() == partnerTransactions[i].getID()) {
				notFound = false;
				break ;
			}
		}

		return notFound;
	}

	private int addTransactionsUserToArrayPosition(User user, Transaction[] transactions, int position) {
		Transaction[] userTransactionArray = user.getTransactions().toArray();
		for (int userTransactionsCounter = 0; userTransactionsCounter < userTransactionArray.length; ++userTransactionsCounter) {
			boolean notFound = checkUnpairedTransaction(user, userTransactionArray, userTransactionsCounter);
			if (notFound) {
				transactions[position] = userTransactionArray[userTransactionsCounter];
				position++;
			}
		}
		return position;
	}

}
