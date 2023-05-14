package jp.ac.uaizu.azumaza.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.ac.uaizu.azumaza.annotation.Column;
import jp.ac.uaizu.azumaza.annotation.Table;
import jp.ac.uaizu.azumaza.dto.Record;

public class DataBase <E extends Record> {

    /**
     * $ ip r
     * > default via 172.17.208.1 dev eth0
     * > 172.17.208.0/20 dev eth0 proto kernel scope link src '''172.27.247.161'''
     * $ sudo service mysql start
     */
    public static final String IP = "172.27.247.161";

    public static final String URL = "jdbc:mysql://" + IP + ":3306/azumaza";

    public static final String USER = "azumaza";

    public static final String PASS = "mysql";

    public enum ColumnType {
        INTEGER,
        DATETIME,
        STRING;
    }

    protected Optional<E> executeQueryFirst(Class<E> clazz, String query, Map<Integer, Object> param) throws Exception {
        List<E> results = executeQuery(clazz, query, param);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }

    public int insert(E record) throws Exception {
        final Map<Integer, Object> param = new HashMap<>();
        final Class<?> clazz = record.getClass();

        final List<String> fields = new ArrayList<>();
        int index = 1;
        for(Field field: clazz.getDeclaredFields()) {
            final Column annotation = field.getDeclaredAnnotation(Column.class);
            if (annotation == null) continue;

            fields.add(annotation.name());
            field.setAccessible(true);
            param.put(index, field.get(record));
            index++;
        }

        final Table table = clazz.getAnnotation(Table.class);
        final StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(table.value()).append(" (").append(String.join(",", fields)).append(") ");
        query.append("VALUES (").append(String.join(",", fields.stream().map(f -> "?").collect(Collectors.toList()))).append(") ");

        System.out.println(query.toString());

        return executeUpdate(query.toString(), param);
    }

    protected int executeUpdate(String query, Map<Integer, Object> param) throws Exception {
        final Connection connection = getConnection();
        final PreparedStatement  statement = connection.prepareStatement(query);

        if (param != null) {
            applyParameter(statement, param);
        }

        return statement.executeUpdate();
    }

    protected List<E> executeQuery(Class<E> clazz, String query, Map<Integer, Object> param) throws Exception {

        final Connection connection = getConnection();
        final PreparedStatement  statement = connection.prepareStatement(query);

        if (param != null) {
            applyParameter(statement, param);
        }

        final ResultSet rowResults = statement.executeQuery();

        final List<E> results = new ArrayList<>();
        while (rowResults.next()) {
            results.add(resolveResult(rowResults, clazz));
        }
        rowResults.close();
        statement.close();
        connection.close();

        return results;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, USER, PASS);
    }

    private void applyParameter(PreparedStatement  statement, Map<Integer, Object> param) throws SQLException {
        for(Entry<Integer, Object> entry : param.entrySet()) {
            final Object value = entry.getValue();
            final Integer key = entry.getKey();

            if (value instanceof String) {
                statement.setString(key, (String) value);
            } else if (value instanceof Integer) {
                statement.setInt(key, (Integer) value);
            } else if (value instanceof LocalDateTime) {
                statement.setTimestamp(key, Timestamp.valueOf((LocalDateTime) value));
            } else {
                statement.setString(key, value.toString());
            }
        }
    }

    private E resolveResult(ResultSet rowResults, Class<E> clazz) throws Exception {
        final E result = clazz.getConstructor().newInstance();
        for(Field field: clazz.getSuperclass().getDeclaredFields()) {
            final Column annotation = field.getDeclaredAnnotation(Column.class);
            if (annotation == null) continue;

            field.setAccessible(true);
            field.set(result, getValue(rowResults, annotation));
        }
        for(Field field: clazz.getDeclaredFields()) {
            final Column annotation = field.getDeclaredAnnotation(Column.class);
            if (annotation == null) continue;

            field.setAccessible(true);
            field.set(result, getValue(rowResults, annotation));
        }
        return result;
    }

    private Object getValue(ResultSet rowResults, Column annotation) throws SQLException {
        switch(annotation.type()) {
        case INTEGER:
            return rowResults.getInt(annotation.name());
        case DATETIME:
            final Optional<Timestamp> timestamp = Optional.ofNullable(rowResults.getTimestamp(annotation.name()));
            return timestamp.map(Timestamp::toLocalDateTime).orElse(null);
        case STRING:
        default:
            return rowResults.getString(annotation.name());
        }
    }
}
