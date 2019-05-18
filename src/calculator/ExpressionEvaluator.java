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

    public double getExpressionValue()
    {
        double value = getTermValue();

        boolean done = false;

        while (!done)
        {
            String nextToken = tokenizer.peekToken();

            if ("+".equals(nextToken) || "-".equals(nextToken))
            {
                tokenizer.getNextToken();
                double value2 = getTermValue();

                if (nextToken.equals("+"))
                {
                    value += value2;
                }
                else
                {
                    value -= value2;
                }
            }
            else
            {
                done = true;
            }
        }

        return value;

    }

    private double getTermValue()
    {
        double value = getFactorValue();
        boolean done = false;

        while (!done)
        {
            String nextToken = tokenizer.peekToken();

            if ("*".equals(nextToken) || "/".equals(nextToken))
            {
                tokenizer.getNextToken();
                double value2 = getFactorValue();

                if (nextToken.equals("*"))
                {
                    value *= value2;
                }
                else
                {
                    value /= value2;
                }
            }
            else
            {
                done = true;
            }
        }

        return value;
    }

    private double getFactorValue()
    {
        double value;
        String nextToken = tokenizer.peekToken();

        if ("(".equals(nextToken))
        {
            tokenizer.getNextToken();
            value = getExpressionValue();
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
        if (Character.isDigit(input.charAt(start)))
        {
            end = start + 1;

            // if the next character is still a digit, sets the end index one position after the
            // end of the number
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
