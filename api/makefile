start :
	./mvnw spring-boot\:run

debug :
	./mvnw spring-boot\:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp\:transport=dt_socket,server=y,suspend=y,address=5005"

db :
	brew services start mongodb-community@5.0

dev :
	db && debug

stop :
	stop-db

stop-db :
	brew services stop mongodb-community@5.0

clean :
	rm -rf target