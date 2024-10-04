package Controller;

import Model.Validaciones.Herramientas;
import dao.implementaciones.UsuarioDAO;
import dominio.Cliente;
import dominio.Usuario;
import dominio.enums.TipoUsuario;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

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

    public String actualizar(String id, String nombreUsuario, String nombre, String contrasenia, String confirmacion, String tipoUsuario) {
        Usuario usuario;

        try {
            usuario = usuarioDAO.getUsuario(Integer.parseInt(Herramientas.extraerID(id)));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        if (usuario.isActivo() == false) {
            return "El usuario a modificar no esta activo";
        }
        if (Herramientas.validarLongitud(nombre) != true) {
            return "Error en el nombre";
        }

        if (!confirmacion.equals(contrasenia)) {
            return "Las contraseñas no coinciden";
        }

        if (!Herramientas.validarLongitud(contrasenia)){
            return "Contraseña demasiado larga";
        }

        try {
            usuarioDAO.updateUsuario(new Usuario(usuario.getId(),nombreUsuario,nombre,contrasenia, TipoUsuario.valueOf(tipoUsuario),true));
            return "Usuario Modificado";
        } catch (SQLException | IOException | ClassNotFoundException e) {
            return "Error al modificar";
            // throw new RuntimeException(e);
        }
    }

    public List<Usuario> listar () {
        try {
            return usuarioDAO.getUsuarios();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    };

    public String[] consultar (String id) {
        Usuario usuario;


        try {
            usuario = usuarioDAO.getUsuario(Integer.parseInt((Herramientas.extraerID(id))));

            if (usuario == null ) {
                return new String[] {"ID no existe"};
            }
            Field[] campos = usuario.getClass().getDeclaredFields();
            String[] datos = new String[campos.length];

            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true); // Permitir el acceso a atributos privados
                try {
                    Object valor = campos[i].get(usuario); // Obtener el valor del atributo
                    if (valor != null) {
                        datos[i - 1] = valor.toString(); // Convertir valor a String
                    } else {
                        datos[i - 1] = "null"; // Representar valores nulos como "null"
                    }
                } catch (IllegalAccessException e) {
                    datos[i - 1] = "Acceso no permitido";
                }
            }
            return datos;

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            throw new RuntimeException();
        }
    }

    public String eliminar(String id) {
        try {
            usuarioDAO.deleteUsuario(Integer.parseInt(Herramientas.extraerID(id)));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return "Usuario Eliminado";
    }


}
