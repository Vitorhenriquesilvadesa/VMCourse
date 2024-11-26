package org.vmcourse.compiler.parse.expression;

import org.vmcourse.compiler.scan.Token;

public class UnaryExpressionNode extends ExpressionNode {
    public final Token operator;
    public final ExpressionNode expression;

    public UnaryExpressionNode(Token operator, ExpressionNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("Unary(%s, %s)", operator.type().name(), expression);
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitUnaryExpression(this);
    }
}
