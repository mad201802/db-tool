package de.dbtool.cli.subcommands;

import de.dbtool.console.ConsolePrinter;
import de.dbtool.files.ProfileHandler;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.TablePrinter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Subcommand to list all available profiles
 */
@CommandLine.Command(name = "list-profiles", description = "List all available profiles", mixinStandardHelpOptions = true)
public class ListProfilesCommand implements Runnable {

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Show more information about the profiles")
    private boolean verbose = false;

    @Override
    public void run() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ListProfilesCommand>> violations = validator.validate(this);

        if(!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for(ConstraintViolation<ListProfilesCommand> violation : violations) {
                errorMsg.append("ERROR: ").append(violation.getMessage()).append("\n");
            }
            System.err.println(errorMsg);
            return;
        }

        try {
            ProfileHandler profileHandler = new ProfileHandler();
            TablePrinter tablePrinter = new TablePrinter(16);

            Profile[] profiles = profileHandler.listProfiles();
            if(profiles == null || profiles.length == 0) {
                ConsolePrinter.printInfo("No profiles found!");
                return;
            }
            List<String[]> tableData = new ArrayList<>(profiles.length);
            tableData.add(new String[] {"Name", "Hostname", "Port", "Database", "Username", "Password", "Type", "Driver"});
            for(Profile p : profiles) {
                tableData.add(new String[] {p.name, p.hostname, Integer.toString(p.port), p.dbName, p.username, tablePrinter.censorPassword(p.password), p.type.toString(), p.driverPath});
            }

            ConsolePrinter.print("Available profiles:");
            String table = tablePrinter.getTableString("Profiles",tableData);
            ConsolePrinter.print(table);

        } catch (Exception ex) {
            ConsolePrinter.printError("Error while listing profiles: " + ex.getMessage());
        }
    }
}
