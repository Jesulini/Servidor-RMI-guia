package ServidorIMC;

import LibreriaIMC.DatosIMC;
import LibreriaIMC.IRemotaCalculoIMC;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculoRmiImcImplem
        extends UnicastRemoteObject
        implements IRemotaCalculoIMC {

    public CalculoRmiImcImplem() throws RemoteException {
        super();
    }

    @Override
    public DatosIMC calcularIMC(double peso, double altura)
            throws RemoteException {

        double imc = peso / (altura * altura);

        String clasificacion;

        if (imc < 18.5) {
            clasificacion = "Bajo peso";
        } else if (imc < 25) {
            clasificacion = "Peso normal";
        } else if (imc < 30) {
            clasificacion = "Sobrepeso";
        } else {
            clasificacion = "Obesidad";
        }

        DatosIMC datos = new DatosIMC(peso, altura);

        datos.setImc(imc);
        datos.setClasificacion(clasificacion);

        return datos;
    }
}