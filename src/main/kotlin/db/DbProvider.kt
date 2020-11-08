package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import conf.DbConf
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import javax.sql.DataSource

class DbProvider {

    companion object {

        fun initialize(dbConfig: DbConf.Companion) {
            val hikariConfig = HikariConfig().apply {
                username = dbConfig.userName
                jdbcUrl = dbConfig.jdbcUrl
                password = dbConfig.password
                driverClassName = dbConfig.driverClassName
            }
            dataSource = HikariDataSource(hikariConfig)

            val jooqConfig = DefaultConfiguration().also {
                it.set(dataSource)
                it.set(SQLDialect.POSTGRES)
            }
            dslContext = DSL.using(jooqConfig)
        }

        lateinit var dataSource: DataSource

        lateinit var dslContext: DSLContext
    }

}