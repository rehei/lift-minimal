name := "Lift Minimal"

// set version to environment variable $tag 
version := sys.props.getOrElse("tag", default = "0.0.0")

organization := "com.github.rehei"

scalaVersion := "2.11.7"

resolvers += "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "staging" at "https://oss.sonatype.org/content/repositories/staging"
resolvers += "releases" at "https://oss.sonatype.org/content/repositories/releases"
resolvers += Resolver.bintrayRepo("rehei", "maven")

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

enablePlugins(TomcatPlugin)
containerPort := 10080

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "com.github.rehei.lift" %% "lift-webjars" % "2.6.2-01",
    "com.github.rehei.lift" %% "lift-hibernate" % "2.6.2-01",
    "org.webjars.bower" % "jquery" % "2.1.4",

    "net.liftweb" %% "lift-webkit" % liftVersion,
    "ch.qos.logback" % "logback-classic" % "1.0.6",
    "ch.qos.logback" % "logback-access" % "1.0.6",
     
    "com.novocode" % "junit-interface" % "0.11" % "test", 
    "com.github.jsimone" % "webapp-runner" % "7.0.57.2" % "test"
  )
}

EclipseKeys.withSource := true

