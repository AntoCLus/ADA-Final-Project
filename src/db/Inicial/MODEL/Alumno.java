package db.Inicial.MODEL;

public class Alumno {

    private int idAlumno;
    private String nombreAlumno;
    private String apellidoAlumno;


  /* public Alumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }*/


    public Alumno(int idAlumno,  String nombreAlumno, String apellidoAlumno) {
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.apellidoAlumno = apellidoAlumno;
    }

    public Alumno(String nombreAlumno) {
    }

    public Alumno(int idAlumno) {
    }

    public Alumno(String nombreAlumno, String apellidoAlumno) {
    }

    /*public Alumno(String string, String string1) {
    }*/


    public int getId() {
        return idAlumno;
    }

    public void setId(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombreAlumno;
    }

    public void setNombre(String nombre) {
        this.nombreAlumno = nombre;
    }

    public String getApellido() {
        return apellidoAlumno;
    }

    public void setApellido(String apellido) {
        this.apellidoAlumno = apellido;
    }

    
}
