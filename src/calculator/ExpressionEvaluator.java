package calculator;

/**
 * This class can compute an arithmetic expression and return the value
 */
public class ExpressionEvaluator
{
    private ExpressionTokenizer tokenizer;

    /**
     * Construct an ExpressionEvaluator with the arithmetic expression entered by the user
     * @param expressionInput a string containing the math expression
     */
    public ExpressionEvaluator(String expressionInput)
    {
        tokenizer = new ExpressionTokenizer(expressionInput);
    }

    /**
     * This method computes an arithmetic expression
     * @return the value of that expression
     */
    public double getExpressionValue()
    {
        double term1 = getTermValue();

        boolean done = false;

        // if not done, keeps looking for terms to add or minus
        while (!done)
        {
            String nextToken = tokenizer.peekToken();

            if ("+".equals(nextToken) || "-".equals(nextToken))
            {
                // consumes "+" or "-" sign
                tokenizer.getNextToken();
                double term2 = getTermValue();

                if (nextToken.equals("+"))
                {
                    term1 += term2;
                }
                else
                {
                    term1 -= term2;
                }
            }
            else
            {
                done = true;
            }
        }

        return term1;

    }

    /**
     * This method computes a arithmetic term consisting of factors
     * @return the value of that term
     */
    private double getTermValue()
    {
        double factor1 = getFactorValue();
        boolean done = false;

        // if not done, keeps looking for factors to multiply or divide
        while (!done)
        {
            String nextToken = tokenizer.peekToken();

            if ("*".equals(nextToken) || "/".equals(nextToken))
            {
                // consumes "*" or "/" sign
                tokenizer.getNextToken();
                double factor2 = getFactorValue();

                if (nextToken.equals("*"))
                {
                    factor1 *= factor2;
                }
                else
                {
                    factor1 /= factor2;
                }
            }
            else
            {
                done = true;
            }
        }

        return factor1;
    }

    /**
     * This method identifies if a factor is a number or another arithmetic expression enclosed
     * within parentheses
     * @return a number or another expression
     */
    private double getFactorValue()
    {
        double value;
        String nextToken = tokenizer.peekToken();

        if ("(".equals(nextToken))
        {
            // Consumes "("
            tokenizer.getNextToken();
            value = getExpressionValue();
            // Consumes ")"
            tokenizer.getNextToken();
        }
        else
        {
            value = Double.parseDouble(tokenizer.getNextToken());
        }

        return value;
    }
}

/**
 * This class breaks down the expression into tokens: numbers, parentheses and operators
 */
class ExpressionTokenizer
{
    private String input;
    // the start of the next token
    private int start;
    // one position after the end of the next token
    private int end;

    public ExpressionTokenizer(String input)
    {
        // sets the input to the expression we want to process
        this.input = input;
        start = 0;
        end = 0;

        // gets the first null token, so the next will be the first token if the input
        // expresion is not empty
        getNextToken();
    }

    /**
     * Peeks the next token without moving the start index to the next one (without consuming the
     * next token)
     * @return the next token or null if reached the end of the expression
     */
    public String peekToken()
    {
        if (start >= input.length())
        {
            return null;
        }
        else
        {
            return input.substring(start, end);
        }
    }

    /**
     * Gets the next token and move the start index to the next token
     * @return
     */
    public String getNextToken()
    {
        // gets the next token
        String nextToken = peekToken();

        // moves the start index to the next token
        start = end;

        // if there are no more tokens left, return null (protects the index going out of boundary)
        if (start >= input.length())
        {
            return nextToken;
        }

        // checks if the first character is a digit
        if (Character.isDigit(input.charAt(start)) || input.charAt(start) == '-')
        {
            end = start + 1;

            // if the next character is still a digit or a decimal place, sets the end index one
            // position after the end of the number
            while (end < input.length() &&
                    (Character.isDigit(input.charAt(end)) || input.charAt(end) == '.'))
            {
                end++;
            }

        }
        // if the first character is not a digit
        else
        {
            end = start + 1;
        }

        return nextToken;
    }
}
