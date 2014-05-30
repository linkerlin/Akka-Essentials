package org.akka.essentials.scala.supervisor.example3
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

case class Result()
case class DeadWorker()
case class RegisterWorker(worker: ActorRef, supervisor: ActorRef)

object MyActorSystem {
  
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("faultTolerance")

    val supervisor = system.actorOf(Props[SupervisorActor], name = "supervisor")

    val mesg: Int = 8
    supervisor ! mesg

    supervisor ! "Do Something"

    Thread.sleep(4000)

    supervisor ! mesg

    system.shutdown()
  }
}