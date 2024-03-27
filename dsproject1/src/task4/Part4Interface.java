

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Part4Interface extends Remote {
    String sayHello() throws RemoteException;
    public int add(int a,int b) throws RemoteException;
    public int[] sort(int[] a) throws RemoteException;
}