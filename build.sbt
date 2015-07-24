name := "Lift v2.6 Template Application with Bootstrap v3"

// set version to environment variable $tag 
version := sys.props.getOrElse("tag", default = "0.0.0")

organization := "com.github.rehei"

scalaVersion := "2.11.7"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                  "staging"       at "https://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
                 )


unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "net.liftweb" 		%% "lift-webkit" 	% liftVersion,
    "org.eclipse.jetty" % "jetty-runner" 	% "9.3.0.v20150612",
    "ch.qos.logback" 	% "logback-classic" % "1.0.6",
    "org.webjars.bower" % "d3" % "3.5.6",
    "org.webjars" % "webjars-locator" % "0.27"
  )
}

EclipseKeys.withSource := true

