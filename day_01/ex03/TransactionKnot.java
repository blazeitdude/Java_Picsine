public class TransactionKnot {
	private Transaction	data;
	private TransactionKnot	prev;
	private	TransactionKnot	next;

	public TransactionKnot(Transaction instance) {
		this.data = instance;
		prev = null;
		next = null;
	}

	public Transaction getData() {
		return data;
	}

	public TransactionKnot getNext() {
		return next;
	}

	public void setData(Transaction data) {
		this.data = data;
	}

	public void setNext(TransactionKnot next) {
		this.next = next;
	}

	public TransactionKnot getPrev() {
		return prev;
	}

	public void setPrev(TransactionKnot prev) {
		this.prev = prev;
	}

}
