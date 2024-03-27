import java.rmi.Remote;
import java.rmi.RemoteException;

public interface notificationclientinterface extends Remote{
	void notify(String message) throws RemoteException;

	void tellclientinputisready(OutPutObject r) throws RemoteException;

}
