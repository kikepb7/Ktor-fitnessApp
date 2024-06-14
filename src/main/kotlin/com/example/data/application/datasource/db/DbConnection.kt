package com.example.data.application.datasource.db

import org.ktorm.database.Database

object DbConnection {
    private val db: Database? = null

    fun getDatabaseInstance(): Database {
        return db ?: Database.connect(
            url = "jdbc:mysql://127.0.0.1:3306/ktor-fitness",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = ""
        )
    }
}