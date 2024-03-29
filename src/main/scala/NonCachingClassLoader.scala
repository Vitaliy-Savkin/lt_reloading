package com.epam

import java.io.{FileInputStream, IOException, DataInputStream, File}
import java.net.MalformedURLException

class NonCachingClassLoader(classFilter: String => Boolean)
                           (implicit parent: ClassLoader)
  extends ClassLoader(parent){

  protected def classNameToPath(name: String): String =
    if (name endsWith ".class") name
    else name.replace('.', '/') + ".class"

  override def loadClass(name: String): Class[_] =
    try {
      if(!classFilter(name)) parent.loadClass(name)
      else {
        val fileURL = parent.getResource(classNameToPath(name))
        val file = new File(fileURL.getFile)
        val classData = Array.ofDim[Byte](file.length.toInt)
        val dis = new DataInputStream(new FileInputStream(file))
        dis.readFully(classData)
        dis.close()
        defineClass(name, classData, 0, classData.length)
      }
    } catch {
      case e @ (_: MalformedURLException | _: IOException) => null
    }
}
