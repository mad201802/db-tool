package de.dbtool.cli.subcommands;

import picocli.CommandLine;

/**
 * Subcommand to list all available profiles
 */
@CommandLine.Command(name = "list-profiles", description = "List all available profiles", mixinStandardHelpOptions = true)
public class ListProfilesCommand implements Runnable {

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Show more information about the profiles", required = false)
    private boolean verbose = false;

    @Override
    public void run() {
        // TODO: Implement this
    }
}
