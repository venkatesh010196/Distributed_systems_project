
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;


public class Server3 implements ServerInterface3,Runnable{
	 
	    static int Counter=0;
	    static Vector<Integer> clock3=new Vector<Integer>();
	    Server3(){}
	    static{
	    Random rand=new Random();
		Counter=rand.nextInt(10);
	    for(int i=0;i<3;i++) {
	       if(i==2)
	       clock3.add(Counter);
	       else
	    	   clock3.add(i,0);}
		      }
	public static void main(String[] args) {
		
		try {
			Server3 obj = new Server3();
			ServerInterface3 stub3 = (ServerInterface3) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry3 = LocateRegistry.createRegistry(999);
			registry3.bind("Server3", stub3);
			Thread t1=new Thread(new Server3());
			t1.start();
               }
		 catch (Exception e) {
				System.err.println("Server3 exception: " + e.toString());
				e.printStackTrace();}
	            }

	public void setCounter(int avg) {
		for(int i=0;i<3;i++)
		{
			clock3.set(i,avg);
		}
		Counter=avg;	
	}


	public int getCounter() {
		return Counter;
	}

	public void receiveMessege(String msg, int serverno,Vector<Integer> clock) {
		System.out.println("timestamp before receiving the messege");
		for(int j=0;j<3;j++) {
			System.out.println(clock3.get(j));	
		}
		Counter++;
		clock3.set(2,Counter);
		System.out.println("timestamp after receiving the messege");
		 for(int i=0;i<3;i++) {
			 if(clock.get(i)>clock3.get(i))
				 clock3.set(i,clock.get(i));
		    	System.out.println(clock3.get(i));
		    }
		System.out.println("received messege is:\n"+msg);
		
	}
	
	@Override
	public void run() {
		while(true) {
		Scanner obj1=new Scanner(System.in);
		Scanner obj2=new Scanner(System.in);
		System.out.println("Enter the messege to send");
        String msg=obj1.nextLine();
        System.out.println("Enter the server no");
        int serverno=obj2.nextInt();
        System.out.println("time stamp before sending the messege");
	    for(int i=0;i<3;i++) {
	    	System.out.println(clock3.get(i));
	    }
        Counter++;
	    clock3.set(2,Counter);
	    try {
        switch(serverno) {
	case 1:
		Registry registry1 = LocateRegistry.getRegistry(997);
		ServerInterface1 stub1 = (ServerInterface1) registry1.lookup("Server1");
		stub1.receiveMessege(msg,1,clock3);
		System.out.println("time stamp after sending the messege");
		for(int i=0;i<3;i++) {
	    	System.out.println(clock3.get(i));
	    }
		break;
	case 2:
		Registry registry2 = LocateRegistry.getRegistry(998);
		ServerInterface2 stub2 = (ServerInterface2) registry2.lookup("Server2");
		stub2.receiveMessege(msg,1,clock3);
		System.out.println("time stamp after sending the messege");
		for(int i=0;i<3;i++) {
	    	System.out.println(clock3.get(i));
	    }
		break;
	case 3:
		receiveMessege(msg,1,clock3);
		System.out.println("time stamp after sending the messege");
		for(int i=0;i<3;i++) {
	    	System.out.println(clock3.get(i));
	    }
        }
	    }
	    catch (Exception e) {
			System.err.println("exception: " + e.toString());
			e.printStackTrace();}
            }
	    
		}
	}

