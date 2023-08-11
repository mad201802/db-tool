package de.dbtool.cli.subcommands;

import de.dbtool.cli.DbToolCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GrepCommandTests {
    @Test
    public void testExecution() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-h"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);

            Assertions.assertTrue(out_text.toString().contains("Usage: db-tool grep"));
            Assertions.assertTrue(err_text.toString().isEmpty());
        }
    }
}
