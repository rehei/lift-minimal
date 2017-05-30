package code.util

import scala.collection.mutable.ListBuffer

class ReactiveBinder[T](val data: T) {

  protected val listeners = new ListBuffer[ViewUpdate]()

  def modify(block: (T) => Unit) = {
    fireModified()
    block(data)
  }

  def addListener(viewUpdate: ViewUpdate) = {
    listeners.append(viewUpdate)
  }

  def fireModified() = {
    for (listener <- listeners) {
      listener.modified()
    }
  }

}
