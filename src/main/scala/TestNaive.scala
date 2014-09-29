package com.epam
object TestNaive extends App {
  // first time we load class
  val c1 = getClass.getClassLoader.loadClass("com.epam.BasicModule") // it's easy
  var i: BasicModule = c1.newInstance().asInstanceOf[BasicModule]
  i.method() // "A1"
  // wait, change and recompile BasicModule.scala(println("A2"))
  readLine()
  // then we try to reload
  // name is the same, but class has been changed
  val c2 = getClass.getClassLoader.loadClass("com.epam.BasicModule")
  i = c2.newInstance().asInstanceOf[BasicModule]
  i.method() // "A1" WTF???
}
