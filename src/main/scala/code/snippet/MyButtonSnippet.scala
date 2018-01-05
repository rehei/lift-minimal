package code.snippet

import scala.xml.NodeSeq

import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds
import net.liftweb.util.Helpers

class MyButtonSnippet {

  def render() = {

    val id = Helpers.nextFuncName

    <button id={ id }>MyButton
    </button>
    <script>
      { "$('#" + id + "').click(function() { " + SHtml.ajaxInvoke { () => myBlubbFunction }.toJsCmd + " })" }
    </script>

  }

  def myBlubbFunction = {
    println("foobar")
    JsCmds.Alert("Blubbi")
  }

}