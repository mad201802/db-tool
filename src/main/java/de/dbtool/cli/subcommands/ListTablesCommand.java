package de.dbtool.cli.subcommands;

import de.dbtool.database.Query;
import de.dbtool.database.QueryProcessor;
import de.dbtool.database.factories.DatabaseFactory;
import de.dbtool.database.interfaces.DefaultDatabase;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.ProfileHandler;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.TablePrinter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Subcommand to list all available profiles
 */
@CommandLine.Command(name = "list-tables", description = "List all available tables in profile", mixinStandardHelpOptions = true)

public class ListTablesCommand implements Runnable {

    @CommandLine.Option(names = {"-p", "--profile"}, description = "The name of the profile", required = true)
    private String profileName;

    @CommandLine.Option(names = {"-lt", "--limit-text-length"}, description = "Limits the length of text in a column and display ellipsis", required = false)
    private int limitTextLength = -1;

    @Override
    public void run() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ListTablesCommand>> violations = validator.validate(this);

        if(!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for(ConstraintViolation<ListTablesCommand> violation : violations) {
                errorMsg.append("ERROR: ").append(violation.getMessage()).append("\n");
            }
            System.err.println(errorMsg);
            return;
        }

        try{
            ProfileHandler profileHandler = new ProfileHandler();
            Profile profile = profileHandler.getProfile(profileName);
            TablePrinter tablePrinter = new TablePrinter(limitTextLength);

            if (profile == null) throw new DbToolException("Profile not found");

            System.out.println("Using profile: " + profile.name);

            IDatabase database = DatabaseFactory.getDatabaseType(profile);

            DefaultDatabase defaultDatabase = (DefaultDatabase) database;
            defaultDatabase.connect();
            List<String> tables = defaultDatabase.getAllDatabaseTables();

            String tableString = tablePrinter.getTableString("Tables found: " + tables.size(), tables.stream().map(x -> new String[]{x}).toList());
            System.out.println(tableString);

        } catch (Exception ex) {
            System.err.println("Error while listing tables: " + ex.getMessage());
        }
    }
}