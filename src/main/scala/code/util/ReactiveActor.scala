package code.util

import net.liftweb.http.CometActor

trait ReactiveActor extends CometActor with ReactiveListener {

  override def modified(viewUpdate: ViewUpdate) = {
    partialUpdate(viewUpdate.updateCmd())
  }

}