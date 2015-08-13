package util

class ReflectUtil {

  def getPackageWithSlashes(clazz: java.lang.Class[_]) = {
    val packages = clazz.getCanonicalName().split('.')
    packages.dropRight(1).mkString("/")
  }

}