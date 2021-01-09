package db.Inicial.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Inicial.MODEL.Curso;

public class CursoDAO {

    public static void insert(Curso curso, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO CURSO (NAME) VALUES(?)");
        stmt.setString(1, curso.getName());
        stmt.executeUpdate();
    }

    public static List<Curso> findAll(Connection connection) throws SQLException {
        List<Curso> listaCursos = new ArrayList<Curso>();
        String sql = "SELECT * FROM CURSO";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nombreCurso = rs.getString(2);
            int idCurso = rs.getInt(1);
            Curso curso = new Curso(nombreCurso);
            curso.setId(idCurso);
            listaCursos.add(curso);
        }

        return listaCursos;
    }

    public static int delete(int idCurso, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("DELETE FROM CURSO WHERE ID = ?");
        prepStmt.setInt(1, idCurso);
        return prepStmt.executeUpdate();
    }

    public static int update(Curso curso, Connection connection) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("UPDATE CURSO SET NAME = ? WHERE ID = ?");
        prepStmt.setString(1, curso.getName());
        prepStmt.setInt(2, curso.getId());
        return prepStmt.executeUpdate();
    }

    public static List<Curso> findByName(String name, Connection connection) throws SQLException {

        String sql2 = "SELECT * FROM CURSO WHERE NAME LIKE '%" + name + "%' ORDER BY NAME";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql2);

        List<Curso> lista = new ArrayList<Curso>();
        Curso curso = null;
        while (rs.next()) {
            curso = new Curso(rs.getInt(1), rs.getString(2));
            lista.add(curso);
        }
        return lista;
    }

    public static Curso findById(int id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM CURSO WHERE ID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Curso curso = null;
        if (rs.next()) {
            curso = new Curso(rs.getInt(1), rs.getString(2));
        }
        return curso;
    }
}

