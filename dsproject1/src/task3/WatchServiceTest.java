
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WatchServiceTest {

	public static void main(String[] args) throws InterruptedException {
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();

			SynchroServerInterface stub;

			String s = System.getProperty("user.dir") + "/clientfiles/";
			File f = new File(s);
			File[] files = f.listFiles();
			Registry registry = LocateRegistry.getRegistry(998);
			try {
				stub = (SynchroServerInterface) registry.lookup("Hello");
				if (files != null) {
					for (File file : files) {
						File fnw = new File(s + file.getName());
						stub.upload(fnw);
					}
				}
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Path dir = Paths.get(s);
			dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);

			while (true) {

				WatchKey key = null;

				// Retrieve key from WatchService Queue.
				try {
					key = watchService.take();
				} catch (InterruptedException e) {
					return;
				}

				// Process each event for the key.
				for (WatchEvent<?> event : key.pollEvents()) {

					WatchEvent.Kind<?> kind = event.kind();

					// OVERFLOW is implicitly registered.
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}

					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					String filename = ev.context().toString();
					try {
						stub = (SynchroServerInterface) registry.lookup("Hello");
						if (kind.name().equals("ENTRY_CREATE")) {
							File fnw = new File(s + filename);
							stub.upload(fnw);
							System.out.print("File Upload is Sucessfull.");
						}
						if (kind.name().equals("ENTRY_DELETE")) {
							stub.delete(filename);
							System.out.print("File Delete is Sucessfull.");
						}
						if (kind.name().equals("ENTRY_MODIFY")) {

							File fnw = new File(s + filename);
							stub.update(fnw, filename);
							System.out.print("File Update is Sucessfull.");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// Reset key to be able to retrieve further events.
				// Break if key is invalid. (reset() returns false.)
				if (!key.reset())
					break;
				Thread.sleep(60000);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
