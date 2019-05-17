package calculator;

import javax.swing.*;

public class CalculatorTester
{
    public static void main(String[] args)
    {
        ExpressionTokenizer expression = new ExpressionTokenizer("(1+2)*36");

        System.out.println(expression.nextToken());
        System.out.println(expression.peekToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
        System.out.println(expression.nextToken());
    }
}
