package lab.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import lab.model.Customer;
import lab.model.Squishee;

@Aspect
public class Politeness {

  @Before("execution(* sellSquishee(..))")
  public void sayHello(JoinPoint joinPoint) {
    AopLog.append(
        "Hello " + ((Customer) joinPoint.getArgs()[0]).getName() + ". How are you doing? \n");
  }

  @AfterReturning(pointcut = "execution(* sellSquishee(..))",
      returning = "retVal", argNames = "retVal")
  public void askOpinion(Object retVal) {
    AopLog.append("Is " + ((Squishee) retVal).getName() + " Good Enough? \n");
  }

  @AfterThrowing("execution(* sellSquishee(..))")
  public void sayYouAreNotAllowed() {
    AopLog.append("Hmmm... \n");
  }

  @After("execution(* sellSquishee(..))")
  public void sayGoodBye() {
    AopLog.append("Good Bye! \n");
  }

  @Around("execution(* sellSquishee(..))")
  public Object sayPoliteWordsAndSell(ProceedingJoinPoint pjp) throws Throwable {
    AopLog.append("Hi! \n");
    Object retVal = pjp.proceed();
    AopLog.append("See you! \n");
    return retVal;
  }

}