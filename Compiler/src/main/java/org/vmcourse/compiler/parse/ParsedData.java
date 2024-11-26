package org.vmcourse.compiler.parse;

import org.vmcourse.compiler.component.IOComponent;
import org.vmcourse.compiler.parse.expression.ExpressionNode;

import java.util.List;

public class ParsedData extends IOComponent<ParsedData> {

    public final List<ExpressionNode> expressions;

    public ParsedData(List<ExpressionNode> expressions) {
        this.expressions = expressions;
    }
}
