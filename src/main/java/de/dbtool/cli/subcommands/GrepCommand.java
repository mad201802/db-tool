package de.dbtool.cli.subcommands;

import de.dbtool.cli.subcommands.containers.*;
import de.dbtool.database.Query;
import de.dbtool.database.QueryProcessor;
import de.dbtool.database.factories.DatabaseFactory;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.ProfileHandler;
import de.dbtool.files.schemas.Profile;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import picocli.CommandLine;

import java.util.List;
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

    @CommandLine.ArgGroup(exclusive = false, multiplicity = "0..*", heading = "Table content/value regex options")
    private List<ValueRegexOption> valueRegexOptions;

    @CommandLine.Option(names = {"-r", "--range"}, description = "Search for a range in table values", required = false)
    private String rangeQuery;

    @CommandLine.Option(names = {"-lc", "--limit-columns"}, description = "Limits the number of columns to display", required = false)
    private String limitColumnsQuery;

    @Min(value = 1, message = "Limit rows must be greater than 0")
    @CommandLine.Option(names = {"-lr", "--limit-rows"}, description = "Limits the number of rows to display", required = false)
    private int limitRows;

    @Min(value = 1, message = "Limit text length must be greater than 0")
    @CommandLine.Option(names = {"-lt", "--limit-text-length"}, description = "Limits the length of text in a column and display ellipsis", required = false)
    private int limitTextLength;

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

            if (profile == null) throw new DbToolException("Profile not found");

            System.out.println("Using profile: " + profile.name);

            Query query = new Query(tablePatternOptions, tableRegexOptions, columnPatternOptions, columnRegexOptions, valuePatternOptions, valueRegexOptions);
            IDatabase database = DatabaseFactory.getDatabaseType(profile);
            QueryProcessor queryProcessor = new QueryProcessor(database, query);
            List<String[]> result = queryProcessor.executeQuery();

        } catch (DbToolException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
