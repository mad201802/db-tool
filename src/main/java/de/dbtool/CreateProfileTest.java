package de.dbtool;

import de.dbtool.cli.DbToolCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CreateProfileTest {

    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            PicocliRunner.run(DbToolCommand.class, ctx, "create-profile", "-h", "localhost", "-n", "mysql", "-p", "3306", "-db", "app_db", "-t", "MYSQL", "-u", "db_user", "-pw", "password");

            // Set to default again
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println(baos);
        }
    }
}
