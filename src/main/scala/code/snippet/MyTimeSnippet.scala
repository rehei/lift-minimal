package code
package snippet

import scala.xml.{ NodeSeq, Text }
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import Helpers._
import org.slf4j.LoggerFactory
import java.util.logging.Level

class MyTimeSnippet {

  def render(in: NodeSeq) = {
    <text>{ new Date().toString() }</text>
  }

}

