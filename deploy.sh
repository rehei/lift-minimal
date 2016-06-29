URL=https://github.com/rehei/tomcat7-rehei-deploy/releases/download/0.7.0/deploy-tomcat7.sh
FILE=deploy-tomcat7.sh
curl -L -J $URL > $FILE
chmod +x $FILE

export CONTEXT=ROOT
export PORT=28080
./$FILE
