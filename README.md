# DB-Tool - a Database Application Project

## Projektidee

- Programmiersprache: Java
- Kurzbeschreibung: Ein Java command line tool, welches es dem Nutzer erlaubt, seine Datenbanken zu verwalten.

### Commands

| command | description |
|---------|-------------|
| grep    | list        |

## Commands
### Create a new connection profile
To create a new connection profile, use the following command:
```
db-tool create-profile -n [NAME] -h [HOST] - p [PORT] -db [DATABASE_NAME] -u [USERNAME] -p [PASSWORD] -t [DATABASE_TYPE] -d [DRIVER_PATH]
```
The profile will be saved in the `profiles` folder.
With the `-d` option you can specify the path to the driver jar file. If you don't specify the path, the tool will only use the predefined drivers (MySQL, PostgreSql and SQLite).

It is important to correctly handle all possible errors/exceptions. For example, if the user enters a wrong database type, the tool should print an error message and exit.
Also, a wrong driver path should be handled correctly. If the config file is invalid, the tool should print an error message and exit.
If the computer is not connected to the internet and the user tries to connect to a database, the tool should print an error message and exit.
In general, the tool should not crash, but print an valuable error message and exit.

### List all connection profiles
To list all connection profiles, use the following command:
```
db-tool list-profiles
```

### Search trough a database
To search trough a database, this tool is offering multiple options. The user can search for a specific table, column or value.
A distinction is made between 2 things: the pattern (like) search and the regex search.
The pattern search is used to search for a specific value. The regex search is used to search for a pattern.
They both can be combined and used together. When used together the search will be executed like a AND operation.

#### Pattern search
To search for a specific value, use the following command:
```
db-tool grep -p [PROFILE_NAME] -tp [TABLE_PATTERN] -cp [COLUMN_PATTERN] -vp [VALUE_PATTERN]
```
The `-tp` option is used to specify the table pattern. Therefore, only the tables which match the pattern will be searched.
The `-cp` option is used to specify the column pattern. Therefore, only the columns which match the pattern will be searched.
The `-vp` option is used to specify the value pattern. Therefore, only the values which match the pattern will be searched.
We are also offering the possibility to use multiple patterns of the same type. This effects the search like a AND operation.

Here is a example, that will search in the `users` or `admin` table in the `name` column for the value that starts with `f`:
```
db-tool grep -p [PROFILE_NAME] -tp "users" -tp "admin" -cp "name" -vp "f*"
```

#### Regex search
We're also offering a regex search in addition to the pattern search. To use the regex search, use the following command:
```
db-tool grep -p [PROFILE_NAME] -tr [TABLE_REGEX] -cr [COLUMN_REGEX] -vr [VALUE_REGEX]
```

The `-tr` option is used to specify the table regex. Therefore, only the tables which match the regex will be searched.
The `-cr` option is used to specify the column regex. Therefore, only the columns which match the regex will be searched.
The `-vr` option is used to specify the value regex. Therefore, only the values which match the regex will be searched.
We are also offering the possibility to use multiple regex of the same type. This effects the search like a AND operation.
Regex Searches may be very inefficient, because they are executed on the client side.

Here is a example, that will search in the `users` or `admin` table in the `name` column for the value that starts with `f`:
```
db-tool grep -p [PROFILE_NAME] -tr "users|admin" -cr "name" -vr "^f.*"
```

#### Combining pattern and regex search
If you want to, for example, search in the `users` table in the `name` column for the value that only contains characters from `a` and `l`, you can use the following command:
```
db-tool grep -p [PROFILE_NAME] -tp "users" -cp "name" -vr "^[a-l]*$"
```

#### Range search
It is also possible to search for a range of values. They need to be numeric. To do this, use the following command:
```
db-tool grep -p [PROFILE_NAME] -tp "users" -cp "age" -vr "18:25"
```
The `-vr` option is used to specify the value range. Therefore, only the values which are in the range will be searched.
The range is specified by a colon. The first value is the lower bound and the second value is the upper bound.

It is also possible to use operations like `>`, `<`, `>=` and `<=` in the range. To do this, use the following command:
```
db-tool grep -p [PROFILE_NAME] -tp "users" -cp "age" -vr ">18"
```

If the column is not numeric, the tool will print an error message and exit.

### Limit the search result
#### Limit the number of columns
It is possible to limit the columns in the search result. To do this, use the following command:
```
db-tool grep -p [PROFILE_NAME] [...] -lc [COLUMN_INDEXES]
```
The `-lc` option is used to specify the column indexes. Therefore, only the columns with the specified indexes will be shown in the search result.
The column indexes are separated by a comma. The indexes start with 1.

Here is a example, that will search in the `users` table in the `name` column for the value that starts with `f` and only show the `id` (Index 1), `name` (Index 2) columns and the column between index 5 and index 7 :
```
db-tool grep -p [PROFILE_NAME] -tp "users" -cp "name" -vp "f*" -lc "1,2,5-7"
```

#### Limit the text length
It is possible to limit the text length in the search result. To do this, use the following command:
```
db-tool grep -p [PROFILE_NAME] [...] -lt [TEXT_LENGTH]
```
The `-lt` option is used to specify the text length. If the text is longer than the specified length, the text will be cut off and an ellipsis will be added.

Here is a example, that will search in the `users` table in the `name` column for the value that starts with `f` and only show the first 10 characters of the value:
```
db-tool grep -p [PROFILE_NAME] -tp "users" -cp "name" -vp "f*" -lt 10
```

#### Limit the number of rows
It is possible to limit the number of rows in the search result. To do this, use the following command:
```
db-tool grep -p [PROFILE_NAME] [...] -lr [ROW_COUNT]
```
The `-lr` option is used to specify the row count. Therefore, only the specified number of rows will be shown in the search result.

Here is a example, that will search in the `users` table in the `name` column for the value that starts with `f` and only show the first 10 rows:
```
db-tool grep -p [PROFILE_NAME] -tp "users" -cp "name" -vp "f*" -lr 10
```