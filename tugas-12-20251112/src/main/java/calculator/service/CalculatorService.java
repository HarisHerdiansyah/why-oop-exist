/**
 * Nama             : Raissa Christabel Sebayang - 140810240008
 *                    Abraham Gomes Samosir - 140810240044
 *                    Haris Herdiansyah - 140810240074
 * Program Utama    : Aplikasi Kalkulator Scientific
 * Modul            : CalculatorService.java
 * Deskripsi        : Kelas layanan untuk melakukan perhitungan matematika menggunakan library mXparser.
 * Tanggal          : 11 November 2025
 */

package calculator.service;

import org.mariuszgromada.math.mxparser.*;

public class CalculatorService {
    public CalculatorService() {
    }

    public void setDegreesMode() {
        mXparser.setDegreesMode();
    }

    public void setRadiansMode() {
        mXparser.setRadiansMode();
    }

    public boolean isDegree() {
        return mXparser.checkIfDegreesMode();
    }

    public double calculateExpression(String expressionString) {
        License.iConfirmNonCommercialUse("CalculatorService User");
        Expression expr = new Expression(expressionString);
        return expr.calculate();
    }
}
