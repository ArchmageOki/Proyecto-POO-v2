package src;

public class Persona extends EntidadBase {

    private String apellido1;
    private String apellido2;
    private String dni;
    private int edad;
    private String sexo;
    private double dinero;
    private static int contador = 0;
    private int id;

    /**
     * @param nombre    El nombre de la persona.
     * @param apellido1 El primer apellido de la persona.
     * @param apellido2 El segundo apellido de la persona.
     * @param dni       El documento de identidad de la persona. Si se introduce un
     *                  DNI no válido su valor será "DNI INCORRECTO".
     * @param edad      La edad de la persona.
     * @param sexo      El sexo de la persona. Siempre es "Hombre" a no ser que se
     *                  introduzca "Mujer".
     */
    public Persona(String nombre, String apellido1, String apellido2, String dni, int edad, String sexo) {
        super();
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        setDni(dni);
        setEdad(edad);
        setSexo(sexo);
        this.dinero = 0.0;
        this.dinero = (double) Math.round(dinero * 100 / 100);
        this.id = Persona.contador;
        Persona.contador++;

        addToCooperativa();
    }

    /**
     * @return ID de la persona.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return String de nombre.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * @param nombre El nombre nuevo de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return String de apellido1.
     */
    public String getApellido1() {
        return this.apellido1;
    }

    /**
     * @param apellido1 El apellido1 nuevo de la persona.
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * @return String de apellido2.
     */
    public String getApellido2() {
        return this.apellido2;
    }

    /**
     * @param apellido2 El apellido2 nuevo de la persona.
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * @return String del DNI.
     */
    public String getDni() {
        return this.dni;
    }

    /**
     * @param dni Es el nuevo DNI de la persona. Si no es un DNI correcto, el valor
     *            de DNI pasa a ser "DNI INCORRECTO".
     */
    public void setDni(String dni) {
        if (esDniCorrecto(dni)) {
            this.dni = dni;
        } else {
            this.dni = "DNI INCORRECTO";
        }
    }

    /**
     * @return True si el DNI tiene un formato válido && su letra es correcta.
     */
    private boolean esDniCorrecto(String dni) {
        if (!esFormatoValido(dni)) {
            return false;
        }

        char letraCalculada = calcularLetraDni(dni);

        char letraDni = dni.charAt(8);

        return letraCalculada == letraDni;
    }

    /**
     * @return True si dni se corresponde con una cadena de 8 números y una letra.
     */
    private boolean esFormatoValido(String dni) {
        return dni.matches("\\d{8}[a-zA-Z]");
    }

    /**
     * @return Letra que se corresponde con la numeración del dni.
     */
    private char calcularLetraDni(String dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

        int numero = Integer.parseInt(dni.substring(0, 8));

        int indice = numero % 23;

        return letras.charAt(indice);
    }

    /**
     * @return Edad de la persona
     */
    public int getEdad() {
        return this.edad;
    }

    /**
     * @param edad La edad de la persona. Debe estar comprendida entre 18 y 99 años.
     */
    public void setEdad(int edad) {
        this.edad = edad;
        if (edad < 18) {
            System.out.println("Un menor de edad no puede estar dado de alta.");
            System.out.println("Se establecerá la edad en 18 años.");
            this.edad = 18;
        } else if (edad > 99) {
            System.out.println("La edad no puede ser mayor a 100.");
            System.out.println("Se establecerá la edad en 99 años.");
            this.edad = 99;
        }
    }

    /**
     * @return Sexo de la persona
     */
    public String getSexo() {
        return this.sexo;
    }

    /**
     * @param sexo Sexo de la persona. Por defecto es "Hombre". Si sexo == "Mujer",
     *             se establece como mujer.
     */
    public void setSexo(String sexo) {
        if ("Mujer".equals(sexo)) {
            this.sexo = "Mujer";
        } else {
            this.sexo = "Hombre";
        }
    }

    /**
     * @return Dinero de la persona.
     */
    public double getDinero() {
        return this.dinero;
    }

    /**
     * @return El dinero que ha obtenido la persona de vender sus productos.
     */
    public void printDinero() {
        System.out.printf("%.2f €\n", this.dinero);
    }

    /**
     * @param dinero Cantidad de dinero que recibe la persona.
     */
    public void addDinero(double dinero) {
        if (dinero < 0) {
            System.out.println("No se puede añadir una cantidad negativa de dinero.");
            this.dinero += 0;
        } else {
            dinero = Math.round(dinero * 100) / 100.0;
            this.dinero += dinero;
        }
    }

    /**
     * Añade a la persona a la lista de personas de la cooperativa.
     */
    protected void addToCooperativa() {
        Cooperativa.addPersona(this);
    }

    /**
     * Devuelve null porque no es un método de Persona. El método lo usará la clase
     * hija ConsumidorFinal.
     */
    public String getDireccion() {
        return null;
    }

    /**
     * Devuelve null porque no es un método de Persona. El método lo usará la clase
     * hija ConsumidorFinal.
     */
    public String getCodigoPostal() {
        return null;
    }
}
