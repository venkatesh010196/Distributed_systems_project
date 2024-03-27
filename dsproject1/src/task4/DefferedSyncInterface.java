import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DefferedSyncInterface extends Remote{
	String sayHello() throws RemoteException;
    public void add(Integer A , Integer b,String number) throws RemoteException;
    public void sort(Integer[] array,String number) throws RemoteException;
    public void refisteerformessages(notificationclientinterface c) throws RemoteException;
}
