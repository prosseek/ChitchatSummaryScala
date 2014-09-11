package akka

import akka.actor.{ActorSystem, Props, Actor, ActorLogging}

case class Greeting(who: String)

class GreetingActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(who) ⇒ log.info("Hello " + who)
  }
}

object TestAkka extends App {
  val system = ActorSystem("MySystem")
  val greeter = system.actorOf(Props[GreetingActor], name = "greeter")
  greeter ! Greeting("Charlie Parker")
}
