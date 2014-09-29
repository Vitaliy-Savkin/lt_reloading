package com.epam

object TestCustomCL extends App {
  implicit val parent = this.getClass.getClassLoader
  // first time we load class
  val c1 = new NonCachingClassLoader(_ startsWith "com.epam.impl").loadClass("com.epam.impl.Module")
  var i: BasicModule = c1.newInstance().asInstanceOf[BasicModule]
  i.method() // "A1"
  // wait, change and recompile Module.scala (println("A2"))
  readLine()
  // then we try to reload
  val c2 = new NonCachingClassLoader(_ startsWith "com.epam.impl").loadClass("com.epam.impl.Module")
  i = c2.newInstance().asInstanceOf[BasicModule]
  i.method() // "A2" - Perfect!
}
