
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ServerInterface1 extends Remote {
	public void setCounter(int avg) throws RemoteException;
	public int getCounter() throws RemoteException;
	public void receiveMessege(String msg,int serverno,Vector<Integer> clock1) throws RemoteException;;
}
