import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SynchroServerInterface extends Remote {
	String sayHello() throws RemoteException;
	void upload(File f) throws RemoteException;
	void delete(String f)throws RemoteException;
	void update(File f,String filename) throws RemoteException;
}