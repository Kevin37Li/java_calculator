package calculator;

import javax.swing.*;
import java.util.Scanner;

public class CalculatorTester
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner("this is the first line\nthis is the second line\n");

        String first = in.nextLine();
        String second = in.nextLine();

        System.out.print(first);
        System.out.print(second);
        System.out.print("\ntest");
    }
}
