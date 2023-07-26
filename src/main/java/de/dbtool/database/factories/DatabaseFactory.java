package de.dbtool.database.factories;

import de.dbtool.database.interfaces.DefaultDatabase;
import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;

public class DatabaseFactory {
    public static IDatabase getDatabaseType(Profile profile) throws DbToolException {
        return new DefaultDatabase(profile);
    }
}
