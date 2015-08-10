./sbt package


TAG=${TRAVIS_TAG:=0.0.0}
DIR="$(cd "$(dirname "$0")" && pwd)"
cd $DIR/target/scala-2.11/
APP=$(ls *_2.11-${TAG}.war | sed 's/\(.*\)_2.11-.*\.war/\1/')

rm -rf $DIR/deployment
mkdir $DIR/deployment
mkdir $DIR/deployment/tmp
cd $DIR/deployment/tmp
wget --no-check-certificate https://github.com/rehei/tomcat7-rehei/releases/download/7.0.63/apache-tomcat-7.0.63-windows-x64-rehei.zip
unzip apache-tomcat-7.0.63-windows-x64-rehei.zip -d $DIR/deployment/tomcat7-${APP}
cp $DIR/target/scala-2.11/${APP}_2.11-${TAG}.war $DIR/deployment/tomcat7-${APP}/webapps/ROOT.war
rm -rf $DIR/deployment/tmp
cd $DIR/deployment
zip -r $DIR/deployment/tomcat7-${APP}.zip tomcat7-${APP}



