package de.dbtool.database;

import de.dbtool.database.interfaces.IDatabase;
import de.dbtool.exceptions.DbToolException;

import java.util.List;

public class QueryProcessor {
    private Query query;

    private final IDatabase db;

    public QueryProcessor(IDatabase db, Query query) throws DbToolException {
        this.db = db;
        this.query = query;
    }

    public List<String[]> executeQuery() throws DbToolException {
        if (query == null) throw new RuntimeException("Query is null");
        // TODO: Implement query execution
        this.db.connect();

        System.out.println(this.db.getAllDatabaseTables().toString());

        return null;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

}
