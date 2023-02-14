package com.example.spring_boot_eks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootEksApplication

fun main(args: Array<String>) {
    runApplication<SpringBootEksApplication>(*args)
}
