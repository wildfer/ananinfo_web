#! /bin/bash
#echo $JAVA_HOME
BATCH_HOME=..
LIB_PATH=$BATCH_HOME/lib
export CLASSPATH=$CLASSPATH:.
CLASSPATH=$CLASSPATH:MiniShopCmd.jar
for tradePortalJar in $LIB_PATH/*.jar
do  
   CLASSPATH=$CLASSPATH:$tradePortalJar
done
#echo $CLASSPATH
#
nohup java -server -cp $CLASSPATH com.MiniShopCmd.service.Main &


