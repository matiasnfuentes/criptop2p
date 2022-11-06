package ar.edu.unq.criptop2p.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class LogControllerExecutionAspectAnnotation {

    var logger: Logger = LogManager.getLogger(LogControllerExecutionAspectAnnotation::class.java)

    @Around("within(ar.edu.unq.criptop2p.controller.*)")
    @Throws(Throwable::class)
    fun logControllerExecutionAnnotation(joinPoint: ProceedingJoinPoint): Any? {

        val start = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val executionTime = System.currentTimeMillis() - start
        val args = joinPoint.args.joinToString()

        logger.info("/////// Start Web-Services audit ///////")
        logger.info(joinPoint.signature.declaringTypeName + "." + joinPoint.signature.name + " executed in " + executionTime + "ms")
        if (joinPoint.args.isEmpty()) {
            logger.info("Ran without parameters")
        } else {
            logger.info("Parameters: $args")
        }
        logger.info("/////// End Web-Services audit ///////")

        return proceed

    }

}