import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Asyncclient extends UnicastRemoteObject  implements Runnable,notificationclientinterface {


	private static final long serialVersionUID = 1L;

	protected Asyncclient() throws RemoteException {
		super();
	}

	static Queue<Task> queue = new LinkedList<>();

	@SuppressWarnings("null")
	public static void main(String[] args) throws RemoteException, NotBoundException {

		Integer counter = 0;
		while (true) {
			System.out.print("Enter Operation and Data");
			Scanner sc = new Scanner(System.in);
			String data = sc.nextLine();
			String[] input = data.split(" ");
			Task task = new Task();
			if (input[0].equals("ADD")) {
				Integer a = Integer.parseInt(input[1]);
				Integer b = Integer.parseInt(input[2]);
				task.setOperaiton(input[0]);
				task.setA(a);
				task.setB(b);
				task.setTaskId(String.valueOf(counter));

			} else if (input[0].equals("SORT")) {
				task.setOperaiton(input[0]);
				Integer n = Integer.parseInt(input[1]);
				Integer[] arr = new Integer[n];
				for (int i = 0; i < n; i++) {
					arr[i] = Integer.parseInt(input[i + 2]);
				}
				task.setInput(arr);
				task.setTaskId(String.valueOf(counter));
			} else {
				break;
			}
			counter++;
			queue.add(task);
			Thread t1 = new Thread(new Asyncclient());
			t1.start();
		}

	}

	@Override
	public void run() {
		while (queue != null && queue.size() > 0) {
			Task task = queue.poll();
			
			try {
				Registry registry = LocateRegistry.getRegistry(998);
				AsyncInterface stub = (AsyncInterface) registry.lookup("partfourasync");
				stub.refisteerformessages(this);
				if (task.getOperaiton().equals("ADD")) {
					OutPutObject ob = stub.add(task.getA(), task.getB());
					do {
						Thread.sleep(6000);

					} while (ob.isIsprocessed() == false);
					System.out.println(
							"The addition of" + task.getA() + " and " + task.getB() + " is: " + ob.getResult());
				} else if (task.getOperaiton().equals("SORT")) {
					OutPutObject res = stub.sort(task.getInput());
					do {
						Thread.sleep(6000);

					} while (res.isIsprocessed() == false);
					System.out.println("The Sort of array is ");
					Integer[] array = res.getResultArray();
					for (int i = 0; i < array.length; i++) {
						System.out.print(array[i] + " ");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void notify(String message) throws RemoteException {
		System.out.println(message);
		
	}

	@Override
	public void tellclientinputisready(OutPutObject r) {
		// TODO Auto-generated method stub
		
	}
}
