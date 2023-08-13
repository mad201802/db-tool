package de.dbtool.cli.subcommands;

import de.dbtool.UnitTestUtils;
import de.dbtool.cli.DbToolCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GrepCommandTests {

    @BeforeAll
    public static void setup() {
        UnitTestUtils.createDemoProfile();
    }

    @AfterAll
    public static void cleanup() {
        UnitTestUtils.deleteDemoProfile();
    }

    @Test
    public void test_grep_help() {
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

    @Test
    public void test_grep_profile() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        CreateProfileCommandTests.print(out_text.toString(), err_text.toString());
    }
}
