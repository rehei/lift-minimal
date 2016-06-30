package code.snippet

import net.liftweb.http.LiftRules
import net.liftweb.http.S
import net.liftweb.util.Props
import scala.xml.NodeSeq

class MyModeSnippet {

  def render(blub: NodeSeq) = {
    <strong>{ Props.mode.toString() + blub } { blub.getClass.getName }</strong>
  }

}