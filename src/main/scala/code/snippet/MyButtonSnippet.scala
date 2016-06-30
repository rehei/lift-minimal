package code.snippet

import scala.xml.NodeSeq

import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds
import net.liftweb.util.Helpers.strToCssBindPromoter
import net.liftweb.util.ToCssBindPromoter
import net.liftweb.util.Helpers

class MyButtonSnippet {

  def render() = {

    //    val x: String = "button [onclick]"
    //    val y: ToCssBindPromoter = "button [onclick]"

    //    val update = "button [onclick]" #> SHtml.ajaxInvoke { myBlubbFunction _ }

    //    update(in) 

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