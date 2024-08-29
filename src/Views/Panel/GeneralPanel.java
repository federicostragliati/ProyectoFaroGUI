package Views.Panel;

import javax.swing.*;

public abstract class GeneralPanel extends JPanel {


    JButton createButton;
    JButton consultButton;
    JButton modifyButton;
    JButton listButton;
    JButton deleteButton;

    public GeneralPanel(String boton1, String boton2, String boton3, String boton4 , String boton5) {

        createButton = new JButton(boton1);
        createButton.setBounds(76, 46, 151, 44);

        consultButton = new JButton(boton2);
        consultButton.setBounds(237, 46, 151, 44);

        modifyButton = new JButton(boton3);
        modifyButton.setBounds(398, 46, 151, 44);

        listButton = new JButton(boton4);
        listButton.setBounds(559, 46, 151, 44);

        deleteButton = new JButton(boton5);
        deleteButton.setBounds(720, 46, 155, 44);

        setLayout(null);

        add(createButton);
        add(consultButton);
        add(modifyButton);
        add(listButton);
        add(deleteButton);

    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getConsultButton() {
        return consultButton;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JButton getListButton() {
        return listButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}


