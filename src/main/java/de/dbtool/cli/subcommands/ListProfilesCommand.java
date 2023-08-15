package de.dbtool.cli.subcommands;

import de.dbtool.console.ConsolePrinter;
import de.dbtool.files.ProfileHandler;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.TablePrinter;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Subcommand to list all available profiles
 */
@CommandLine.Command(name = "list-profiles", description = "List all available profiles", mixinStandardHelpOptions = true)
public class ListProfilesCommand implements Runnable {

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Show more information about the profiles")
    private boolean verbose = false;

    @Override
    public void run() {
        try {
            ProfileHandler profileHandler = new ProfileHandler();
            TablePrinter tablePrinter = new TablePrinter(16);

            ArrayList<Profile> profiles = profileHandler.listProfiles();
            if(profiles == null || profiles.size() == 0) {
                ConsolePrinter.printInfo("No profiles found!");
                return;
            }
            List<String[]> tableData = new ArrayList<>();
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
