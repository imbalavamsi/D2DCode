import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DayOfWeekCalculator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a date (MM/dd/yyyy): ");
        String inputDate = scanner.nextLine();

        try {
            String dayOfWeek = getDayOfWeek(inputDate);
            System.out.println("The day of the week for " + inputDate + " is " + dayOfWeek);
        } catch (IllegalArgumentException | DateTimeException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String getDayOfWeek(String inputDate) {
        if (inputDate == null || inputDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Input date is null or empty.");
        }

        try {
            LocalDate date = LocalDate.parse(inputDate, DATE_FORMATTER);
            return date.getDayOfWeek().toString();
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date format. Please use the format MM/dd/yyyy.");
        }
    }
}
