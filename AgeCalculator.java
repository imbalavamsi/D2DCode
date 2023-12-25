import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class AgeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your birthdate (MM/dd/yyyy): ");
        String userInput = scanner.next();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date birthDate = dateFormat.parse(userInput);
            Date currentDate = new Date();

            AgeInfo ageInfo = calculateAge(birthDate, currentDate);

            if (ageInfo != null) {
                System.out.println("Exact age: " + ageInfo.getYears() + " years, " + ageInfo.getMonths() + " months, "
                        + ageInfo.getDays() + " days.");

                System.out.println("\nAdditional Information:");
                System.out.println("Total number of hours: " + ageInfo.getTotalHours());
                System.out.println("Total number of days: " + ageInfo.getTotalDays());
                System.out.println("Total number of weeks: " + ageInfo.getTotalWeeks());
                System.out.println("Total number of months: " + ageInfo.getTotalMonths());
                System.out.println("Total number of years: " + ageInfo.getTotalYears());
            } else {
                System.out.println("Invalid birthdate. Please enter a valid date.");
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format MM/dd/yyyy.");
        }
    }

    private static AgeInfo calculateAge(Date birthDate, Date currentDate) {
        if (birthDate == null || currentDate == null || currentDate.before(birthDate)) {
            return null;
        }

        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        int years = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
        int months = currentCalendar.get(Calendar.MONTH) - birthCalendar.get(Calendar.MONTH);
        int days = currentCalendar.get(Calendar.DAY_OF_MONTH) - birthCalendar.get(Calendar.DAY_OF_MONTH);

        if (days < 0) {
            // Borrow a month
            int lastMonthMaxDay = birthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            days += lastMonthMaxDay;
            months--;
        }

        if (months < 0) {
            // Borrow a year
            months += 12;
            years--;
        }

        long totalDays = daysBetween(birthCalendar, currentCalendar);

        int weeks = (int) (totalDays / 7);

        long hours = hoursBetween(birthCalendar, currentCalendar);

        return new AgeInfo(years, months, weeks, days, hours, totalDays);
    }

    private static long daysBetween(Calendar startDate, Calendar endDate) {
        long startMillis = startDate.getTimeInMillis();
        long endMillis = endDate.getTimeInMillis();
        return (endMillis - startMillis) / (24 * 60 * 60 * 1000);
    }

    private static long hoursBetween(Calendar startDate, Calendar endDate) {
        long startMillis = startDate.getTimeInMillis();
        long endMillis = endDate.getTimeInMillis();
        return (endMillis - startMillis) / (60 * 60 * 1000);
    }

    private static class AgeInfo {
        private final int years;
        private final int months;
        private final int weeks;
        private final int days;
        private final long hours;
        private final long totalDays;

        public AgeInfo(int years, int months, int weeks, int days, long hours, long totalDays) {
            this.years = years;
            this.months = months;
            this.weeks = weeks;
            this.days = days;
            this.hours = hours;
            this.totalDays = totalDays;
        }

        public int getYears() {
            return years;
        }

        public int getMonths() {
            return months;
        }

        public int getWeeks() {
            return weeks;
        }

        public int getDays() {
            return days;
        }

        public long getHours() {
            return hours;
        }

        public long getTotalDays() {
            return totalDays;
        }

        public long getTotalHours() {
            return totalDays * 24;
        }

        public long getTotalWeeks() {
            return totalDays / 7;
        }

        public long getTotalMonths() {
            return totalDays / 30;
        }

        public long getTotalYears() {
            return totalDays / 365;
        }
    }
}
