package LibreriaIMC;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemotaCalculoIMC extends Remote {

    DatosIMC calcularIMC(double peso, double altura)
            throws RemoteException;
}