package Controller;

import Model.Validaciones.Herramientas;
import dao.implementaciones.UsuarioDAO;
import dominio.Usuario;
import dominio.enums.TipoUsuario;

import java.io.IOException;
import java.sql.SQLException;

public class UsuarioController {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String crear(String nombreUsuario, String nombre, String contrasenia, String confirmacion, String tipoUsuario) {

        if (!Herramientas.validarLongitud(nombreUsuario)) {
            return "Error en Usuario";
        }

        if (!Herramientas.validarLongitud(nombre)) {
            return "Error en Nombre";
        }

        if (!confirmacion.equals(contrasenia)) {
            return "Las contraseñas no coinciden";
        }

        if (!Herramientas.validarLongitud(contrasenia)){
            return "Contraseña demasiado larga";
        }

        try {
            usuarioDAO.createUsuario(new Usuario(nombreUsuario, nombre,contrasenia, dominio.enums.TipoUsuario.valueOf(tipoUsuario),true));
            return "Usuario Generado";
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Usuario autenticar(String usuario, String password) {
        return usuarioDAO.autenticarUsuario(usuario, password);
    }
    /*

    public String actualizar() {}


    public String eliminar() {}

    */
}
