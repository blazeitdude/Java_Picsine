public class UserArrayList implements UserList {
	private User[]	data;
	private int		countOfUser;

	public UserArrayList() {
		data = new User[10];
		countOfUser = 0;
	}

	@Override
	public int getCountOfUsers() {
		return countOfUser;
	}

	@Override
	public User getUserByID(int ID) {
		for (int i = 0; i < countOfUser; i++) {
			if (data[i].getID() == ID)
				return (data[i]);
		}
		throw new NotFoundException();
	}

	@Override
	public User getUserByIndex(int index) {
		if (index > countOfUser)
			throw new NotFoundException();
		else
			return this.data[index];
	}

	@Override
	public void addUser(User user) {
		if (this.countOfUser == 10) {
			User[]	data_temp = new User[data.length * 2];
			for (int i = 0; i < data.length; i++)
				data_temp[i] = this.data[i];
			this.data = data_temp;
		}
		else {
			this.data[countOfUser++] = user;
			System.out.println("user successfully added");
		}
	}
}
