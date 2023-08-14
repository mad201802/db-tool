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

public class ListTablesCommandTests {
    @BeforeAll
    public static void setup() {
        UnitTestUtils.createDemoProfile();
    }

    @AfterAll
    public static void cleanup() {
        UnitTestUtils.deleteDemoProfile();
    }

    @Test
    public void test_list_table_help() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"list-tables", "-h"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Usage: db-tool list-tables"));
        Assertions.assertTrue(err_text.toString().isEmpty());
    }

    @Test
    public void test_list_table_invalid_profile() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"list-tables", "-p", "abc"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("Profile not found"));
    }

    @Test
    public void test_list_table_success() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"list-tables", "-p", "demo"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        CreateProfileCommandTests.print(out_text.toString(), err_text.toString());
        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Tables found: 11"));
    }

}
