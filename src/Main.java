import java.util.Stack;

public class Main {
    static int Prec(String ch)
    {
        switch (ch) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
                return 2;

            case "^":
                return 3;
        }
        return -1;
    }

    static private class StringStack{

        StringStack(){
            dane = new String[10];
            wskStosu = -1;
        }
        private String[] dane;
        private int wskStosu;

        private void push(String exp){
            wskStosu++;
            dane[wskStosu] = exp;
        }

        private String peek(){
            return dane[wskStosu];
        }

        private void pop(){
            dane[wskStosu] = "";
            wskStosu--;
        }

        private Boolean isEmpty(){
            return wskStosu == -1;
        }
    }


    static String infixToPostfix(String exp)
    {
        String result = new String("");

        StringStack stack = new StringStack();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);


            if (Character.isLetterOrDigit(c))
                result += c;


            else if (c == '('){
                Character character = c;
                stack.push(character.toString());
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
                Character character = c;
                while (!stack.isEmpty()
                        && Prec(character.toString()) <= Prec(stack.peek())) {

                    result += stack.peek()+" ";
                    stack.pop();
                }
                stack.push(character.toString());
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
        Stack<Integer> stack = new Stack<Integer>();
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