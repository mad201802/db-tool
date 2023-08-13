package de.dbtool.cli.subcommands;

import de.dbtool.cli.subcommands.containers.*;
import de.dbtool.database.Query;
import de.dbtool.database.QueryProcessor;
import de.dbtool.database.factories.DatabaseFactory;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.ProfileHandler;
import de.dbtool.files.schemas.Profile;
import de.dbtool.utils.TablePrinter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Subcommand to search for patterns/regex in a database
 */
@CommandLine.Command(name = "grep", description = "Grep-like tool for searching a database", mixinStandardHelpOptions = true)
public class GrepCommand implements Runnable {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @NotBlank(message = "Name must not be blank")
    @CommandLine.Option(names = {"-p", "--profile"}, description = "The name of the profile", required = true)
    private String profileName;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table name pattern options")
    private List<TablePatternOption> tablePatternOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table name regex options")
    private List<TableRegexOption> tableRegexOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Column name pattern options")
    private List<ColumnPatternOption> columnPatternOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Column name regex options")
    private List<ColumnRegexOption> columnRegexOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table content/value pattern options")
    private List<ValuePatternOption> valuePatternOptions;

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table content/value compare options")
    private List<ValueCompareOption> valueCompareOptions;

    @CommandLine.Option(names = {"--vc-use-and"}, description = "When using multiple value compare options, use AND instead of OR")
    private boolean valueCompareUseAnd = false;

    @CommandLine.Option(names = {"-lc", "--limit-columns"}, description = "Limits the number of columns to display", required = false)
    private String limitColumnsQuery;

    @CommandLine.Option(names = {"-lr", "--limit-rows"}, description = "Limits the number of rows to display", required = false)
    private int limitRows = -1;

    @CommandLine.Option(names = {"-lt", "--limit-text-length"}, description = "Limits the length of text in a column and display ellipsis", required = false)
    private int limitTextLength = -1;

    @Override
    public void run() {

        // Validate the command line options
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<GrepCommand>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<GrepCommand> violation : violations) {
                errorMsg.append("ERROR: ").append(violation.getMessage()).append("\n");
            }
            System.err.println(errorMsg);
            return;
        }

        try {
            ProfileHandler profileHandler = new ProfileHandler();
            Profile profile = profileHandler.getProfile(profileName);
            TablePrinter tablePrinter = new TablePrinter(limitTextLength);

            if (profile == null) throw new DbToolException("Profile not found");

            System.out.println("Using profile: " + profile.name);

            System.out.println("Limit rows: " + limitRows);
            Query query = new Query(tablePatternOptions, tableRegexOptions, columnPatternOptions, columnRegexOptions, valuePatternOptions, valueCompareOptions, valueCompareUseAnd, limitRows);
            IDatabase database = DatabaseFactory.getDatabaseType(profile);
            QueryProcessor queryProcessor = new QueryProcessor(database, query);

            Map<String, List<String[]>> result = queryProcessor.executeQuery();
            if(result.isEmpty()) {
                System.out.println("No results found in database");
                return;
            }

            for(Map.Entry<String, List<String[]>> entry : result.entrySet()) {
                System.out.println(tablePrinter.getTableString("Table " + entry.getKey() + ": " + entry.getValue().size() + " Row(s) found", entry.getValue()));
            }

        } catch (DbToolException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
