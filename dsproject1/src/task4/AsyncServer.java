import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class AsyncServer implements AsyncInterface {
	
	notificationclientinterface ntfyclient = null;

	public static void main(String args[]) {

		try {
			AsyncServer obj = new AsyncServer();
			AsyncInterface stub = (AsyncInterface) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.createRegistry(998);
			registry.bind("partfourasync", stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

	public String sayHello() {
		return "Hello, world!";
	}
	
	
	
	
	public OutPutObject sort(Integer[] a) throws RemoteException {
		OutPutObject r = new OutPutObjectImpl();
		ntfyclient.notify("i have received input for sort");
		Arrays.sort(a);

		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.setResultArray(a);
		r.setIsprocessed(true);
		return r;
	}
	
	@Override
	public void refisteerformessages(notificationclientinterface c) throws RemoteException {
		if (ntfyclient == null) {
			ntfyclient = c;
		}

	}

	public OutPutObject add(Integer a, Integer b) throws RemoteException {
		OutPutObject r = new OutPutObjectImpl();
		ntfyclient.notify("i have received input for add");
		Integer c = a + b;
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.setResult(c);
		r.setIsprocessed(true);
		return r;
	}
}
