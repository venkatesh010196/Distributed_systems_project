
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String operation = args[0];
        String dir = System.getProperty("user.dir")+"/clientfiles/";
        
        try {
        	Registry registry = LocateRegistry.getRegistry(998);
            Hello stub = (Hello) registry.lookup("Hello");
        	if(operation.equals("UPLOAD")) {
                //upload
                Scanner obj=new Scanner(System.in);
                System.out.println("Enter the file name to upload:");
                String fup=obj.nextLine();
                File fnw=new File(dir+fup);
                System.out.println(fnw.getName());
                stub.upload(fnw);
                System.out.println("File upoloaded");
        	}
            if(operation.equals("DOWNLOAD")) {
            	System.out.println("Enter the file name to download:");
            	Scanner obj=new Scanner(System.in);
                String fdown=obj.nextLine();
                File b = stub.download(fdown);
                File clinetfolder = new File(dir+fdown);
                System.out.println(clinetfolder.getAbsolutePath());
                clinetfolder.createNewFile();
                FileOutputStream out = new FileOutputStream(clinetfolder);
    			FileInputStream in = new FileInputStream(b);
    			int n;
                while ((n = in.read()) != -1) {
                    out.write(n);
                }
                System.out.println("File Downloaded.");
            }
            if(operation.equals("DELETE")) {
            	System.out.println("Enter the file name to DELETE:");
            	Scanner obj=new Scanner(System.in);
            	String fdown=obj.nextLine();
            	stub.delete(fdown);
            	System.out.println("File deleted");
            	
            }
            if(operation.equals("RENAME")) {
            	System.out.println("Enter the file name to RENAME:");
            	Scanner obj=new Scanner(System.in);
            	String fdown=obj.nextLine();
            	String fdown1=obj.nextLine();
            	stub.rename(fdown, fdown1);
            	System.out.println("File name is changed");
            }
            
            //download 
            //Scanner obj=new Scanner(System.in);
            
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}


