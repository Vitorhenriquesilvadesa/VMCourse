package org.vmcourse.compiler.parse.expression;

import org.vmcourse.compiler.scan.Token;

public class LiteralExpressionNode extends ExpressionNode {
    public final Token literal;

    public LiteralExpressionNode(Token literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return String.format("Literal(%s)", literal.literal());
    }
}
