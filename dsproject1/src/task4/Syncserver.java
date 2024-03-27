import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
/*import java.rmi.RemoteException;*/
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Syncserver implements Part4Interface {

	public Syncserver() {
	}

	public static void main(String args[]) {

		try {
			Syncserver obj = new Syncserver();
			Part4Interface stub = (Part4Interface) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.createRegistry(998);
			registry.bind("partfour", stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

	public String sayHello() {
		return "Hello, world!";
	}

	public int[] sort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	public int add(int a, int b) {
		int c = a + b;
		return c;

	}
}