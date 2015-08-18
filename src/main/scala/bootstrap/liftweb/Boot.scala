package bootstrap.liftweb

import java.sql.DriverManager
import scala.language.postfixOps
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.slf4j.LoggerFactory
import org.webjars.MultipleMatchesException
import org.webjars.WebJarAssetLocator
import net.liftweb._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.sitemap.Loc._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import code.model.Person
import org.reflections.Reflections
import javax.persistence.Entity
import scala.collection.JavaConversions._
import java.io.File
import net.liftweb.http.provider.servlet.HTTPServletContext
import java.net.URL
import code.Keep

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {

  val HIBERNATE_CONNECTION_URL = "hibernate.connection.url"
  val HIBERNATE_CONNECTION_DRIVER = "hibernate.connection.driver_class"

  object H2 {
    var connection: Box[java.sql.Connection] = None
    var webserver: Box[org.h2.tools.Server] = None
  }

  def boot {
    LoggerFactory.getLogger(this.getClass).info("Booting...")

    // where to search snippet
    LiftRules.addToPackages("code")

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    serveWebjars()
  }

  def serveWebjars() {
    val locator = new WebJarAssetLocator()
    LiftRules.statelessDispatch.prepend(NamedPF("Webjar service") {
      case r @ Req(prefix :: tail, suffx, _) if (prefix == "webjars") => {
        try {
          val path = locator.getFullPath(S.uri.drop("/webjars/".length)).split("/").drop(1).toList
          ResourceServer.findResourceInClasspath(r, path)
        } catch {
          case _: IllegalArgumentException => () => Empty
          case _: MultipleMatchesException => () => Empty
        }
      }
    })
  }

}
