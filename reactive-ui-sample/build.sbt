import Keys._
import android.Keys._

android.Plugin.androidBuild

name := "reactive-ui-sample"
organization := "com.geteit"
version := "0.0.1"

scalaVersion := "2.11.7"

platformTarget := "android-23"

libraryDependencies ++= Seq (
  "com.android.support" % "support-v4" % "23.1.0",
  "com.android.support" % "appcompat-v7" % "23.1.0",
  "com.geteit" %% "geteit-utils" % "0.3",
  "com.geteit" %% "geteit-app" % "0.1"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)

proguardOptions ++= Seq("-ignorewarnings")
proguardCache := Nil // this makes build slower, but more stable









// Test setup
libraryDependencies ++= Seq(
  "com.geteit" %% "robotest" % "0.12" % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)

fork in Test := true













// Build faster (a bit)
proguardScala := true
dexInputs in Android := {
  val di = dexInputs.value
  if (proguardScala.value) di
  else {
    (di._1, di._2 filterNot { f =>
      f.getName.startsWith("scala-")
//      f.getName.startsWith("scala-") || f.getName.startsWith("com.android.support")
    })
  }
}












// unnecessary steps
typedResources := false
retrolambdaEnabled := false

// don't include jni libs in apk file
collectJni := Nil

// don't include assets - we don't use them
collectResources := {
  val (assets, res) = (collectResources in Android).value
  (assets ** "*").get.foreach(_.delete())
  (assets, res)
}