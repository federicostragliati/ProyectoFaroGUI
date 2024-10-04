import Views.Frame.VistaPrincipalAdministrador;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            VistaPrincipalAdministrador vistaPrincipalAdministrador = new VistaPrincipalAdministrador();
            vistaPrincipalAdministrador.setVisible(true);
        });
    }
}
