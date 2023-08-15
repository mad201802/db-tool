package de.dbtool.cli.subcommands;

import de.dbtool.cli.DbToolCommand;
import de.dbtool.files.ProfileHandler;
import de.dbtool.utils.FileSystem;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateProfileCommandTests {

    private static final String TMP_FOLDER = "./tmp";

    @AfterAll
    public static void cleanup() {
        FileSystem.deleteDirectory(new File(TMP_FOLDER));
    }

    public static void print(String out, String err) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));

        System.out.println("OUT: " + out);
        System.err.println("ERR: " + err);
    }

    @Test
    public void test_create_profile_help() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "-h"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("Usage: db-tool create-profile"));
        Assertions.assertTrue(out_text.toString().isEmpty());
    }

    @Test
    public void test_create_profile_missing_parameter() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "test", "-u", "root", "-pw", "root"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("Missing required option: '--type=<type>'"));
        Assertions.assertTrue(out_text.toString().isEmpty());
    }

    @Test
    public void test_create_profile_blank_parameter() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "-n", " ", "-h", "localhost", "-p", "3306", "-db", "test", "-u", "root", "-pw", "root", "-t", "MYSQL"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("ERROR: Name must not be blank"));
        Assertions.assertTrue(out_text.toString().isEmpty());
    }

    @Test
    public void test_create_profile_wrong_type() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "test", "-u", "root", "-pw", "root", "-t", "12345"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("Invalid value for option '--type': expected one of [MYSQL, SQLITE, OTHER]"));
        Assertions.assertTrue(out_text.toString().isEmpty());
    }

    @Test
    public void test_create_profile_other_missing_param() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "test", "-u", "root", "-pw", "root", "-t", "OTHER"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(err_text.toString().contains("If you want to use a database type other than the supported ones, you have to specify the path to the jdbc driver"));
        Assertions.assertTrue(out_text.toString().isEmpty());
    }

    @Test
    public void test_create_profile_duplicate() throws InterruptedException {
        String tmp = TMP_FOLDER + "/test_create_profile_duplicate";
        ProfileHandler.setProfilePath(tmp);

        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            for (int i = 0; i < 2; i++) {
                String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "test", "-u", "root", "-pw", "root", "-t", "MYSQL"};
                PicocliRunner.run(DbToolCommand.class, ctx, args);
            }
        }

        Assertions.assertTrue(err_text.toString().contains("Error while creating profile: Profile with name unittest already exists"));
        Assertions.assertTrue(FileSystem.deleteDirectory(new File(tmp)));
    }

    @Test
    public void test_create_profile_success() {
        String tmp = TMP_FOLDER + "/test_create_profile_success";
        ProfileHandler.setProfilePath(tmp);

        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "test", "-u", "root", "-pw", "root", "-t", "MYSQL"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Profile unittest created successfully"));
        Assertions.assertTrue(err_text.toString().isEmpty());

        Assertions.assertTrue(FileSystem.deleteDirectory(new File(tmp)));
    }

}
