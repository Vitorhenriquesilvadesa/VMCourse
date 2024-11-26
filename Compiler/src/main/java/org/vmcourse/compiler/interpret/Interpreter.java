package org.vmcourse.compiler.interpret;

import org.vmcourse.compiler.exception.TailRuntimeException;
import org.vmcourse.compiler.parse.ParsedData;
import org.vmcourse.compiler.parse.expression.*;
import org.vmcourse.compiler.pass.CompilationPass;
import org.vmcourse.compiler.scan.TokenType;

public class Interpreter extends CompilationPass<ParsedData, InterpretResult>
        implements ExpressionVisitor<Object> {
    @Override
    public Class<ParsedData> getInputType() {
        return ParsedData.class;
    }

    @Override
    public Class<InterpretResult> getOutputType() {
        return InterpretResult.class;
    }

    @Override
    public String getDebugName() {
        return "interpreter-pass";
    }

    private InterpretResult result = new InterpretResult(0);

    @Override
    public InterpretResult pass(ParsedData input) {

        for (ExpressionNode expression : input.expressions) {
            if (result.getCode() == 0) {
                System.out.println(evaluate(expression));
            } else {
                break;
            }
        }

        return result;
    }

    private Object evaluate(ExpressionNode expression) {
        return expression.accept(this);
    }

    @Override
    public Object visitLiteralExpression(LiteralExpressionNode expr) {
        return expr.literal.literal();
    }

    @Override
    public Object visitUnaryExpression(UnaryExpressionNode expr) {
        Object right = evaluate(expr.expression);

        if (checkNumberOperand(right)) {
            return -((Float) right);
        }

        runtimeError("Invalid unary expression.");
        return null;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpressionNode expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);
        TokenType operator = expr.operator.type();

        checkNumberOperands(left, right);

        switch (operator) {
            case Plus: {
                return ((Float) left) + ((Float) right);
            }

            case Minus: {
                return ((Float) left) - ((Float) right);
            }

            case Star: {
                return ((Float) left) * ((Float) right);
            }

            case Slash: {
                return ((Float) left) / ((Float) right);
            }
        }

        runtimeError("Invalid binary operator.");
        return null;
    }

    @Override
    public Object visitGroupExpression(GroupExpression expr) {
        return evaluate(expr.expression);
    }

    private boolean checkNumberOperands(Object a, Object b) {
        if (a instanceof Float && b instanceof Float) {
            return true;
        }

        runtimeError("Operands must be numbers.");
        return false;
    }

    private boolean checkNumberOperand(Object op) {
        if (op instanceof Float) return true;

        runtimeError("Operand must be number");
        return false;
    }

    private void runtimeError(String message) {
        throw new TailRuntimeException(message);
    }
}
