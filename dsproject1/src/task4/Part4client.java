
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Part4client {

	private Part4client() {
	}

	@SuppressWarnings("null")
	public static void main(String[] args) throws RemoteException, NotBoundException {

		String operation = args[0];

		try {
			Registry registry = LocateRegistry.getRegistry(998);
			Part4Interface stub = (Part4Interface) registry.lookup("partfour");
			if (operation.equals("ADD")) {
				Scanner obj = new Scanner(System.in);
				int a = obj.nextInt();
				int b = obj.nextInt();
				int c = stub.add(a, b);
				System.out.println(" The Result is: " + c);
			}

			if (operation.equals("SORT")) {

				Scanner obj1 = new Scanner(System.in);
				System.out.print("Enter the number of elements:");
				Integer n = obj1.nextInt();
				int d[] = new int[n];
				for (int i = 0; i < n; i++) {
					d[i] = obj1.nextInt();
				}
				int p[] = stub.sort(d);
				System.out.println("The Sorted Elements are:");
				for (int j = 0; j < n; j++) {
					System.out.println(p[j]);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
