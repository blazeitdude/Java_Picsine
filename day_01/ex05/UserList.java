public interface UserList {
	public void	addUser(User user);
	public User	getUserByID(int ID);
	public User	getUserByIndex(int index);
	public int	getCountOfUsers();
}
