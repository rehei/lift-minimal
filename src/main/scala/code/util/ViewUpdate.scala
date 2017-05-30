package code.util

import scala.xml.NodeSeq
import net.liftweb.http.CometActor
import net.liftweb.http.js.JsCmds
import net.liftweb.util.Helpers
import net.liftweb.http.js.JE.JsVar
import net.liftweb.http.js.jquery.JqJE
import net.liftweb.http.js.JE

class ViewUpdate(listener: ReactiveListener, block: () => NodeSeq) {

  val id = Helpers.nextFuncName

  def modified() = {
    listener.modified(this)
  }

  def render() = {
    <div id={ id } data-x="viewupdate">
      {
        block()
      }
    </div>
  }

  def updateCmd() = {
    JsCmds.SetHtml(id, block())
  }

}