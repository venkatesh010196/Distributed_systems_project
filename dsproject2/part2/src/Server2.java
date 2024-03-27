import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;


public class Server2 implements ServerInterface2,Runnable{
	 
	    static int Counter=0;
	    static Vector<Integer> clock2=new Vector<Integer>();
	    Server2(){}
	    static{
	    Random rand=new Random();
		Counter=rand.nextInt(10);
		 for(int i=0;i<3;i++) {
		       if(i==1)
		       clock2.add(Counter);
		       else
		    	   clock2.add(i,0);}
			      }
	public static void main(String[] args) {
		
		try {
			Server2 obj = new Server2();
			ServerInterface2 stub2 = (ServerInterface2) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry2 = LocateRegistry.createRegistry(998);
			registry2.bind("Server2", stub2);
			Thread t1=new Thread(new Server2());
			t1.start();
               }
		 catch (Exception e) {
				System.err.println("Server2 exception: " + e.toString());
				e.printStackTrace();}
	            }

	public void setCounter(int avg) {
		for(int i=0;i<3;i++)
		{
			clock2.set(i,avg);
		}
		Counter=avg;	
	}

	public int getCounter() {
		return Counter;
	}

	public void receiveMessege(String msg, int serverno,Vector<Integer> clock) {
		System.out.println("timestamp before receiving the messege");
		for(int j=0;j<3;j++) {
			System.out.println(clock2.get(j));	
		}
		Counter++;
		clock2.set(1,Counter);
		System.out.println("timestamp after receiving the messege");
		 for(int i=0;i<3;i++) {
			 if(clock.get(i)>clock2.get(i))
				 clock2.set(i,clock.get(i));
		    	System.out.println(clock2.get(i));
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
	    	System.out.println(clock2.get(i));
	    }
        Counter++;
	    clock2.set(1,Counter);
	    try {
        switch(serverno) {
	case 1:
		Registry registry = LocateRegistry.getRegistry(997);
		ServerInterface1 stub1 = (ServerInterface1) registry.lookup("Server1");
		stub1.receiveMessege(msg,1,clock2);
		System.out.println("time stamp after sending the messege");
		 for(int i=0;i<3;i++) {
		    	System.out.println(clock2.get(i));
		    }
		 break;
	case 2:
		  receiveMessege(msg,1,clock2);
		  System.out.println("time stamp after sending the messege");
		  for(int i=0;i<3;i++) {
		    	System.out.println(clock2.get(i));
		    }
		  break;
	case 3:
		Registry registry2 = LocateRegistry.getRegistry(999);
		ServerInterface3 stub3 = (ServerInterface3) registry2.lookup("Server3");
		stub3.receiveMessege(msg,1,clock2);
		System.out.println("time stamp after sending the messege");
		 for(int i=0;i<3;i++) {
		    	System.out.println(clock2.get(i));
		    }}
	    }
	    catch (Exception e) {
			System.err.println("exception: " + e.toString());
			e.printStackTrace();}
            }
	    
		}
	}


