package code.util

import scala.xml.NodeSeq
import net.liftweb.http.CometActor
import net.liftweb.util.Helpers

class ReactiveModel[T](listener: ReactiveListener, val data: T) {

  val binder = new ReactiveBinder(data)

  def modify(block: (T) => Unit) = {
    binder.modify(block)
  }

  def foreach(block: T => NodeSeq) {
    map(block)
  }

  def map(block:T => NodeSeq) = {
    val viewUpdate = new ViewUpdate(listener, () => block(data))
    binder.addListener(viewUpdate)

    viewUpdate.render()
  }

}