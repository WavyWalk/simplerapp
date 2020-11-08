/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.generated.tables.SchemaMigrations;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaMigrationsRecord extends TableRecordImpl<SchemaMigrationsRecord> implements Record1<String> {

    private static final long serialVersionUID = 1301304992;

    /**
     * Setter for <code>schema_migrations.version</code>.
     */
    public void setVersion(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>schema_migrations.version</code>.
     */
    public String getVersion() {
        return (String) get(0);
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    public Row1<String> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return SchemaMigrations.SCHEMA_MIGRATIONS.VERSION;
    }

    @Override
    public String component1() {
        return getVersion();
    }

    @Override
    public String value1() {
        return getVersion();
    }

    @Override
    public SchemaMigrationsRecord value1(String value) {
        setVersion(value);
        return this;
    }

    @Override
    public SchemaMigrationsRecord values(String value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SchemaMigrationsRecord
     */
    public SchemaMigrationsRecord() {
        super(SchemaMigrations.SCHEMA_MIGRATIONS);
    }

    /**
     * Create a detached, initialised SchemaMigrationsRecord
     */
    public SchemaMigrationsRecord(String version) {
        super(SchemaMigrations.SCHEMA_MIGRATIONS);

        set(0, version);
    }
}
