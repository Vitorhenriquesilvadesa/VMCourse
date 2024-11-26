package org.vmcourse.compiler.parse;

import org.vmcourse.compiler.exception.TailRuntimeException;
import org.vmcourse.compiler.parse.expression.*;
import org.vmcourse.compiler.pass.CompilationPass;
import org.vmcourse.compiler.scan.ScannedData;
import org.vmcourse.compiler.scan.Token;
import org.vmcourse.compiler.scan.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser extends CompilationPass<ScannedData, ParsedData> {

    private int current;
    List<Token> tokens;
    List<ExpressionNode> expressions;

    @Override
    public ParsedData pass(ScannedData input) {
        tokens = input.getTokens();
        expressions = new ArrayList<>();

        while (!isAtEnd()) {
            expressions.add(expression());
        }

        // expressions.forEach(System.out::println);

        return new ParsedData(expressions);
    }

    private ExpressionNode expression() {
        return term();
    }

    private ExpressionNode term() {
        ExpressionNode left = factor();

        if (match(TokenType.Plus, TokenType.Minus)) {
            Token operator = previous();
            ExpressionNode right = factor();
            return new BinaryExpressionNode(left, operator, right);
        }

        return left;
    }

    private ExpressionNode factor() {
        ExpressionNode expression = unary();

        while (match(TokenType.Star, TokenType.Slash)) {
            Token operator = previous();
            ExpressionNode right = factor();
            expression = new BinaryExpressionNode(expression, operator, right);
        }

        return expression;
    }

    private ExpressionNode unary() {
        if (match(TokenType.Minus, TokenType.Mark)) {
            Token operator = previous();

            // Correção de "right = expression()" para "right = unary()"
            ExpressionNode right = unary();
            return new UnaryExpressionNode(operator, right);
        }

        return literal();
    }

    private ExpressionNode literal() {
        if (match(TokenType.Number)) {
            return new LiteralExpressionNode(previous());
        }

        if (match(TokenType.LeftParen)) {
            return grouping();
        }

        throw new TailRuntimeException(String.format("Invalid expression at line %d. Error: '%s'", previous().line(), peek().lexeme()));
    }

    private ExpressionNode grouping() {
        Token paren = previous();
        ExpressionNode expression = expression();
        consume(TokenType.RightParen, "Expect ')' after group expression.");

        return new GroupExpression(paren, expression);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean isAtEnd() {
        return peek().type() == TokenType.EndOfFile;
    }

    private void advance() {
        current++;
    }

    private void consume(TokenType type, String message) {
        if (!match(type)) {
            throw new TailRuntimeException(message);
        }
    }

    private boolean match(TokenType... types) {
        if (check(types)) {
            advance();
            return true;
        }

        return false;
    }

    private boolean check(TokenType... types) {
        for (TokenType type : types) {
            if (peek().type() == type) {
                return true;
            }
        }

        return false;
    }

    private Token peek() {
        return tokens.get(current);
    }

    @Override
    public Class<ScannedData> getInputType() {
        return ScannedData.class;
    }

    @Override
    public Class<ParsedData> getOutputType() {
        return ParsedData.class;
    }

    @Override
    public String getDebugName() {
        return "Parse Pass";
    }
}
