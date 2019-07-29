package dev.anthonybruno.gymbuddy.common;

import dev.anthonybruno.gymbuddy.Server;
import dev.anthonybruno.gymbuddy.db.Database;

public abstract class Repository {

    protected static final Database db = Server.DATABASE;
    protected final String tableName;

    public Repository(String tableName) {
        this.tableName = tableName;
    }
}
