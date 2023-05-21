package de.dbtool.cli;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DevelopmentExecution {
    @Test
    public void testExecution() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"grep", "-p", "oracledb", "-lc", "1,2,8-22", "-lt", "10"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);

            // Set to default again
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println(baos);
        }
    }

    @Test
    public void testWithCommandLineOption() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"create-profile", "--name=oracledb", "--host=127.0.0.1", "--port=3306", "--database=db", "--username=root", "--password=root", "--type=OTHER", "-d=C:\\Users\\micha\\Downloads\\ojdbc11.jar"};
            PicocliRunner.run(DbToolCommand.class, ctx, args);

            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println(baos);
        }
    }
}
