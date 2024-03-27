
import java.rmi.registry.Registry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
/*import java.rmi.RemoteException;*/
import java.rmi.server.UnicastRemoteObject;

public class Server1 implements SynchroServerInterface {
	
	String dir = System.getProperty("user.dir")+"/serverfiles/";

	public Server1() {
	}

	public String sayHello() {
		return "Hello, world!";
	}

	public static void main(String args[]) {

		try {
			Server1 obj = new Server1();
			SynchroServerInterface stub = (SynchroServerInterface) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.createRegistry(998);
			registry.bind("Hello", stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void upload(File f) {
		System.out.println(f.getName());
		File fnew = new File(dir + f.getName());
		try {
			fnew.createNewFile();
			FileOutputStream out = new FileOutputStream(fnew);
			FileInputStream in = new FileInputStream(f);
			int n;

			// read() function to read the
			// byte of data
			while ((n = in.read()) != -1) {
				// write() function to write
				// the byte of data
				out.write(n);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


	@Override
	public void delete(String f) throws RemoteException {
		File file = new File(dir+f);
		file.delete();
		
	}

	@Override
	public void update(File f, String newf) throws RemoteException {
		delete(newf);
		upload(f);
		
	}
}
