package org.vmcourse.compiler.parse.expression;

public interface ExpressionVisitor<T> {

    T visitLiteralExpression(LiteralExpressionNode expr);

    T visitUnaryExpression(UnaryExpressionNode expr);

    T visitBinaryExpression(BinaryExpressionNode expr);

    T visitGroupExpression(GroupExpression expr);
}
