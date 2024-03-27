import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OutPutObjectImpl extends UnicastRemoteObject implements OutPutObject {

	private static final long serialVersionUID = 1L;


	protected OutPutObjectImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	Integer result;
	Integer[] oputputarray;
	boolean isprocessed;
	String operation;
	

	public boolean isIsprocessed() {
		return isprocessed;
	}

	public void setIsprocessed(boolean isprocessed) {
		this.isprocessed = isprocessed;
	}

	@Override
	public void setResult(Integer a) throws RemoteException {
		// TODO Auto-generated method stub
		this.result = a;

	}

	@Override
	public Integer getResult() throws RemoteException {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public void setResultArray(Integer[] a) throws RemoteException {
		// TODO Auto-generated method stub
		this.oputputarray = a;

	}

	@Override
	public Integer[] getResultArray() throws RemoteException {
		// TODO Auto-generated method stub
		return oputputarray;
	}

	@Override
	public void setoperation(String operation) throws RemoteException {
		// TODO Auto-generated method stub
		this.operation = operation;

	}

	@Override
	public String getoperation() throws RemoteException {
		// TODO Auto-generated method stub
		return operation;
	}

}
