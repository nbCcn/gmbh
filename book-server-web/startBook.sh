TIME_STAMP=`date +%Y%m%d%H%M`
CODE_HOME=/usr/local/book/server
PROJECTNAME=book_server
cd $CODE_HOME  
pid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}'`   
if [ $pid ]; then  
    echo "App  is  running  and pid=$pid"  
else  
   nohup java -jar $CODE_HOME/$PROJECTNAME.jar > /dev/null 2>&1 &
fi 
