/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.generated.DefaultSchema;
import org.jooq.generated.Indexes;
import org.jooq.generated.tables.records.AccountsRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Accounts extends TableImpl<AccountsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>accounts</code>
     */
    public static final Accounts ACCOUNTS = new Accounts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountsRecord> getRecordType() {
        return AccountsRecord.class;
    }

    /**
     * The column <code>accounts.id</code>.
     */
    public final TableField<AccountsRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>accounts.user_id</code>.
     */
    public final TableField<AccountsRecord, Long> USER_ID = createField(DSL.name("user_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>accounts.password</code>.
     */
    public final TableField<AccountsRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>accounts.email</code>.
     */
    public final TableField<AccountsRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>accounts.created_at</code>.
     */
    public final TableField<AccountsRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>accounts.updated_at</code>.
     */
    public final TableField<AccountsRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6), this, "");

    private Accounts(Name alias, Table<AccountsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Accounts(Name alias, Table<AccountsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>accounts</code> table reference
     */
    public Accounts(String alias) {
        this(DSL.name(alias), ACCOUNTS);
    }

    /**
     * Create an aliased <code>accounts</code> table reference
     */
    public Accounts(Name alias) {
        this(alias, ACCOUNTS);
    }

    /**
     * Create a <code>accounts</code> table reference
     */
    public Accounts() {
        this(DSL.name("accounts"), null);
    }

    public <O extends Record> Accounts(Table<O> child, ForeignKey<O, AccountsRecord> key) {
        super(child, key, ACCOUNTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.INDEX_ACCOUNTS_ON_USER_ID);
    }

    @Override
    public Identity<AccountsRecord, Long> getIdentity() {
        return (Identity<AccountsRecord, Long>) super.getIdentity();
    }

    @Override
    public Accounts as(String alias) {
        return new Accounts(DSL.name(alias), this);
    }

    @Override
    public Accounts as(Name alias) {
        return new Accounts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Accounts rename(String name) {
        return new Accounts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Accounts rename(Name name) {
        return new Accounts(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Long, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
