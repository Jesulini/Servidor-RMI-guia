package ClienteIMC;

import LibreriaIMC.DatosIMC;
import LibreriaIMC.IRemotaCalculoIMC;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VentanaPrincipal extends JFrame {

    private JTextField txtPeso;
    private JTextField txtAltura;
    private JLabel lblResultado;
    private JLabel lblClasificacion;
    private JButton btnCalcular;

    private IRemotaCalculoIMC servicio;

    public VentanaPrincipal() {

        configurarVentana();
        conectarServidor();
    }

    private void configurarVentana() {

        setTitle("Calculadora de IMC - RMI");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel lblPeso = new JLabel("Peso (kg):");
        txtPeso = new JTextField();

        JLabel lblAltura = new JLabel("Altura (m):");
        txtAltura = new JTextField();

        btnCalcular = new JButton("CALCULAR IMC");

        lblResultado = new JLabel("IMC: -");
        lblClasificacion = new JLabel("Clasificación: -");

        panel.add(lblPeso);
        panel.add(txtPeso);

        panel.add(lblAltura);
        panel.add(txtAltura);

        panel.add(new JLabel(""));
        panel.add(btnCalcular);

        panel.add(lblResultado);
        panel.add(lblClasificacion);

        add(panel);

        btnCalcular.addActionListener(e -> calcularIMC());
    }

    private void conectarServidor() {

        try {

            Registry registro =
                    LocateRegistry.getRegistry("localhost", 1099);

            servicio =
                    (IRemotaCalculoIMC) registro.lookup("CalculoIMC");

            System.out.println("Conectado al servidor RMI.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar con el servidor RMI.\n"
                    + "Verifique que el servidor esté iniciado.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void calcularIMC() {

        try {

            if (servicio == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No hay conexión con el servidor.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            if (peso <= 0 || altura <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El peso y la altura deben ser mayores que cero.",
                        "Datos inválidos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            DatosIMC resultado =
                    servicio.calcularIMC(peso, altura);

            lblResultado.setText(
                    String.format("IMC: %.2f", resultado.getImc())
            );

            lblClasificacion.setText(
                    "Clasificación: " + resultado.getClasificacion()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese valores numéricos válidos.",
                    "Datos inválidos",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al comunicarse con el servidor:\n"
                    + e.getMessage(),
                    "Error RMI",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}