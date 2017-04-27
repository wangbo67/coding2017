package basic.dataStructure.stack.expr;

import basic.dataStructure.stack.Stack;

import java.util.List;

public class InfixExpr {
    String expr = null;

    public InfixExpr(String expr) {
        this.expr = expr;
    }

    public float evaluate() {
        TokenParser parser = new TokenParser();
        List<Token> tokens = parser.parse(expr);

        Stack numbers = new Stack();
        Stack operators = new Stack();

        int temp = 0;

        for (Token token : tokens) {
            if (token.isOperator()) {
                operators.push(token);
            }

            if (token.isNumber()) {
                numbers.push(token);
            }

            //先计算
            int opeSize = operators.size();
            int numSize = numbers.size();
            if (numSize == 3 && opeSize == 2) {
                Token tmp = (Token) operators.pop();
                if (tmp.hasHigherPriority((Token) operators.peek())) {
                    //如果1+2*3，先计算numbers后两位
                    numbers.push(new Token(Token.NUMBER, calculate(numbers, tmp) + ""));
                } else {
                    //如果1*2+3，先计算numbers栈前两位
                    //先保存数字和运算符
                    Token sNum = (Token) numbers.pop();
                    Token sOper = tmp;

                    //需要进行计算的运算符
                    Token oper = (Token) operators.pop();
                    numbers.push(new Token(Token.NUMBER, calculate(numbers, oper) + ""));
                    numbers.push(new Token(Token.NUMBER, sNum + ""));
                    operators.push(new Token(Token.OPERATOR, sOper.toString()));
                }
            }
        }

        if (numbers.size() == 2 && operators.size() == 1) {
            return calculate(numbers, (Token) operators.pop());
        } else {
            throw new RuntimeException("last calculation exception, numbers.size=" + numbers.size() + ", operators.size=" + operators.size());
        }
    }

    private float calculate(Stack numbers, Token operator) {
        Token token2 = (Token) numbers.pop();
        float val2 = token2.getFloatValue();

        Token token1 = (Token) numbers.pop();
        float val1 = token1.getFloatValue();

        String oper = operator.toString();
        float res = 0l;
        if (oper.equals("*")) {
            res = val1 * val2;
        } else if (oper.equals("+")) {
            res = val1 + val2;
        } else if (oper.equals("-")) {
            res = val1 - val2;
        } else {
            if (val2 == 0) throw new RuntimeException("cannot divide 0, calculation canceled");
            res = val1 / val2;
        }
//        System.out.println("计算结果: " + val1 + oper + val2 + "=" + res);
        return res;
    }

}
