package code.snippet

import net.liftweb.http.LiftRules
import net.liftweb.http.S
import net.liftweb.util.Props

class MyModeSnippet {

  def render() = {
    <strong>{ Props.mode.toString() }</strong>
  }

}