import Views.Dialog.LoginDialog;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

       SwingUtilities.invokeLater(() -> {
           LoginDialog loginDialog =new LoginDialog();
           loginDialog.setVisible(true);
            });


    }
}
