package de.dbtool.cli.subcommands;

import de.dbtool.UnitTestUtils;
import de.dbtool.cli.DbToolCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class ListProfilesCommandTests {

    @Test
    public void test_create_profile_help() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"list-profiles", "-h"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("Usage: db-tool list-profiles [-hvV]"));
    }

    @Test
    public void test_create_profile_list_no_profiles() {
        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"list-profiles"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        Assertions.assertTrue(out_text.toString().contains("No profiles found!"));
    }

    @Test
    public void test_create_profile_list_with_profiles() {
        UnitTestUtils.createDemoProfile();

        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out_text));
        System.setErr(new PrintStream(err_text));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"list-profiles"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);
        }

        CreateProfileCommandTests.print(out_text.toString(), err_text.toString());
        File f = new File(System.getProperty("user.dir") + "/src/test/resources/chinook.db");
        CreateProfileCommandTests.print(f.exists() + "", null);
        Assertions.assertTrue(out_text.toString().contains("demo"));
        Assertions.assertTrue(out_text.toString().contains("SQLITE"));
        UnitTestUtils.deleteDemoProfile();
    }


}
