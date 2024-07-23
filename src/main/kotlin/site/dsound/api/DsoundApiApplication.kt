package site.dsound.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DsoundApiApplication

fun main(args: Array<String>) {
    runApplication<DsoundApiApplication>(*args)
}
