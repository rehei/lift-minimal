package code.snippet

import code.Global
import net.liftweb.http.S
import code.model.Person
import net.liftweb.util.LoanWrapper
import scala.collection.JavaConversions._

class MyPersonSnippet {

  def render = {
    val session = Global.session.get
    val transaction = session.getTransaction()
    transaction.begin()
    val p1 = new Person()
    p1.name = "Heiner"
    p1.age = 5
    session.save(p1)
    transaction.commit()
    println("PERSON" + session.createQuery("FROM Person").list().map(_.asInstanceOf[Person]).get(0).age)
    <strong>{ "Person" + p1.name }</strong>
  }

}