/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row1;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.generated.DefaultSchema;
import org.jooq.generated.tables.records.SchemaMigrationsRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaMigrations extends TableImpl<SchemaMigrationsRecord> {

    private static final long serialVersionUID = -643607150;

    /**
     * The reference instance of <code>schema_migrations</code>
     */
    public static final SchemaMigrations SCHEMA_MIGRATIONS = new SchemaMigrations();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SchemaMigrationsRecord> getRecordType() {
        return SchemaMigrationsRecord.class;
    }

    /**
     * The column <code>schema_migrations.version</code>.
     */
    public final TableField<SchemaMigrationsRecord, String> VERSION = createField(DSL.name("version"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * Create a <code>schema_migrations</code> table reference
     */
    public SchemaMigrations() {
        this(DSL.name("schema_migrations"), null);
    }

    /**
     * Create an aliased <code>schema_migrations</code> table reference
     */
    public SchemaMigrations(String alias) {
        this(DSL.name(alias), SCHEMA_MIGRATIONS);
    }

    /**
     * Create an aliased <code>schema_migrations</code> table reference
     */
    public SchemaMigrations(Name alias) {
        this(alias, SCHEMA_MIGRATIONS);
    }

    private SchemaMigrations(Name alias, Table<SchemaMigrationsRecord> aliased) {
        this(alias, aliased, null);
    }

    private SchemaMigrations(Name alias, Table<SchemaMigrationsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> SchemaMigrations(Table<O> child, ForeignKey<O, SchemaMigrationsRecord> key) {
        super(child, key, SCHEMA_MIGRATIONS);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public SchemaMigrations as(String alias) {
        return new SchemaMigrations(DSL.name(alias), this);
    }

    @Override
    public SchemaMigrations as(Name alias) {
        return new SchemaMigrations(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SchemaMigrations rename(String name) {
        return new SchemaMigrations(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SchemaMigrations rename(Name name) {
        return new SchemaMigrations(name, null);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }
}
