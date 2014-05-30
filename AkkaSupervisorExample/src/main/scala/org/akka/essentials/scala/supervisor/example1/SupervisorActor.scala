package org.akka.essentials.scala.supervisor.example1
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration


class SupervisorActor extends Actor with ActorLogging {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._


  val childActor = context.actorOf(Props[WorkerActor], name = "workerActor")

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = FiniteDuration(10, TimeUnit.SECONDS)) {

    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  def receive = {
    case result: Result =>
      childActor.tell(result, sender)
    case msg: Object =>
      childActor ! msg

  }
}