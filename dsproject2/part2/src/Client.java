

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public static void main(String[] args) {
		try {
		Registry registry1 = LocateRegistry.getRegistry(997);
		ServerInterface1 stub1 = (ServerInterface1) registry1.lookup("Server1");
		
		Registry registry2 = LocateRegistry.getRegistry(998);
		ServerInterface2 stub2 = (ServerInterface2) registry2.lookup("Server2");
		
		Registry registry3 = LocateRegistry.getRegistry(999);
		ServerInterface3 stub3 = (ServerInterface3) registry3.lookup("Server3");
		
		System.out.println("counter values before synchronization");
		System.out.println(stub1.getCounter()+ "\t" );
		System.out.println(stub2.getCounter()+ "\t" );
		System.out.println(stub3.getCounter()+ "\t" );
		
		synchronizeClocks(stub1, stub2, stub3);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
	public static void synchronizeClocks(ServerInterface1 stub1,ServerInterface2 stub2,ServerInterface3 stub3) {
		try {
			int counter1 = stub1.getCounter();
			int counter2=stub2.getCounter();
			int counter3=stub3.getCounter();
			int avg=(counter1+counter2+counter3)/3;
			stub1.setCounter(avg);
			stub2.setCounter(avg);
			stub3.setCounter(avg);
			
			System.out.println("counter values after synchronization");
			System.out.println(stub1.getCounter()+ "\t" );
			System.out.println(stub2.getCounter()+ "\t" );
			System.out.println(stub3.getCounter()+ "\t" );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
