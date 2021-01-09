package db.Inicial;

import db.Inicial.DAO.InscripcionDAO;
import db.Inicial.MODEL.Alumno;
import db.Inicial.DAO.AlumnoDAO;
import db.Inicial.DAO.CursoDAO;
import db.Inicial.DAO.ProfesorDAO;
import db.Inicial.MODEL.Curso;
import db.Inicial.MODEL.Profesor;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

// Menu: 1.cursos 2:estudiantes 3:inscripciones
// Necesitamos alta, listado, modificacion y baja de alumno y de curso
// Inscripcion, cancelacion, listados (varios)

public class AppJDBC {

    public static void main(String[] args) {

        try {
            Connection connection = AdminBD.obtenerConexion("com.mysql.cj.jdbc.Driver");

            Scanner sc = new Scanner(System.in);
            System.out.println("SISTEMA DE CURSOS");
            System.out.println();
            int opcion = menuOpciones(sc);
            while (opcion != 0) {

                switch (opcion) {
                    case 1:
                        submenuCurso(sc, connection);
                        break;
                    case 2:
                        submenuAlumno(sc, connection);
                        break;
                    case 3:
                        submenuInscripcion(sc, connection);
                        break;
                    case 4:
                        submenuProfesor(sc, connection);
                        break;
                }
                opcion = menuOpciones(sc);
            }
            connection.close();

        } catch (ClassNotFoundException e) {
            System.err.println("Error en carga de Clase driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error en ejecución SQL: " + e.getMessage());
        }

    }

    private static int menuOpciones(Scanner sc) {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("1. Curso");
        System.out.println("2. Estudiantes");
        System.out.println("3. Inscripción");
        System.out.println("0. Salir");
        System.out.println();
        System.out.println("Ingrese opcion: ");
        return sc.nextInt();
    }

