package com.backend.global.config;

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["com.backend.domain.chat.repository"])
@EnableMongoAuditing
class MongoDBConfig(
	@Value("\${spring.data.mongodb.uri}")
	val uri: String
) {

	@Bean
	fun mongoDatabaseFactory(): MongoDatabaseFactory = SimpleMongoClientDatabaseFactory(uri)

	@Bean
	fun mongoTemplate(): MongoTemplate = MongoTemplate(mongoDatabaseFactory())
}
