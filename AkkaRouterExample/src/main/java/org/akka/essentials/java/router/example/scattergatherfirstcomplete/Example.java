package org.akka.essentials.java.router.example.scattergatherfirstcomplete;

import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.util.Duration;
import org.akka.essentials.java.router.example.RandomTimeActor;

//import scala.concurrent.Await;
//import scala.concurrent.Future;
//import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.ScatterGatherFirstCompletedRouter;
import akka.util.Timeout;

public class Example {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ActorSystem _system = ActorSystem
				.create("ScatterGatherFirstCompletedRouterExample");
		ActorRef scatterGatherFirstCompletedRouter = _system.actorOf(new Props(
				RandomTimeActor.class)
				.withRouter(new ScatterGatherFirstCompletedRouter(5, Duration
						.parse("5 seconds"))),"myScatterGatherFirstCompletedRouterActor");

		Timeout timeout = new Timeout(Duration.parse("10 seconds"));
		Future<Object> futureResult = akka.pattern.Patterns.ask(
				scatterGatherFirstCompletedRouter, "message", timeout);
		String result = (String) Await.result(futureResult, timeout.duration());
		System.out.println(result);

		_system.shutdown();

	}

}
