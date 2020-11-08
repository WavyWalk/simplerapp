/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.generated.DefaultSchema;
import org.jooq.generated.Keys;
import org.jooq.generated.tables.records.UsersRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Users extends TableImpl<UsersRecord> {

    private static final long serialVersionUID = 1771144023;

    /**
     * The reference instance of <code>users</code>
     */
    public static final Users USERS = new Users();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsersRecord> getRecordType() {
        return UsersRecord.class;
    }

    /**
     * The column <code>users.id</code>.
     */
    public final TableField<UsersRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('users_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>users.created_at</code>.
     */
    public final TableField<UsersRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>users.updated_at</code>.
     */
    public final TableField<UsersRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), org.jooq.impl.SQLDataType.LOCALDATETIME, this, "");

    /**
     * The column <code>users.name</code>.
     */
    public final TableField<UsersRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * Create a <code>users</code> table reference
     */
    public Users() {
        this(DSL.name("users"), null);
    }

    /**
     * Create an aliased <code>users</code> table reference
     */
    public Users(String alias) {
        this(DSL.name(alias), USERS);
    }

    /**
     * Create an aliased <code>users</code> table reference
     */
    public Users(Name alias) {
        this(alias, USERS);
    }

    private Users(Name alias, Table<UsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Users(Table<O> child, ForeignKey<O, UsersRecord> key) {
        super(child, key, USERS);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<UsersRecord, Long> getIdentity() {
        return Keys.IDENTITY_USERS;
    }

    @Override
    public Users as(String alias) {
        return new Users(DSL.name(alias), this);
    }

    @Override
    public Users as(Name alias) {
        return new Users(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(String name) {
        return new Users(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Name name) {
        return new Users(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, LocalDateTime, LocalDateTime, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}