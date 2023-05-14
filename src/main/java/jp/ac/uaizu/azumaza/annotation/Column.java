package jp.ac.uaizu.azumaza.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jp.ac.uaizu.azumaza.dao.DataBase.ColumnType;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {

    String name();

    ColumnType type() default ColumnType.STRING;
}
