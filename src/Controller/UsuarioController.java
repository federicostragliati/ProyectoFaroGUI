package Controller;

import dao.implementaciones.UsuarioDAO;
import dominio.Usuario;

import java.io.IOException;
import java.sql.SQLException;

public class UsuarioController {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String crear(String nombreUsuario, String nombre, String contraseña, String TipoUsuario) {
        Usuario usuario = new Usuario();
        try {
            usuarioDAO.createUsuario(usuario);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String actualizar() {}

    public String eliminar() {}
}
