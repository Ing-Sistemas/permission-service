package com.example.springboot.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PermissionServiceApplication


fun main(args: Array<String>) {
    runApplication<PermissionServiceApplication>(*args)
}

// IDK what this is equisde
//    @Bean
//    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
//        return CommandLineRunner { args: Array<String?>? ->
//            val beanNames = ctx.beanDefinitionNames
//            Arrays.sort(beanNames)
//            for (beanName in beanNames) {
//                println(beanName)
//            }
//        }
//    }
//
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            SpringApplication.run(Application::class.java, *args)
//        }
//    }
//}