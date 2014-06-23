package org.akka.essentials.scala.router.example.scattergatherfirstcomplete

import java.util.concurrent.TimeUnit

import akka.dispatch.Await
import org.akka.essentials.scala.router.example.MsgEchoActor
import org.akka.essentials.scala.router.example.RandomTimeActor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.routing.ScatterGatherFirstCompletedRouter
import akka.util.{Duration, Timeout}

object Example {
  def main(args: Array[String]): Unit = {
    val _system = ActorSystem("SGFCRouterExample")
    val scatterGatherFirstCompletedRouter = _system.actorOf(Props[RandomTimeActor].withRouter(
      ScatterGatherFirstCompletedRouter(nrOfInstances = 5, within = Duration.apply(5, TimeUnit.SECONDS))), name = "mySGFCRouterActor")

    implicit val timeout = Timeout.apply(5, TimeUnit.SECONDS)
    val futureResult = scatterGatherFirstCompletedRouter ? "message"
    val result = Await.result(futureResult, timeout.duration)
    System.out.println(result)

    _system.shutdown()
  }
}