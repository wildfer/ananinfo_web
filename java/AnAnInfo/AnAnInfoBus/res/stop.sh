ps -ef|grep com.MiniShop.service.Main|grep -v grep|awk -F ' ' '{print $2}'|xargs kill -9
