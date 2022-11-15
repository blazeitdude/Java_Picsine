import sun.awt.geom.AreaOp;

public class Program {
	public static void main(String[] args) {
		UserList listOfUsers = new UserArrayList();
		User	bohdan = new User("Bohdan", 220);
		User	efim = new User("Efim", 5000);
		User	maks = new User("Maks", 10000);
		Transaction trans = new Transaction(bohdan, maks);

		listOfUsers.addUser(bohdan);
		listOfUsers.addUser(efim);
		listOfUsers.addUser(maks);

		System.out.println(listOfUsers.getCountOfUsers());
		System.out.println(listOfUsers.getUserByID(1));
		trans.setTransfer_amount(220);
		System.out.println(listOfUsers.getUserByIndex(2));
	}
}
