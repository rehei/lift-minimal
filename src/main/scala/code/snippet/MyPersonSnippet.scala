package code.snippet

import net.liftweb.http.S
import code.model.Person
import net.liftweb.util.LoanWrapper
import scala.collection.JavaConversions._

class MyPersonSnippet {

  def render = {
    val p1 = new Person()
    p1.name = "Heiner"
    p1.age = 5
    <strong>{ "Person" + p1.name }</strong>
  }

}