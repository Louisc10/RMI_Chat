import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.rmi.registry.*;

public class ChatServer implements ServerInterface{
    private static ArrayList<Chat> chats;
    private static ArrayList<ClientInterface> clientInterfaces;

    public ChatServer(){
        chats = new ArrayList<>();
        clientInterfaces = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
			// Create a Hello remote object
			ChatServer chatServer = new ChatServer();
			ServerInterface s_stub = (ServerInterface) UnicastRemoteObject.exportObject(chatServer, 0);

			// Register the remote object in RMI registry with a given identifier
			Registry registry = LocateRegistry.getRegistry("localhost");
			registry.bind("ServerService", s_stub);
			
			System.out.println("Server ready");

		} catch (Exception e) {
			System.err.println("Error on server :" + e);
			e.printStackTrace();
		}
    }

    @Override
    public boolean isRegistered(String name) throws RemoteException {
        // TODO Auto-generated method stub
        for (ClientInterface clientInterface : clientInterfaces) {
            if(clientInterface.getName().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public void register(ClientInterface client) throws RemoteException {
        // TODO Auto-generated method stub
        String text = client.getName() + " has join the chat";
        for (Chat chat : chats) {
            text = "[" + chat.getAuthor() + "] " + chat.getMessage(); 
            client.publishMessage(chat.getChat());
        }
        for (ClientInterface clientInterface : clientInterfaces) {
            clientInterface.publishMessage(text);
        }
        clientInterfaces.add(client);
        System.out.println(text);
    }

    @Override
    public void disconnect(ClientInterface client) throws RemoteException {
        // TODO Auto-generated method stub
        if(clientInterfaces.remove(client)){
            String text = client.getName() + " has left the chat";
            System.out.println(text);
        }
    }

    @Override
    public void publishMessage(ClientInterface client, String message) throws RemoteException {
        // TODO Auto-generated method stub
        if(clientInterfaces.contains(client)){
            String text = "[" + client.getName() + "] " + message; 
            chats.add(new Chat(client.getName(), message));
            for (ClientInterface clientInterface : clientInterfaces) {
                clientInterface.publishMessage(text);
            }
        }
        else
            System.out.println("[!] Illegal message detected from " + client.getName());
    }
    
}
