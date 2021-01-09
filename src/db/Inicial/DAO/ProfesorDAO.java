package db.Inicial.DAO;


import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Inicial.MODEL.Profesor;

public class ProfesorDAO {

    public static void insert(Profesor profesor, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO profesor (name, lastname) VALUES(?, ?)");
        stmt.setString(1, profesor.getNombre());
        stmt.setString(2, profesor.getApellido());
        stmt.executeUpdate();
    }

    public static List<Profesor> findAll(Connection connection) throws SQLException {
        List<Profesor> listaProfesor = new ArrayList<>();
        String sql = "SELECT * FROM PROFESOR";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            listaProfesor.add(convertirProfesor(rs));
        }

        return listaProfesor;
    }

    public static int delete(String profesor, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM PROFESOR WHERE ID = ?");
        prepStmt.setInt(1, Integer.parseInt(profesor));
        /*prepStmt.setInt(1, profesor.getId());*/
        return prepStmt.executeUpdate();
    }

    public static int update(Profesor profesor, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("UPDATE ALUMNO SET name = ?, lastname = ? " +
                "WHERE id = ?");
        prepStmt.setString(1, profesor.getNombre());
        prepStmt.setString(2, profesor.getApellido());
        prepStmt.setInt(3, profesor.getId());
        return prepStmt.executeUpdate();
    }


    private static Profesor convertirProfesor(ResultSet rs) throws SQLException {
        String nombre = rs.getString("name");
        String apellido = rs.getString("lastname");
        int id = rs.getInt("id");
        int nuevoNombre = 0;
        return new Profesor(nuevoNombre, id, nombre, apellido);
    }

    public static Profesor findByName(String nombre, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM profesor WHERE UPPER(name) = ?");
        statement.setString(1, nombre.toUpperCase());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return convertirProfesor(resultSet);
        }
        return null;
    }
}


