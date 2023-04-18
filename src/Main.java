import java.util.Stack;

public class Main {
    static int precedence(String ch)
    {
        return switch (ch) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> -1;
        };
    }

    static private class StringStack{

        StringStack(){
            dane = new String[10];
            stackPtr = -1;
        }
        private String[] dane;
        private int stackPtr;

        private void push(String exp){
            stackPtr++;
            dane[stackPtr] = exp;
        }

        private String peek(){
            return dane[stackPtr];
        }

        private void pop(){
            dane[stackPtr] = "";
            stackPtr--;
        }

        private Boolean isEmpty(){
            return stackPtr == -1;
        }
    }


    static String infixToPostfix(String exp)
    {
        String result = "";

        StringStack stack = new StringStack();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);


            if (Character.isLetterOrDigit(c))
                result += c;


            else if (c == '('){
                stack.push(Character.toString(c));
            }

            else if (c == ')') {
                while (!stack.isEmpty()
                        && !stack.peek().equals("(")) {
                    result += " " + stack.peek();
                    stack.pop();
                }

                stack.pop();
            }

            else
            {
                result += " ";
                while (!stack.isEmpty()
                        && precedence(Character.toString(c)) <= precedence(stack.peek())) {

                    result += stack.peek()+" ";
                    stack.pop();
                }
                stack.push(Character.toString(c));
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("("))
                return "Invalid Expression";
            result += " " + stack.peek();
            stack.pop();
        }

        return result;
    }

    public static int evaluatePostfix(String expr) {
        Stack<Integer> stack = new Stack<>();
        String[] characters = expr.split(" ");

        for (String character : characters) {
            if (character.matches("\\d+")) {
                stack.push(Integer.parseInt(character));
            } else if (character.equals("+")) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                stack.push(operand1 + operand2);
            } else if (character.equals("-")) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                stack.push(operand1 - operand2);
            } else if (character.equals("*")) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                stack.push(operand1 * operand2);
            } else if (character.equals("/")) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                stack.push(operand1 / operand2);
            }
            else if (character.equals("^")) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                stack.push((int)Math.pow(operand1,operand2));
            }
        }

        return stack.pop();
    }

    public static void main(String[] args){
        for(String argument: args){
            String postfix = infixToPostfix(argument);
            System.out.println(argument + ":" + postfix + evaluatePostfix(postfix) ) ;
        }
    }
}