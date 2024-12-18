package org.vmcourse.compiler.parse.expression;

import org.vmcourse.compiler.scan.Token;

public class GroupExpression extends ExpressionNode {
    public final Token paren;
    public final ExpressionNode expression;

    public GroupExpression(Token paren, ExpressionNode expression) {
        this.paren = paren;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("Grouping(%s)", expression);
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitGroupExpression(this);
    }
}
