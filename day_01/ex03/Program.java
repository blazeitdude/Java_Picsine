import java.util.UUID;

public class Program {
	public static void main(String[] args) {

		UserList	list = new UserArrayList();

		User p1 = new User("Mike", 2600);
		User p2 = new User("John", 1000);
		User p3 = new User("Nikol", -200);

		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);

		System.out.println("\n||Выполнение транзакций||");
		Transaction t1 = new Transaction(p1, p2);
		t1.setTransfer_amount(1000);
		System.out.println(t1);
		System.out.println();
		Transaction t2 = new Transaction(p1, p2);
		t2.setTransfer_amount(-500);
		System.out.println(t2);
		System.out.println();
		Transaction t3 = new Transaction(p2, p3);
		t3.setTransfer_amount(200);
		System.out.println(t3);
		System.out.println();
		Transaction t4 = new Transaction(p2, p3);
		t4.setTransfer_amount(400);
		System.out.println(t4);
		System.out.println();

//        Добавление транзакций в двусвязанный список
		TransactionList tl = new TransactionLinkedLIst();
		tl.addTransaction(t1);
		tl.addTransaction(t2);
		tl.addTransaction(t3);
		tl.addTransaction(t4);
		System.out.println("\nУдаление Транзакции ID: " + t2.getID());
		tl.removeTransactionByID(t2.getID());
		System.out.println("\n||Вывод транзакций||");
		tl.print();
		tl.removeTransactionByID(UUID.randomUUID());
	}
}
