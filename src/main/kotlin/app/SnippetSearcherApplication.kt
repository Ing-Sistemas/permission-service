package app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SnippetSearcherApplication

fun main(args: Array<String>) {
	SpringApplication.run(SnippetSearcherApplication::class.java, *args)
}
