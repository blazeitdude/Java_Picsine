public class SynchronisedPrint {
	private volatile int	perm = 0;

	 synchronized void	printEgg() {
		 while (perm == 1) {
			 try {
				 wait();
			 } catch (InterruptedException interruptedException) {
				 System.out.println(interruptedException.getMessage());
			 }
		 }
			 ++perm;
			 System.out.println("Egg");
			 notify();
	 }

	 synchronized void printHen() {
		 while (perm == 0) {
			 try {
				 wait();
			 } catch (InterruptedException interruptedException) {
				 System.out.println(interruptedException.getMessage());
			 }
		 }
		 --perm;
		 System.out.println("Hen");
		 notify();
	 }
}
