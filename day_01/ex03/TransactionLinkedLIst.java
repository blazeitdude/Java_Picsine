import java.util.UUID;

public class TransactionLinkedLIst implements TransactionList{
	private int				length = 0;
	private TransactionKnot	head;

	@Override
	public void addTransaction(Transaction instance) {
		TransactionKnot	knot = new TransactionKnot(instance);
		if (head != null) {
			head.setPrev(knot);
			knot.setNext(head);
		}
		head = knot;
		length++;
	}

	@Override
	public void removeTransactionByID(UUID id) {
		TransactionKnot	current = head;
		while (current != null) {
			if (current.getData().getID().equals(id)) {
				if (current.getPrev() != null) {
						current.getPrev().setNext(current.getNext());
				}
				if (current.getNext() != null) {
					if (current.getPrev() == null) {
						head = current.getNext();
					}
					current.getNext().setPrev(current.getPrev());
				}
				length--;
				return ;
			}
			current = current.getNext();
		}
		throw new TransactionNotFoundException();
	}

	public int getLength() {
		return length;
	}

	@Override
	public Transaction[] toArray() {
		Transaction[]	ret = new Transaction[length];
		TransactionKnot	current = head;
		int				i = 0;

		while (current != null) {
			ret[i++] = current.getData();
			current = current.getNext();
		}
		return ret;
	}

	public void print() {
		Transaction[] arr = toArray();
		for (Transaction el : arr) {
			System.out.println(el);
		}
	}
}
