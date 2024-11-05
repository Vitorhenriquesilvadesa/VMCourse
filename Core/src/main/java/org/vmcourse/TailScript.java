package org.vmcourse;

import org.vmcourse.compiler.parse.Parser;
import org.vmcourse.compiler.pipeline.CompilationPipeline;
import org.vmcourse.compiler.scan.Scanner;
import org.vmcourse.util.TailFile;

public class TailScript {
    public static void main(String[] args) {

        CompilationPipeline pipeline = new CompilationPipeline();
        TailFile file = new TailFile(args[0]);
        pipeline.insertStage(new Scanner())
                .insertStage(new Parser());

        pipeline.execute(file);
    }
}