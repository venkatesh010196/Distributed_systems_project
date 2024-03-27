
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
	String sayHello() throws RemoteException;
	void upload(File f) throws RemoteException;
	File download(String f) throws RemoteException;
	void delete(String f)throws RemoteException;
	void rename(String currnet,String newf) throws RemoteException;
}
