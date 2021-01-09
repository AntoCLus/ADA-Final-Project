package db.Inicial.DAO;

import db.Inicial.MODEL.Alumno;
import db.Inicial.MODEL.Curso;
import db.Inicial.MODEL.Inscripcion;
import db.Inicial.MODEL.Profesor;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InscripcionDAO {

    public static void insertInscripcion(String inscripcion, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO inscripcion(" +
                "id_alumno, id_curso, id_profesor, estado, comision) VALUES(?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, inscripcion.getAlumno().getId());
        stmt.setInt(2, inscripcion.getCurso().getId());
        stmt.setInt(3, inscripcion.getProfesor().getId());
        stmt.setInt(4, inscripcion.getEstado());
        stmt.setString(5, inscripcion.getComision());
        stmt.executeUpdate();

    }

    public static void borrarInscripcion(Inscripcion inscripcion, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM inscripcion WHERE id = ?");
        prepStmt.setInt(1, inscripcion.getId());
        prepStmt.executeUpdate();
    }

    public static int cancelarInscripcion(Inscripcion inscripcion, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("UPDATE INSCRIPCION SET estado = 0 WHERE id = ?");
        prepStmt.setInt(1, inscripcion.getId());
        return prepStmt.executeUpdate();
    }

    public void findAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM inscripcion");
        ResultSet resultSet = statement.executeQuery();
        List<Inscripcion> inscripcionList = new ArrayList<>();
        while (resultSet.next()) {
            inscripcionList.add(convertir(resultSet));
        }
    }

    public Inscripcion convertir(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int idAlumno = resultSet.getInt("id_alumno");
        int idCurso = resultSet.getInt("id_curso");
        int idProfesor = resultSet.getInt("id_profesor");
        int estado = resultSet.getInt("estado");
        int nota1 = resultSet.getInt("nota1");
        int nota2 = resultSet.getInt("nota2");
        String comision = resultSet.getString("comision");
        String nuevoNombre = null;
        return new Inscripcion(id, new Curso(idCurso), new Alumno(idAlumno), new Profesor(idProfesor, nuevoNombre),
                nota1, nota2, comision, estado);
    }


}

