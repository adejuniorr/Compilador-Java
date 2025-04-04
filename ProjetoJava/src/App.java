import SimpleCalculator.SimpleCalculator;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World! Let's do some calculations!");
        try {
            SimpleCalculator calculator = new SimpleCalculator();
            double a = 10.0;
            double b = 5.0;
            double c = 8.0;
            double d = 2.0;

            double sum = calculator.add(a, b);
            double difference = calculator.subtract(a, b);
            double product = calculator.multiply(a, c);
            double quotient = calculator.divide(c, d); // This will throw an exception

            System.out.println("Soma: " + sum);
            System.out.println("Difference: " + difference);
            System.out.println("Product: " + product);
            System.out.println("Quotient: " + quotient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


