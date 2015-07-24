package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import scala.language.postfixOps
import org.webjars.WebJarAssetLocator
import org.webjars.MultipleMatchesException

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {

    // where to search snippet
    LiftRules.addToPackages("code")

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => {
      notices match {
        case NoticeType.Notice => Full((8 seconds, 4 seconds))
        case _ => Empty
      }
    })

    ResourceServer.baseResourceLocation = "META-INF"
    ResourceServer.allow {
      case _ => true
    }

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
