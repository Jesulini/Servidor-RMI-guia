package LibreriaIMC;

import java.io.Serializable;

public class DatosIMC implements Serializable {

    private double peso;
    private double altura;
    private double imc;
    private String clasificacion;

    public DatosIMC(double peso, double altura) {
        this.peso = peso;
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public double getImc() {
        return imc;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}