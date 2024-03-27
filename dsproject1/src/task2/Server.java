
import java.rmi.registry.Registry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
/*import java.rmi.RemoteException;*/
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {
	
	 String dir = System.getProperty("user.dir")+"/serverfiles/";

	public Server() {
	}

	public String sayHello() {
		return "Hello, world!";
	}

	public static void main(String args[]) {

		try {
			Server obj = new Server();
			Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

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
		File fnew = new File(dir+f.getName());
		System.out.println("yup" + fnew.getAbsolutePath());
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
	public File download(String f) throws RemoteException {
		File fdown = new File(dir+f);
		return fdown;
	}

	@Override
	public void delete(String f) throws RemoteException {
		File file = new File(dir+f);
		file.delete();
		
	}

	@Override
	public void rename(String current, String newf) throws RemoteException {
		File currentfile = new File(dir+current);
		File newfile = new File(dir+newf);
		currentfile.renameTo(newfile);
		
	}
}
