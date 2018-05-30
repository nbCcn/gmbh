TIME_STAMP=`date +%Y%m%d%H%M`
PROJECTNAME=book_client
USER=admin
PASS=admin

pid=`ps -ef |grep $PROJECTNAME |grep -v "grep" |awk '{print $2}' `
if [ $pid ]; then
    echo "App  is  running  and pid=$pid"
    curl -X POST -u $USER:PASS http://127.0.0.1:8053/stopServer
    if [[ $? -eq 0 ]];then
       echo "sucess to stop $PROJECTNAME "
    else
       echo "fail to stop $PROJECTNAME "
     fi
fi