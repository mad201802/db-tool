package de.dbtool.cli.subcommands;

public class CreateProfileCommandTests {

//    @BeforeEach
//    public void setup() {
//        FileSystem.deleteDirectory(new File("./tmp"));
//        ProfileHandler.setProfilePath("./tmp/profiles");
//    }
//
//    @AfterEach
//    public void teardown() {
//        FileSystem.deleteDirectory(new File("./tmp/profiles"));
//    }
//
//    public void printConsoleOutput(ByteArrayOutputStream out_text, ByteArrayOutputStream err_text) {
//        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
//        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
//        System.out.println("out_text: " + out_text.toString());
//        System.out.println("err_text: " + err_text.toString());
//    }
//
//    @Test
//    public void test_create_profile_command() {
//        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
//        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out_text));
//        System.setErr(new PrintStream(err_text));
//
//        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
//            String[] args = new String[]{"create-profile", "-h"};
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//
//            Assertions.assertTrue(out_text.toString().isEmpty());
//            Assertions.assertTrue(err_text.toString().contains("Missing required parameter for option '--host' (<hostname>)"));
//        }
//    }
//
//    @Test
//    public void test_new_profile_not_all_arguments() {
//        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
//        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out_text));
//        System.setErr(new PrintStream(err_text));
//
//        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
//            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "unittest", "-u", "root", "-pw", "root"};
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//        }
//
//        Assertions.assertTrue(err_text.toString().contains("Missing required option: '--type=<type>'"));
//    }
//
//    @Test
//    public void test_new_profile_success() {
//        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
//        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out_text));
//        System.setErr(new PrintStream(err_text));
//
//        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
//            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "unittest", "-u", "root", "-pw", "root", "-t", "MYSQL"};
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//        }
//
//        Assertions.assertTrue(out_text.toString().contains("Profile unittest created successfully"));
//        Assertions.assertTrue(err_text.toString().isEmpty());
//
//        printConsoleOutput(out_text, err_text);
//
//        FileSystem.deleteFile(new File("./tmp/profiles/unittest.json"));
//    }
//
//    @Test
//    public void test_new_profile_duplicate() {
//        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
//        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out_text));
//        System.setErr(new PrintStream(err_text));
//
//        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
//            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "unittest", "-u", "root", "-pw", "root", "-t", "MYSQL"};
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//            out_text.reset();
//            err_text.reset();
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//        }
//
//        Assertions.assertTrue(out_text.toString().isEmpty());
//        Assertions.assertTrue(err_text.toString().contains("Error while creating profile: Profile with name unittest already exists"));
//
//        FileSystem.deleteFile(new File("./tmp/profiles/unittest.json"));
//    }
//
//    @Test
//    public void test_profile_custom_db_driver_missing_argument() {
//        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
//        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out_text));
//        System.setErr(new PrintStream(err_text));
//
//        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
//            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "unittest", "-u", "root", "-pw", "root", "-t", "OTHER"};
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//        }
//
//        Assertions.assertTrue(out_text.toString().isEmpty());
//        Assertions.assertTrue(err_text.toString().contains("If you want to use a database type other than the supported ones, you have to specify the path to the jdbc driver"));
//    }
//
//    @Test
//    public void test_profile_custom_db_driver() {
//        ByteArrayOutputStream out_text = new ByteArrayOutputStream();
//        ByteArrayOutputStream err_text = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out_text));
//        System.setErr(new PrintStream(err_text));
//
//        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
//            String[] args = new String[]{"create-profile", "-n", "unittest", "-h", "localhost", "-p", "3306", "-db", "unittest", "-u", "root", "-pw", "root", "-t", "OTHER", "-d", "path/to/driver"};
//            PicocliRunner.run(DbToolCommand.class, ctx, args);
//        }
//
//        Assertions.assertTrue(out_text.toString().contains("Profile unittest created successfully"));
//        Assertions.assertTrue(err_text.toString().isEmpty());
//
//        FileSystem.deleteDirectory(new File("./tmp/profiles/unittest.json"));
//    }

}
