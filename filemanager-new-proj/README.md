# File Manager

##Properties
#Spring
	spring.application.name=file_manager
	server.port= 8764

#mongodb
	spring.data.mongodb.host=localhost
	spring.data.mongodb.port=27017
	spring.data.mongodb.database=hdfs_file_manager_db
	spring.data.mongodb.username=root
	spring.data.mongodb.password=Me@Won6O_DB
	spring.data.mongodb.authentication-database = admin

#Hadoop HDFS
	hadoop.hdfs.uri  = hdfs://172.20.0.4:9000
	hadoop.hdfs.user = root 


#logging
	logging.level.org.springframework.data=debug
	logging.level.= ERROR,INFO
	logging.file=hdfs_file_manager.log

