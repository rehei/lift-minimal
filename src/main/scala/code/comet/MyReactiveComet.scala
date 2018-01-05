package code.comet

import java.util.Date

import net.liftweb.actor.LAPinger
import net.liftweb.http.CometActor
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Helpers
import scala.xml.NodeSeq
import scala.collection.mutable.ListBuffer
import net.liftweb.http.js.JsCmds
import code.util.ReactiveModel
import code.util.ViewUpdate
import code.model.Person
import code.util.ReactiveListener
import code.util.ReactiveActor

class MyReactiveComet extends ReactiveActor {

  val person1 = new Person()
  val person2 = new Person()
  val m1 = new ReactiveModel(this, person1)
  val m2 = new ReactiveModel(this, person2)

  val runnable = new Runnable() {
    override def run() = {
      while (true) {
        Thread.sleep(1000)
        m1.modify(m => m.age = m.age + 1)
        m2.modify(m => m.age = m.age + 3)
      }
    }
  }

  new Thread(runnable).start()

  override def render = {
    <div>
      {
        for (x <- m1) yield {
          <b>{ x.age }</b>
        }
      }
      {
        for (x <- m2) yield {
          <b>{ x.age }</b>
        }
      }
    </div>
  }

}