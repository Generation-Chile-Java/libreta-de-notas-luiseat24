import java.util.*;

public class LibretaDeNotas {

    private final Scanner teclado = new Scanner(System.in);
    private final Map<String, List<Double>> calificaciones = new HashMap<>();

    private static final double nota_minima = 1.0;
    private static final double nota_maxima = 7.0;
    private static final double nota_aprovatoria = 4.0;

    public static void main(String[] args) {
        LibretaDeNotas app = new LibretaDeNotas();
        app.ejecutar();
    }

    public void ejecutar(){
        int cantidadAlumnos = solicitarEntero("Ingrese la cantidad de alumnos",1,100 );
        int cantidadNotas = solicitarEntero("Ingrese la cantidad de notas por alimno",1,10);

        ingresarDatosEstudiantes(cantidadAlumnos, cantidadNotas);
        mostrarMenu();

    }

    private void ingresarDatosEstudiantes(int cantidadAlumnos, int cantidadNotas){
        for (int i = 1; i <=cantidadAlumnos; i++){
            System.out.println("NOmbre del alumno #" + i + ": ");
            String nombre = teclado.nextLine();

            List <Double> notas = new ArrayList<>();
            for (int j = 1; j <= cantidadNotas; j++){
            double nota = solicitarDecimal("Ingrese nota #" + j + "( " + nota_minima + " a "+ nota_maxima + "): ", nota_minima, nota_maxima);

             notas.add(nota);

            }
            calificaciones.put(nombre, notas);
        }

    }
    private void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n📋 Menú:");
            System.out.println("1. Mostrar promedio, nota máxima y mínima por estudiante");
            System.out.println("2. Evaluar si una nota es aprobatoria");
            System.out.println("3. Comparar una nota con el promedio del curso");
            System.out.println("0. Salir");
            opcion = solicitarEntero("Seleccione una opción: ", 0, 3);

            switch (opcion) {
                case 1 -> mostrarResumenEstudiantes();
                case 2 -> evaluarNotaEstudiante();
                case 3 -> compararConPromedioCurso();
                case 0 -> System.out.println("👋 Saliendo del sistema...");
            }
        } while (opcion != 0);
    }

    private void mostrarResumenEstudiantes() {
        for (Map.Entry<String, List<Double>> entry : calificaciones.entrySet()) {
            String nombre = entry.getKey();
            List<Double> notas = entry.getValue();

            double promedio = calcularPromedio(notas);
            double max = Collections.max(notas);
            double min = Collections.min(notas);

            System.out.printf("👨‍🎓 %s → Promedio: %.2f | Máx: %.1f | Mín: %.1f%n", nombre, promedio, max, min);
        }
    }

    private void evaluarNotaEstudiante() {
        String nombre = solicitarNombre("Ingrese el nombre del estudiante: ");
        if (!calificaciones.containsKey(nombre)) {
            System.out.println("❌ Estudiante no encontrado.");
            return;
        }

        double nota = solicitarDecimal("Ingrese la nota a evaluar: ", nota_minima, nota_maxima);
        System.out.println(nota >= nota_aprovatoria ? "✅ Aprobatoria" : "❌ Reprobatoria");
    }

    private void compararConPromedioCurso() {
        double promedioCurso = calcularPromedioGeneral();

        String nombre = solicitarNombre("Ingrese el nombre del estudiante: ");
        if (!calificaciones.containsKey(nombre)) {
            System.out.println("❌ Estudiante no encontrado.");
            return;
        }

        double nota = solicitarDecimal("Ingrese la nota a comparar: ", nota_minima, nota_maxima);
        if (nota > promedioCurso) {
            System.out.printf("📈 La nota está sobre el promedio del curso (%.2f)%n", promedioCurso);
        } else if (nota < promedioCurso) {
            System.out.printf("📉 La nota está bajo el promedio del curso (%.2f)%n", promedioCurso);
        } else {
            System.out.printf("📊 La nota es igual al promedio del curso (%.2f)%n", promedioCurso);
        }
    }

    private double calcularPromedio(List<Double> notas) {
        return notas.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    private double calcularPromedioGeneral() {
        return calificaciones.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Double::doubleValue)
                .average().orElse(0);
    }


    private int solicitarEntero(String mensaje, int minimo, int maximo){
        while (true){
            try{
                System.out.println(mensaje);
                int valor = Integer.parseInt(teclado.nextLine());
                if (valor >= minimo && valor <= maximo) return valor;
                System.out.println("Valor fuera del rango permitido");

            }catch (NumberFormatException e){
                System.out.println("Entrada invalida, Ingrese un numero entero.");
            }
        }
    }

    private double solicitarDecimal(String mensaje, double minimo, double maximo){
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(teclado.nextLine());
                if (valor >= minimo && valor <= maximo) return valor;
                System.out.println("⚠️ Valor fuera del rango permitido.");
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada inválida. Ingrese un número decimal.");
            }
        }
    }

    private String solicitarNombre(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }



}
