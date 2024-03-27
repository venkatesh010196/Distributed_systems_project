import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AsyncInterface extends Remote{
	String sayHello() throws RemoteException;
    public OutPutObject add(Integer A , Integer b) throws RemoteException;
    public OutPutObject sort(Integer[] array) throws RemoteException;
    public void refisteerformessages(notificationclientinterface c) throws RemoteException;
}
