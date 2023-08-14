package de.dbtool;

import de.dbtool.cli.DbToolCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTests {

    private Connection conn;

    public static void main(String[] args) throws SQLException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] cli_args = new String[]{"grep", "-p", "mysql", "-tp", "f*", "-lc", "10", "-lr", "10", "--please-tell-me-everything"};
//            String[] cli_args = new String[]{"grep", "-p", "eventmate_db", "-tr", "(User)|(Event)", "-cp", "*", "-vp", "t*"};

            PicocliRunner.run(DbToolCommand.class, ctx, cli_args);

            // Set to default again
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println(baos);
        }
    }
}
