package db.Inicial.MODEL;

public class Inscripcion {

        private int id;
        private Curso curso;
        private Alumno alumno;
        private Profesor profesor;
        private int nota1;
        private int nota2;
        private String comision;
        private int estado;

        public Inscripcion(int id, Curso curso, Alumno alumno, Profesor profesor, int nota1, int nota2, String comision, int estado) {
            this.id = id;
            this.curso = curso;
            this.alumno = alumno;
            this.profesor = profesor;
            this.nota1 = nota1;
            this.nota2 = nota2;
            this.comision = comision;
            this.estado = estado;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Curso getCurso() {
            return curso;
        }

        public void setCurso(Curso curso) {
            this.curso = curso;
        }

        public Alumno getAlumno() {
            return alumno;
        }

        public void setAlumno(Alumno alumno) {
            this.alumno = alumno;
        }

        public Profesor getProfesor() {
            return profesor;
        }

        public void setProfesor(Profesor profesor) {
            this.profesor = profesor;
        }

        public int getNota1() {
            return nota1;
        }

        public void setNota1(int nota1) {
            this.nota1 = nota1;
        }

        public int getNota2() {
            return nota2;
        }

        public void setNota2(int nota2) {
            this.nota2 = nota2;
        }

        public String getComision() {
            return comision;
        }

        public void setComision(String comision) {
            this.comision = comision;
        }

        public int getEstado() {
            return estado;
        }

        public void setEstado(int estado) {
            this.estado = estado;
        }
    }
