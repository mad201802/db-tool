package de.dbtool.cli.subcommands;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.ProfileHandler;
import de.dbtool.files.schemas.Profile;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import picocli.CommandLine;

import java.util.Set;

/**
 * Subcommand to create a new connection profile
 */
@CommandLine.Command(name = "create-profile", description = "Create a new database profile", mixinStandardHelpOptions = true)
public class CreateProfileCommand implements Runnable {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @NotBlank(message = "Name must not be blank")
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of the profile", required = true)
    private String name;

    @NotBlank(message = "Host must not be blank")
    @CommandLine.Option(names = {"-h", "--host"}, description = "Hostname of the database", required = true)
    private String hostname;

    @CommandLine.Option(names = {"-p", "--port"}, description = "Port of the database", required = true)
    private int port;

    @NotBlank(message = "Database must not be blank")
    @CommandLine.Option(names = {"-db", "--database"}, description = "Name of the database", required = true)
    private String dbName;

    @NotBlank(message = "Username must not be blank")
    @CommandLine.Option(names = {"-u", "--username"}, description = "Username to login to the database", required = true)
    private String username;

    @NotBlank(message = "Password must not be blank")
    @CommandLine.Option(names = {"-pw", "--password"}, description = "Password of the defined user", required = true)
    private String password;

    @CommandLine.Option(names = {"-t", "--type"}, description = "Type of database (use OTHER when using a custom JDBC Driver)", required = true)
    private SupportedDatabases type;

    @CommandLine.Option(names = {"-d", "--driver"}, description = "Path to the JDBC Driver")
    private String driverPath;

    @Override
    public void run() {
        // Check if the user specified a custom driver path, but did not specify the type OTHER
        if (type == SupportedDatabases.OTHER && driverPath == null) {
            throw new CommandLine.ParameterException(spec.commandLine(), "If you want to use a database type other than the supported ones, you have to specify the path to the jdbc driver\n");
        }

        // Validate the command line options
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CreateProfileCommand>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("\n");
            for (ConstraintViolation<CreateProfileCommand> violation : violations) {
                errorMsg.append("ERROR: ").append(violation.getMessage()).append("\n");
            }
            throw new CommandLine.ParameterException(spec.commandLine(), errorMsg.toString());
        }

        // Create the profile
        try {
            ProfileHandler profileHandler = new ProfileHandler();

            if (profileHandler.getProfile(name) != null) {
                throw new DbToolException("Profile with name " + name + " already exists");
            }

            Profile newProfile = profileHandler.createProfile(name, hostname, port, dbName, username, password, type, driverPath);
            if (newProfile != null) {
                System.out.println("Profile " + name + " created successfully");
            } else {
                throw new DbToolException("Error while creating profile");
            }

        } catch (DbToolException ex) {
            System.err.println("Error while creating profile: " + ex.getMessage());
        }
    }
}
