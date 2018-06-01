TIME_STAMP=`date +%Y%m%d%H%M`
CODE_HOME=/usr/local/book/client
PROJECTNAME=book_client
USER=admin
PASS=admin

cd $CODE_HOME  
pid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `   
if [ $pid ]; then  
    echo "App  is  running  and pid=$pid"  
    curl -X POST -u $USER:PASS http://127.0.0.1:8053/stopServer
fi  
nohup java -Xms256m -Xmx512m -XX:PermSize=256M -XX:MaxPermSize=512M -jar $CODE_HOME/$PROJECTNAME.jar > /dev/null 2>&1 &