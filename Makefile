
.PHONY: mvn-run
mvn-run:
	@mvn package -Dmaven.test.skip=true && java -jar target/school.jar

docker-postgres: docker-postgres-stop docker-postgres-rm docker-postgres-up

.PHONY: docker-postgres-up
docker-postgres-up:
	@docker run --rm --name rsp-help-school-postgres -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=rsp_help_school -v rsp-help-school-postgres-data:/var/lib/postgresql/data -d postgres:alpine

.PHONY: docker-postgres-stop
docker-postgres-stop:
	@docker stop rsp-help-school-postgres | true

.PHONY: docker-postgres-rm
docker-postgres-rm:
	@docker volume rm rsp-help-school-postgres-data | true
