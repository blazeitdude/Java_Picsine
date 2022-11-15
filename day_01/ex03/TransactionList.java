import java.util.UUID;

public interface TransactionList {
	public void			addTransaction(Transaction instance);
	public void				removeTransactionByID(UUID id);
	public Transaction[]	toArray();
	public void 			print();
}
