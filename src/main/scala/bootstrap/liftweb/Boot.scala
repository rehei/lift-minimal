package bootstrap.liftweb

import scala.language.postfixOps
import org.slf4j.LoggerFactory
import com.github.rehei.lift.webjars.Webjars
import net.liftweb.http.Html5Properties
import net.liftweb.http.LiftRules
import net.liftweb.http.LiftRulesMocker.toLiftRules
import net.liftweb.http.Req
import net.liftweb.util.Vendor.valToVendor
import code.model.Person

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {

  def boot {
    LoggerFactory.getLogger(this.getClass).info("Booting...")

    // where to search snippet
    LiftRules.addToPackages("code")

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    Webjars.init
  }

}
