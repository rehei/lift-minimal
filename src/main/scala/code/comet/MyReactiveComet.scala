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

class MyReactiveComet extends CometActor with ReactiveListener {

  val person = new Person()
  val model = new ReactiveModel(this, person)

  val runnable = new Runnable() {
    override def run() = {
      while (true) {
        Thread.sleep(1000)
        model.modify(m => m.age = m.age + 1)
      }
    }
  }

  new Thread(runnable).start()

  override def modified(viewUpdate: ViewUpdate) = {
    partialUpdate(viewUpdate.updateCmd())
  }
  
  override def render = {
    for (x <- model) yield {
      <b>{ x.age }</b>
    }
  }

}