package de.dbtool;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DbToolCommandTest {

    @Test
    public void testWithCommandLineOption() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[] { "-v" };
            PicocliRunner.run(DbToolCommand.class, ctx, args);

            // db-tool
            assertTrue(baos.toString().contains("Hi!"));
        }
    }
}
