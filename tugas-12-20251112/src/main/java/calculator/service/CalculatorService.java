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
