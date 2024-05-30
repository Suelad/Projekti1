import java.util.Stack;

public class ProjectNumberOne {
    public static int evaluateExpression(String expression) {
        Stack<Integer> operands_Stack = new Stack<>();
        Stack<Character> operators_Stack = new Stack<>();
        int i = 0;
        while (i < expression.length()) {
            char k = expression.charAt(i);
            if (Character.isWhitespace(k)) {
                i++;
                continue;
            }
            if (Character.isDigit(k)) {
                int nr = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    nr = nr * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                operands_Stack.push(nr);
                i--; 
            }
            else if (k == '(') {
                operators_Stack.push(k);
            }
            else if (k == ')') {
                while (operators_Stack.peek() != '(') {
                    processTheOperator(operands_Stack, operators_Stack);
                } 
                operators_Stack.pop(); 
            }
            else if (isOperator(k)) {
                while (!operators_Stack.isEmpty() && precedence(operators_Stack.peek()) >= precedence(k)) {
                    if (k == '^' && operators_Stack.peek() == '^') break;
                    processTheOperator(operands_Stack, operators_Stack);
                }
                operators_Stack.push(k);
            }
            i++;
        }
        while (!operators_Stack.isEmpty()) {
            processTheOperator(operands_Stack, operators_Stack);
        }
        return operands_Stack.pop();
    }
    
    private static boolean isOperator(char k) {
        return k == '+' || k == '-' || k == '*' || k == '/' || k == '^' || k == '%';
    }
 
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
        }
        return -1; 
    }
 
    private static void processTheOperator(Stack<Integer> operandStack, Stack<Character> operatorStack) {
        char operator = operatorStack.pop();
        int operator_2 = operandStack.pop();
        int operator_1 = operandStack.pop();
        int result = 0;

        switch (operator) {
            case '+':
                result = operator_1 + operator_2;
                break;
            case '-':
                result = operator_1 - operator_2;
                break;
            case '*':
                result = operator_1 * operator_2;
                break;
            case '/':
                result = operator_1 / operator_2;
                break;
            case '%':
                result = operator_1 % operator_2;
                break;
            case '^':
                result = (int) Math.pow(operator_1, operator_2);
                break;
        }
 
 
        operandStack.push(result);
    }
 
    public static void main(String[] args) {
        System.out.println("2+3*(6+7-3)=" + evaluateExpression("2+3*(6+7-3)")); 
        System.out.println("((8+2) * (40-24)) * 4=" + evaluateExpression("((8+2) * (40-24)) * 4")); 
        System.out.println(evaluateExpression("2 ^ 2 ^ 3")); 
        System.out.println(evaluateExpression("2 * 2 ^ (3 + 2)")); 
        System.out.println(evaluateExpression("2+(3^4)^2")); 
    }
}



