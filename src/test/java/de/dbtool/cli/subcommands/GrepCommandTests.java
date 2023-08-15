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
        }

        Assertions.assertTrue(out_text.toString().contains("Usage: db-tool grep"));
        Assertions.assertTrue(err_text.toString().isEmpty());
    }

    @Test
    public void test_grep_all_tables() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-lr", "1"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 11 Table(s)"));
    }

    @Test
    public void test_grep_table_pattern() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-tp", "invoice*", "-lr", "1"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 2 Table(s)"));
        Assertions.assertTrue(out_text.toString().contains("Table invoice_item"));
        Assertions.assertTrue(out_text.toString().contains("Table invoices"));
    }

    @Test
    public void test_grep_table_regex() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-tr", "(albums)|(employees)", "-lr", "1"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 2 Table(s)"));
        Assertions.assertTrue(out_text.toString().contains("Table albums"));
        Assertions.assertTrue(out_text.toString().contains("Table employees"));
    }

    @Test
    public void test_grep_column_pattern() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-t", "customers", "-cp", "C*"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 1 Table(s)"));
        Assertions.assertTrue(out_text.toString().contains("Company"));
        Assertions.assertTrue(out_text.toString().contains("Country"));
        Assertions.assertTrue(out_text.toString().contains("CustomerId"));
        Assertions.assertTrue(out_text.toString().contains("City"));
    }

    @Test
    public void test_grep_column_regex() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-t", "customers", "-cr", "(Company)|(FirstName)", "-lr", "1"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 1 Table(s)"));
        Assertions.assertTrue(out_text.toString().contains("Company"));
        Assertions.assertTrue(out_text.toString().contains("FirstName"));
    }

    @Test
    public void test_grep_value_pattern() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-t", "customers", "-cp", "C*", "-vp", "C*"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 1 Table(s)"));
        Assertions.assertTrue(out_text.toString().contains("Table customers: 14 Row(s) found"));
    }

    @Test
    public void test_grep_value_compare_all() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-t", "customers", "-cp", "C*", "-vc", "CustomerId > 10"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("Found 1 Table(s)"));
        Assertions.assertTrue(out_text.toString().contains("Table customers: 49 Row(s) found"));
    }

    @Test
    public void test_grep_invalid_input() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "   ", "-t", "customers"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("ERROR: Name must not be blank"));
    }

    @Test
    public void test_grep_no_results() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "demo", "-t", "abc"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Successfully connected to database"));
        Assertions.assertTrue(out_text.toString().contains("No results found in database"));
    }

}
