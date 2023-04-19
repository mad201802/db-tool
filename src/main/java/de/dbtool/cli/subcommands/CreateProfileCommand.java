package de.dbtool.cli.subcommands;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import picocli.CommandLine;

/**
 * Subcommand to create a new connection profile
 */
@CommandLine.Command(name = "create-profile", description = "Create a new database profile", mixinStandardHelpOptions = true)
public class CreateProfileCommand implements Runnable {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of the profile", required = true)
    private String name;

    @CommandLine.Option(names = {"-h", "--host"}, description = "Hostname of the database", required = true)
    private String hostname;

    @CommandLine.Option(names = {"-p", "--port"}, description = "Port of the database", required = true)
    private int port;

    @CommandLine.Option(names = {"-db", "--database"}, description = "Name of the database", required = true)
    private String dbName;

    @CommandLine.Option(names = {"-u", "--username"}, description = "Username to login to the database", required = true)
    private String username;

    @CommandLine.Option(names = {"-pw", "--password"}, description = "Password of the defined user", required = true)
    private String password;

    @CommandLine.Option(names = {"-t", "--type"}, description = "Type of database (use OTHER when using a custom JDBC Driver)", required = true)
    private SupportedDatabases type;

    @CommandLine.Option(names = {"-d", "--driver"}, description = "Path to the JDBC Driver", required = false)
    private String driverPath;

    // Maybe this is not needed, because the profiles are getting stored in the user's home directory under .dbtool. Otherwise, the list-profiles command would not work
//    @CommandLine.Option(names={"-o", "--output"}, description = "Output path for the connection profile", required = false)
//    private String outputPath;

    @Override
    public void run() {
        if (type == SupportedDatabases.OTHER && driverPath == null) {
            throw new CommandLine.ParameterException(spec.commandLine(), "If you want to use a database type other than the supported ones, you have to specify the path to the jdbc driver\n");
        }

        // TODO: Implement this
    }
}
