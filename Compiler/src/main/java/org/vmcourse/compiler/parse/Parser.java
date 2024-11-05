package org.vmcourse.compiler.parse;

import org.vmcourse.compiler.pass.CompilationPass;
import org.vmcourse.compiler.scan.ScannedData;
import org.vmcourse.compiler.scan.Token;

public class Parser extends CompilationPass<ScannedData, ParsedData> {
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

    @Override
    public ParsedData pass(ScannedData input) {
        for (Token token : input.getTokens()) {
            System.out.println(token.toString());
        }
        return new ParsedData();
    }
}
