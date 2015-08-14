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

    setupDB()
  }

  def setupDB() {
    val sessionFactory = createSessionFactory(loadConfig())
    val session = sessionFactory.openSession()

    val transaction = session.getTransaction()
    transaction.begin()
    val p1 = new Person()
    p1.name = "Heiner"
    p1.age = 5
    session.save(p1)
    transaction.commit()
    println("PERSON" + session.createQuery("FROM Person").list().map(_.asInstanceOf[Person]).get(0).age)
  }

  def addAnnotatedClassesFrom(cfg: Configuration) = {
    val path = Keep.getClass().getPackage().getName()
    val reflections = new Reflections(path)
    for (clazz <- reflections.getTypesAnnotatedWith(classOf[Entity])) {
      cfg.addAnnotatedClass(clazz)
    }
  }

  def loadConfig() = {
    val cfg = new Configuration();
    val cfgFile = LiftRules.context.resource("/WEB-INF/hibernate.cfg.xml")
    cfg.configure(cfgFile)
  }

  def createSessionFactory(cfg: Configuration) = {
    val driverString = cfg.getProperty(HIBERNATE_CONNECTION_DRIVER)
    val connectionString = cfg.getProperty(HIBERNATE_CONNECTION_URL)
    loadDriver(driverString)
    DriverManager.getConnection(connectionString)
    if (connectionString != null && connectionString.startsWith("jdbc:h2:mem:")) {
      org.h2.tools.Server.createWebServer().start()
    }
    addAnnotatedClassesFrom(cfg)
    val registry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties())
    cfg.buildSessionFactory(registry.build())
  }

  def loadDriver(driverClassName: String) {
    Class.forName(driverClassName)
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
