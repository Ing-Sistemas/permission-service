package app

import health.HealthController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackageClasses = [HealthController::class])
class SnippetSearcherApplication

fun main(args: Array<String>) {
	SpringApplication.run(SnippetSearcherApplication::class.java, *args)
}