    private static void submenuInscripcion(Scanner sc, Connection connection) throws SQLException {
        System.out.println();
        System.out.println("Menu:");
        String curso = sc.next();
        System.out.println("1. Ingrese el nombre del curso al cual se desea inscribir: ");
        System.out.println("2. Inscribir");
        System.out.println("3. Borrar Inscripción ");
        System.out.println("0. Salir");
        System.out.println();
        System.out.println("Ingrese opcion: ");

        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                listaCursos(connection);
                break;
            case 2:
                altaInscripcion(connection);
                break;
            case 3:
                bajaInscipcion(sc, connection);
                break;
        }


    }



    private static void bajaInscipcion(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese nombre del curso al que desea inscribirse: ");
        String inscripcion = sc.next();
        InscripcionDAO.insertInscripcion(inscripcion, connection);
    }


    private static void altaInscripcion(Connection connection) {
    }

    private static void submenuProfesor(Scanner sc, Connection connection) throws SQLException {
        System.out.println();
        System.out.println("Menu:");
        String nombreProfesor = sc.next();
        System.out.println("1. Ingrese el nombre del profesor:  " + nombreProfesor);
        System.out.println("2. ver Profesores");
        System.out.println("3. Inscripción");
        System.out.println("0. Salir");
        System.out.println();
        System.out.println("Ingrese opcion: ");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                altaProfesor(sc, connection);
                break;
            case 2:
                bajaProfesor(sc, connection);
                break;
            case 3:
                modificarProfesor(sc, connection);
                break;
            case 4:
                listaProfesores(connection);
                break;

        }
    }


    private static void altaProfesor(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese nombre del profesor: ");
        String nombre = sc.next();
        System.out.println("Ingrese apellido del profesor: ");
        String apellido = sc.next();

        Profesor profesor = new Profesor(nombre, apellido);
        ProfesorDAO.insert(profesor, connection);
    }

    private static void bajaProfesor(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese el nombre del profesor para darlo de baja: ");
        String nombre = sc.next();
        Profesor profesor = ProfesorDAO.findByName(nombre, connection);

        if (profesor != null) {
            int modificado = ProfesorDAO.delete(nombre, connection);
            if (modificado == 1) {
                System.out.println("Baja realizada");
            } else {
                System.err.println("No se realizó la baja");
            }
        } else {
            System.err.println("No se encontró el profesor");
        }

    }

    private static void modificarProfesor(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese nombre del profesor a modificar:");
        String nombreProfesor = sc.next();

        Profesor profesor = ProfesorDAO.findByName(nombreProfesor, connection);
        if (profesor == null) {
            System.out.println("No existe profesor con nombre " + nombreProfesor);
        } else {
            System.out.println("Ingrese nuevo nombre del profesor:");
            String nuevoNombre = sc.next();
            profesor.setNombre(nuevoNombre);
            int updated = ProfesorDAO.update(profesor, connection);

            if (updated == 1) {
                System.out.println("Modificación realizada");
            } else {
                System.err.println("Error en la modificación de profesor: actualizados = " + updated);
            }
        }
    }

    private static void listaProfesores(Connection connection) throws SQLException {
        System.out.println("LISTA DE PROFESORES:");
        List<Profesor> listaProfesores = ProfesorDAO.findAll(connection);
        for (Profesor profesor : listaProfesores) {
            System.out.println(profesor.getNombre() + " - " + profesor.getNombre());
        }
    }

    private static void bajaCurso(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese ID del curso a borrar:");
        int idCurso = sc.nextInt();
        Curso cursoDelete = CursoDAO.findById(idCurso, connection);

        if (cursoDelete != null) {
            int modificadas = CursoDAO.delete(idCurso, connection);
            if (modificadas == 1) {
                System.out.println("Baja realizada");
            } else {
                System.err.println("No se realizó la baja");
            }
        } else {
            System.err.println("No se encontró el curso");
        }

    }

    private static void modificarCursos(Scanner sc, Connection connection) throws SQLException {

        System.out.println("Ingrese ID del curso a modificar:");
        int idCurso = sc.nextInt();
        // AGREGAR VALIDACION PARA VERICAR QUE EXISTA EL CURSO
        System.out.println("Ingrese nuevo nombre del curso:");
        String nuevoNombre = sc.next();
        // VALIDAR EL NUEVO NOMBRE -> CANTIDAD MINIMA DE CARACTERES
        Curso curso = new Curso(nuevoNombre);
        curso.setId(idCurso);
        int updated = CursoDAO.update(curso, connection);
        if (updated == 1) {
            System.out.println("Modificacion realizada");
        } else {
            System.err.println("Error en la modificacion de curso: actualizados = " + updated);
        }

    }

    private static void listaCursos(Connection connection) throws SQLException {
        System.out.println("LISTA CURSOS:");
        List<Curso> listaCursos = CursoDAO.findAll(connection);
        for (Curso curso : listaCursos) {
            System.out.println(curso.getId() + " - " + curso.getName());
        }
    }

    private static void altaCurso(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese nombre curso: ");
        String nombreCurso = sc.next();
        Curso curso = new Curso(nombreCurso);
        CursoDAO.insert(curso, connection);

    }

    private static void buscarCursoPorNombre(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Buscar curso por nombre");
        System.out.println();
        System.out.println("Ingrese nombre del curso:");
        String nombre = sc.next();
        List<Curso> listaCursos = CursoDAO.findByName(nombre, connection);
        System.out.println();
        listaCursos.forEach(curso -> System.out.println(curso.getId() + " - " + curso.getName()));

    }

    private static void submenuCurso(Scanner sc, Connection connection) throws SQLException {
       /* int menuOpcionesCurso = 1;
        while (menuOpcionesCurso == 1) {
            switch (menuOpcionesCurso) {
                case 1:
                    nuevoCurso(connection);
                    break;
                case 2:
                    mostrarCursos(connection);
                    break;
                case 3:
                    modificarCursos(sc, connection);
                case 4:
                    bajaCurso(sc, connection);

            }*/
        System.out.println();
        System.out.println("Submenu Curso:");
        System.out.println("1. Alta curso");
        System.out.println("2. Listar cursos");
        System.out.println("3. Modificar curso");
        System.out.println("4. Baja curso");
        System.out.println("5. Buscar curso por nombre");
        System.out.println("0. Salir");
        System.out.println();
        System.out.println("Ingrese opcion: ");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                altaCurso(sc, connection);
                break;
            case 2:
                listaCursos(connection);
                break;
            case 3:
                modificarCursos(sc, connection);
                break;
            case 4:
                bajaCurso(sc, connection);
                break;
            case 5:
                buscarCursoPorNombre(sc, connection);
                break;
        }
    }


    private static void submenuAlumno(Scanner sc, Connection connection) throws SQLException {
        System.out.println();
        System.out.println("Menu:");
        String nombreAlumno = sc.next();
        String apellidoAlumno = sc.next();
        String DNI = sc.next();
        System.out.println("1. Ingrese por DNI" + DNI);
        System.out.println("2. Ingrese nombre y apellido" + nombreAlumno + apellidoAlumno);
        System.out.println("3. Inscripción");
        System.out.println("0. Salir");
        System.out.println();
        System.out.println("Ingrese opcion: ");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                altaAlumno(sc, connection);
                break;
            case 2:
                bajaAlumno(sc, connection);
                break;
            case 3:
                modificarAlumno(sc, connection);
                break;
            case 4:
                bajaAlumno(sc, connection);
                break;
            case 5:
                listaAlumno(connection);
                break;
        }
    }

    private static void bajaAlumno(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese el nombre del alumno para darlo de baja: ");
        String nombreAlumno = sc.next();
        Alumno alumno = new Alumno(nombreAlumno);
        Alumno alumnoDelete = (Alumno) AlumnoDAO.findAll(connection);
        System.out.println("Ingrese el id del alumno para darlo de baja: ");
        int id = sc.nextInt();
        if (alumnoDelete != null) {
            int modificado = AlumnoDAO.deleteAlumno(id, connection);
            if (modificado == 1) {
                System.out.println("Baja realizada");
            } else {
                System.err.println("No se realizó la baja");
            }
        } else {
            System.err.println("No se encontró el alumno");
            int modificado = AlumnoDAO.deleteAlumno(id, connection);
            if (modificado == 1) {
                System.out.println("Baja realizada");
            } else {
                System.err.println("No se realizó la baja");
            }
        }

    }

    private static void altaAlumno(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese nombre del alumno: ");
        String nombreAlumno = sc.next();
        System.out.println("Ingrese apellido del alumno: ");
        String apellido = sc.next();
        AlumnoDAO.insertAlumno(new Alumno(nombreAlumno), connection);
    }

    private static void modificarAlumno(Scanner sc, Connection connection) throws SQLException {
        System.out.println("Ingrese nombre del alumno a modificar:");
        String nombreAlumno = sc.next();
        System.out.println("Ingrese nuevo nombre del alumno:");
        String nuevoNombre = sc.next();
        Alumno alumno = new Alumno(nuevoNombre);
        alumno.setNombre(nombreAlumno);
        int updated = AlumnoDAO.updateAlumno(alumno, connection);
        if (updated == 1) {
            System.out.println("Modificación realizada");
        } else {
            System.err.println("Error en la modificación de profesor: actualizados = " + updated);
        }

    }

    private static void listaAlumno(Connection connection) throws SQLException {
        System.out.println("LISTA ALUMNOS:");
        List<Alumno> listaAlumno = AlumnoDAO.findAll(connection);
        for (Alumno alumno : listaAlumno) {
            System.out.println(alumno.getId() + " - " + alumno.getNombre());
        }
    }
}


    /*private static void nuevoCurso(Connection connection) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nombre del curso: ");
        String cursoName = scan.next();
        Curso nuevoCurso = new Curso(cursoName);
        CursoDAO.insert(nuevoCurso, connection);
        System.out.println("The course wad added succesfully");

    }

    private static void mostrarCursos(Connection connection) throws SQLException {
        System.out.println("Mostrar Cursos disponibles:");
        CursoDAO.findAll(connection);
        List<Curso> listaCursos = CursoDAO.findAll(connection);
        for (Curso course : listaCursos) {
            System.out.println(course.getId() + ": " + course.getName() + " ");
        }
    }*/




