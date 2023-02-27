import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

public class ChatClient implements ClientInterface{
    private String name;
    private static ServerInterface serverInterface;
    private static Scanner sc = new Scanner(System.in);
    
    public ChatClient(String name){
        this.name = name;
    }

    private static String welcome(){
        String name = "";
        boolean isRegistered = true;
        System.out.println("Welcome to the My Chat Application");
        do {
            System.out.println("Please enter your name");
            name = sc.nextLine().trim();
            if(name.equals(""))
                System.err.println("Please input your name");
            
            try {
                isRegistered = serverInterface.isRegistered(name);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (name.equals("") || isRegistered);

        return name;
    }

    public static void main(String[] args) {
        try {
			if (args.length < 1) {
                System.out.println("Usage: java ChatClient <rmiregistry host>");
				return;
			}
			
			String host = args[0];
			// Get remote object reference
			Registry registry = LocateRegistry.getRegistry(host);
			serverInterface = (ServerInterface) registry.lookup("ServerService");

			String clientName = welcome();
			ChatClient chatClient = new ChatClient(clientName);
			UnicastRemoteObject.exportObject(chatClient, 0);

            serverInterface.register(chatClient);

			String s;

			while(true){
                s = sc.nextLine();
				if(s.equals("quit")){
                    serverInterface.disconnect(chatClient);
					System.out.println("Thank you for using this application");

					sc.close();
					System.exit(0);
					break;
				}
                serverInterface.publishMessage(chatClient, s);
			}

			// registry.unbind("Info_itfService"); //Don't Need to
		} catch (Exception e) {
			System.err.println("Error on client: " + e);
		}

    }

    @Override
    public String getName() throws RemoteException {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public void publishMessage(String msg) throws RemoteException {
        // TODO Auto-generated method stub
        System.out.println(msg);
    }
}
