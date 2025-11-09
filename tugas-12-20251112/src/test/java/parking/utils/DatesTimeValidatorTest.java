/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Parkir Pelabuhan
 * Modul            : DatesTimeValidator.java
 * Deskripsi        : Kelas untuk melakukan unit test pada DatesTimeValidator.
 * Tanggal          : 11 November 2025
 */

package parking.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Date and Time Validator Test")
public class DatesTimeValidatorTest {
    @Test
    @DisplayName("validateYear: accepts positive year")
    public void validateYear_acceptsPositive() {
        assertDoesNotThrow(() -> DatesTimeValidator.validateYear(1));
        assertDoesNotThrow(() -> DatesTimeValidator.validateYear(2024));
    }

    @Test
    @DisplayName("validateYear: rejects zero or negative year")
    public void validateYear_rejectsNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateYear(0));
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateYear(-100));
    }

    @Test
    @DisplayName("validateMonth: accepts months in range 1..12")
    public void validateMonth_acceptsRange() {
        assertDoesNotThrow(() -> DatesTimeValidator.validateMonth(1));
        assertDoesNotThrow(() -> DatesTimeValidator.validateMonth(12));
        assertDoesNotThrow(() -> DatesTimeValidator.validateMonth(6));
    }

    @Test
    @DisplayName("validateMonth: rejects out-of-range months")
    public void validateMonth_rejectsOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateMonth(0));
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateMonth(13));
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateMonth(-5));
    }

    @Test
    @DisplayName("validateDay: accepts valid day for 30-day and 31-day months")
    public void validateDay_acceptsValidForMonths() {
        // April (30 days)
        assertDoesNotThrow(() -> DatesTimeValidator.validateDay(2023, 4, 30));
        // July (31 days)
        assertDoesNotThrow(() -> DatesTimeValidator.validateDay(2023, 7, 31));
    }

    @Test
    @DisplayName("validateDay: rejects invalid day for month")
    public void validateDay_rejectsInvalidForMonth() {
        // April has 30 days, 31 should fail
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateDay(2023, 4, 31));
        // September has 30 days, 0 should fail
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateDay(2023, 9, 0));
    }

    @Test
    @DisplayName("validateDay: handles February in leap year")
    public void validateDay_handlesFebruaryLeapYear() {
        // 2020 is leap year
        assertDoesNotThrow(() -> DatesTimeValidator.validateDay(2020, 2, 29));
        // 2020 day 30 is invalid
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateDay(2020, 2, 30));
    }

    @Test
    @DisplayName("validateDay: rejects February 29 on non-leap year")
    public void validateDay_rejectsFeb29OnNonLeapYear() {
        // 2021 is not leap year
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateDay(2021, 2, 29));
    }

    @Test
    @DisplayName("validateHours: accepts range 0..23 and rejects outside")
    public void validateHours_range() {
        assertDoesNotThrow(() -> DatesTimeValidator.validateHours(0));
        assertDoesNotThrow(() -> DatesTimeValidator.validateHours(23));
        assertDoesNotThrow(() -> DatesTimeValidator.validateHours(12));

        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateHours(-1));
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateHours(24));
    }

    @Test
    @DisplayName("validateMinutes: accepts range 0..59 and rejects outside")
    public void validateMinutes_range() {
        assertDoesNotThrow(() -> DatesTimeValidator.validateMinutes(0));
        assertDoesNotThrow(() -> DatesTimeValidator.validateMinutes(59));
        assertDoesNotThrow(() -> DatesTimeValidator.validateMinutes(30));

        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateMinutes(-1));
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateMinutes(60));
    }

    @Test
    @DisplayName("validateSeconds: accepts range 0..59 and rejects outside")
    public void validateSeconds_range() {
        assertDoesNotThrow(() -> DatesTimeValidator.validateSeconds(0));
        assertDoesNotThrow(() -> DatesTimeValidator.validateSeconds(59));
        assertDoesNotThrow(() -> DatesTimeValidator.validateSeconds(45));

        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateSeconds(-1));
        assertThrows(IllegalArgumentException.class, () -> DatesTimeValidator.validateSeconds(60));
    }
}
