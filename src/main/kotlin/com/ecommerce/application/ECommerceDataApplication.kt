package com.ecommerce.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.ecommerce.*"])
@EnableJpaRepositories("com.ecommerce.repository")
@EntityScan("com.ecommerce.model")
class ECommerceDataApplication

fun main(args: Array<String>) {
	runApplication<ECommerceDataApplication>(*args)
}
