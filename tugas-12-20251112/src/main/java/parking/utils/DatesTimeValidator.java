package parking.utils;

public class DatesTimeValidator {
    private final static int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    static public void validateYear(int year) throws IllegalArgumentException {
        if (year <= 0) {
            throw new IllegalArgumentException("Nilai tahun harus lebih besar dari 0.");
        }
    }

    static public void validateMonth(int month) throws IllegalArgumentException {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Nilai bulan harus antara 1 hingga 12.");
        }
    }

    static public void validateDay(int year, int month, int day) throws IllegalArgumentException {
        int maxDays = daysInMonth[month - 1];
        if (month == 2 && isLeapYear(year)) {
            maxDays = 29;
        }

        if (day < 1 || day > maxDays) {
            throw new IllegalArgumentException("Nilai hari tidak valid untuk bulan dan tahun yang diberikan.");
        }
    }

    static public void validateHours(int hours) throws IllegalArgumentException {
        if (hours < 0 || hours > 23) {
            throw new IllegalArgumentException("Nilai jam harus antara 0 hingga 23.");
        }
    }

    static public void validateMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Nilai menit harus antara 0 hingga 59.");
        }
    }

    static public void validateSeconds(int seconds) throws IllegalArgumentException {
        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException("Nilai detik harus antara 0 hingga 59.");
        }
    }
}
