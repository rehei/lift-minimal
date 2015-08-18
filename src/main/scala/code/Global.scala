package code

import net.liftweb.http.RequestVar
import java.sql.DriverManager
import net.liftweb.http.LiftRules
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.reflections.Reflections
import javax.persistence.Entity
import net.liftweb.common.Box
import scala.collection.JavaConversions._
import net.liftweb.http.Factory

object Global extends Factory {

  val HIBERNATE_CONNECTION_URL = "hibernate.connection.url"
  val HIBERNATE_CONNECTION_DRIVER = "hibernate.connection.driver_class"

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
    val registry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties())
    cfg.buildSessionFactory(registry.build())
  }

  def loadDriver(driverClassName: String) {
    Class.forName(driverClassName)
  }

  val cfg = loadConfig()
  val driverString = cfg.getProperty(HIBERNATE_CONNECTION_DRIVER)
  val connectionString = cfg.getProperty(HIBERNATE_CONNECTION_URL)
  loadDriver(driverString)
  DriverManager.getConnection(connectionString)
  if (connectionString != null && connectionString.startsWith("jdbc:h2:mem:")) {
    org.h2.tools.Server.createWebServer().start()
  }
  addAnnotatedClassesFrom(cfg)

  val sessionFactory = createSessionFactory(cfg)
  object session extends RequestVar(sessionFactory.openSession()) {
    override def onShutdown(session: CleanUpParam): Unit = {
      println(this.get.clear())
    }
  }

}