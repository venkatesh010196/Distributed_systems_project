import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class DefferedSyncClient extends UnicastRemoteObject implements Runnable, notificationclientinterface {

	private static final long serialVersionUID = 1L;

	protected DefferedSyncClient() throws RemoteException {
		super();
	}

	static Queue<Task> queue = new LinkedList<>();
	static Map<String, OutPutObject> hashmap = new HashMap<>();

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
			Thread t1 = new Thread(new DefferedSyncClient());
			t1.start();
		}

	}

	@Override
	public void run() {
		while (queue != null && queue.size() > 0) {
			Task task = queue.poll();

			try {
				Registry registry = LocateRegistry.getRegistry(998);
				DefferedSyncInterface stub = (DefferedSyncInterface) registry.lookup("partfourdesync");
				stub.refisteerformessages(this);
				String number = task.getTaskId();
				if(task.getOperaiton().equals("ADD")) {
					stub.add(task.getA(), task.getB(),number);
				}else {
					stub.sort(task.getInput(),number);
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
		try {
			if(r.getoperation().equals("ADD")) {
				System.out.println("The add output is " + r.getResult());
			}else {
				System.out.println("The sort output is ");
				Integer[] array = r.getResultArray();
				for (int i = 0; i < array.length; i++) {
					System.out.print(array[i] + " ");
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
