import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
    public void register(ClientInterface client) throws RemoteException;
    public void disconnect(ClientInterface client) throws RemoteException;
    public void publishMessage(ClientInterface client, String message) throws RemoteException;
}
