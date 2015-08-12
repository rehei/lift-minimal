package code.comet

import net.liftweb.http.CometActor
import net.liftweb.util.Helpers
import net.liftweb.actor.LAScheduler
import net.liftweb.actor.LAPinger
import net.liftweb.http.js.JsCmds.SetHtml
import java.util.Date
import scala.xml.Text
import net.liftweb.http.js.JsCmds

class MyTimeComet extends CometActor {

  def render = {
    val id = Helpers.nextFuncName
    ping(id)
    <span id={ id }></span>
  }

  def ping(id: String) = {
    LAPinger.schedule(this, id, 1000)
  }

  override def lowPriority: PartialFunction[Any, Unit] = {
    case id: String => {
      val text = new Date().toString()
      partialUpdate(SetHtml(id, <span>{ text }</span>))
      ping(id)
    }
  }

}