package db.Inicial.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Inicial.MODEL.Alumno;

public class AlumnoDAO {

    public static void insertAlumno(Alumno alumno, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO ALUMNO (NOMBRE) VALUES(?)");
        stmt.setString(1, alumno.getNombre());
        stmt.setString(1, alumno.getApellido());
        stmt.executeUpdate();
    }

    public static List<Alumno> findAll(Connection connection) throws SQLException {
        List<Alumno> listaAlumno = new ArrayList<Alumno>();
        String sql = "SELECT * FROM ALUMNO";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nombreAlumno = rs.getString(2);
            String apellidoAlumno =rs.getString(3);
            int idAlumno = rs.getInt(1);
            Alumno alumno = new Alumno(nombreAlumno, apellidoAlumno);
            alumno.setId(idAlumno);
            listaAlumno.add(alumno);
        }

        return listaAlumno;
    }

    public static int deleteAlumno(int id, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM ALUMNO WHERE id = ?");
        prepStmt.setInt(1, id);
        return prepStmt.executeUpdate();
    }

    public static int updateAlumno(Alumno alumno, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("UPDATE ALUMNO SET NAME = ?, SET LASTNAME = ?, WHERE idAlumno = ?");
        prepStmt.setString(1, alumno.getNombre());
        prepStmt.setInt(2, alumno.getId());
        prepStmt.setString(3, alumno.getApellido());
        return prepStmt.executeUpdate();
    }

    public static List<Alumno> findByName(String nombre, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT  * FROM ALUMNO WHERE name LIKE ? ORDER BY NAME");
        stmt.setString(1, "%" + nombre + "%");
        ResultSet rs = stmt.executeQuery();
        List<Alumno> lista = new ArrayList<Alumno>();
        while (rs.next()) {
            lista.add(convertirAlumno(rs));
        }
        return lista;
    }

    public static Alumno findById(int id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM ALUMNO WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Alumno alumno = null;
        if (rs.next()) {
            alumno = convertirAlumno(rs);
        }
        return alumno;
    }

    public static Alumno findByLastName(String apellido, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ALUMNO WHERE lastname = ? ORDER BY lastname");
        stmt.setString(1, "%" + apellido + "%");
        ResultSet rs = stmt.executeQuery();
        Alumno alumno = null;
        if (rs.next()) {
            alumno = convertirAlumno(rs);
        }
        return alumno;
    }

    private static Alumno convertirAlumno(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("name");
        String apellido = rs.getString("lastName");
        return new Alumno(id, nombre, apellido);
    }

}

