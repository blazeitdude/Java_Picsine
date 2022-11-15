import java.util.Scanner;
import java.util.UUID;

public class Menu {
	private	boolean				dev;
	private	Scanner				input;
	private	TransactionsService	service;

	public void	setProfile() {
		dev = true;
	}

	public Menu() {
		input = new Scanner(System.in);
		service = new TransactionsService();
	}
	private void	showMenu() {

		System.out.println("1. Add  a user" + "\n2. View user balances" +
				"\n3. Perform a transfer" + "\n4. View all transactions for a specific user");
		if (dev) {
			System.out.println("5. DEV - remove a transfer by ID" +
					"\n6. DEV - check transfer validity" +
					"\n7. Finish execution\n");
		}
	}

	private void	choice(int command) {
		switch (command) {
			case 1:
				addUser();
				break ;
			case 2:
				viewBalance();
				break ;
			case 3:
				performTransfer();
				break ;
			case 4:
				viewTransactions();
				break ;
			case 5:
				if (dev) {
					removeTransfer();
					break ;
				}
			case 6:
				if (dev) {
					checkTransferValidity();
					break ;
				}
			case 7:
				System.exit(0);
		}
	}

	public void start() {
		while (true) {
			showMenu();
			System.out.print("--> ");
			choice(input.nextInt());
			System.out.println("-----------------------------ldione-----------------------------");
		}
	}

	private void	addUser() {
		String	tmp = "";
		Integer		balance = 0;
		input.nextLine();
		while (true) {
			System.out.println("Enter a username and a balance");
			System.out.print("--> ");
			tmp = input.nextLine();
			String[]	parsed_line = tmp.split(" ");
			if (parsed_line.length != 2) {
				System.out.println("Incorrect number of arguments");
				continue ;
			}
			try {
				balance = Integer.parseInt(parsed_line[1]);
			} catch (NumberFormatException numberFormatException) {
				System.out.println("Balance is not a number");
				continue;
			}
			tmp = parsed_line[0];
			break ;
		}
		User newUser = new User(tmp, balance);
		service.addUser(newUser);
		System.out.println("User with id = " + newUser.getID() + " is added");
	}

	private void	viewBalance() {
		String	tmp = "";
		Integer	ID;
		input.nextLine();
		while (true) {
			System.out.println("Enter a user ID");
			System.out.print("--> ");
			tmp = input.nextLine();
			try {
				ID = Integer.parseInt(tmp);
			} catch (NumberFormatException numberFormatException) {
				System.out.println("ID is not a number");
				continue;
			}
			try {
				System.out.println(service.getUserList().getUserByID(ID).getName() + ": "
				+ service.retrieveUserBalanceByID(ID));
			} catch (NotFoundException notFoundException) {
				System.out.println("User not found");
			}
			break ;
		}
	}

	private void	performTransfer() {
		String	tmp = "";
		int[]	inp = new int[3];
		input.nextLine();
		while (true) {
			System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
			System.out.print("--> ");
			tmp = input.nextLine();
			String[]	args = tmp.split(" ");
			if (args.length != 3) {
				System.out.println("Incorrect number of arguments");
				continue;
			}
			try {
				for (int i = 0; i < 3; i++)
					inp[i] = Integer.parseInt(args[i]);

			} catch	(NumberFormatException numberFormatException)
			{
				System.out.println("one of the arguments is not a number");
				continue;
			}
			if (inp[0] == inp[1]) {
				System.out.println("Еhe sender cannot be the recipient!");
				break;
			}
			try {
				service.transferTransaction(inp[0], inp[1], inp[2]);
			} catch (IllegalTransactionException illegalTransactionException) {
				System.out.println("transaction cannot be completed: insufficient funds");
				break;
			}
			System.out.println("transaction completed successfully");
			break;
		}
	}

	private void	viewTransactions() {
		String	tmp = "";
		int		ID;

		input.nextLine();
		while (true) {
			System.out.println("Enter a user ID");
			System.out.print("--> ");
			tmp = input.nextLine();
			try {
				ID = Integer.parseInt(tmp);
			} catch (NumberFormatException numberFormatException) {
				System.out.println("ID is not a number");
				continue;
			}
			try {
				printTransactionArray(service.retrieveUserTransactionByID(ID).toArray(), ID);
			} catch (NotFoundException notFoundException) {
				System.out.println("User not found");
				return ;
			}
			break;
		}
	}

	private void printTransactionArray(Transaction[] transactionsArray, int userID) {
		if (transactionsArray.length == 0)
			System.out.println("Empty Array\n");
		String str;
		for (int count = 0; count < transactionsArray.length; count++) {
			if (transactionsArray[count].getSender().getID() == userID) {
				str = "to " + transactionsArray[count].getRecipient().getName() + "(id = " + transactionsArray[count].getSender().getID() +
						") " + -1 * transactionsArray[count].getTransfer_amount() + " " + transactionsArray[count].getSender().getTransfer_type() +
						" id = " + transactionsArray[count].getID();
			} else {
				str = "from " + transactionsArray[count].getRecipient().getName() + "(id = " + transactionsArray[count].getRecipient().getID() +
						") " + transactionsArray[count].getTransfer_amount() + " " + transactionsArray[count].getRecipient().getTransfer_type() +
						" id = " + transactionsArray[count].getID();
			}
			System.out.println(str);
		}
	}

	private void	removeTransfer() {
		String	tmp = "";
		UUID	transferID;
		int		userID;
		input.nextLine();
		while (true) {
			System.out.println("Enter a user ID, a transfer ID");
			System.out.print("--> ");
			tmp = input.nextLine();
			String[]	args = tmp.split(" ");
			if (args.length != 2) {
				System.out.println("Incorrect number of arguments");
				continue;
			}
			try {
				userID = Integer.parseInt(args[0]);
			} catch	(NumberFormatException numberFormatException) {
				System.out.println("user ID is not a number!");
				continue;
			}
			try {
				transferID = UUID.fromString(args[1]);
			}	catch (IllegalArgumentException illegalArgumentException) {
				System.out.println("Transfer ID is incorrect!");
				continue;
			}
			try {
				service.getUserList().getUserByID(userID).getTransactions().removeTransactionByID(transferID);
				System.out.println("ID: " + userID + "\nname: " + service.getUserList().getUserByID(userID)
				+ "\ntransaction: " + transferID + "\nremoved");
			} catch (TransactionNotFoundException transactionNotFoundException) {
				System.out.println("Transaction not found");
			} catch (NotFoundException notFoundException) {
				System.out.println("User  not found");
			}
			break ;
		}
	}

	private void	checkTransferValidity() {
		System.out.print("results --> ");
		printTransactionArray(service.checkValidityTransactions());
	}

	public static void printTransactionArray(Transaction[] transactionArray) {
		if (transactionArray.length == 0)
			System.out.println("Нет транзакций\n");
		for (int count = 0; count < transactionArray.length; count++) {
			System.out.println(transactionArray[count]);
		}
	}
}
