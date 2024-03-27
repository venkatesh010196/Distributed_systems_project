import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OutPutObject extends Remote {
	void setResult(Integer a) throws RemoteException;

	Integer getResult() throws RemoteException;

	void setResultArray(Integer[] a) throws RemoteException;

	Integer[] getResultArray() throws RemoteException;

	void setoperation(String operation) throws RemoteException;

	String getoperation() throws RemoteException;

	boolean isIsprocessed() throws RemoteException;

	void setIsprocessed(boolean isprocessed) throws RemoteException;
	

}
