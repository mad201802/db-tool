package de.dbtool.cli;

import de.dbtool.cli.subcommands.CreateProfileCommand;
import de.dbtool.cli.subcommands.GrepCommand;
import de.dbtool.cli.subcommands.ListProfilesCommand;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;

/**
 * Main command for the db-tool
 */
@CommandLine.Command(name = "db-tool", version = "0.0.1", description = "Tool to help manage databases", mixinStandardHelpOptions = true, subcommands = {CreateProfileCommand.class, ListProfilesCommand.class, GrepCommand.class})
public class DbToolCommand implements Runnable {

    @CommandLine.Spec
    CommandSpec spec;

    @Override
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand\n");
    }
}
