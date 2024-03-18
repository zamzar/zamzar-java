.PHONY: up clean sync generate build test bump publish

SPEC_REMOTE = https://raw.githubusercontent.com/zamzar/zamzar-spec/main/openapi/spec.yaml
SPEC = spec.yaml
GENERATOR_CMD = docker compose exec codegen \
				java -jar /opt/openapi-generator/modules/openapi-generator-cli/target/openapi-generator-cli.jar
GENERATE_ARGS = generate -i $(SPEC) -g java -c openapi-generator-config.yaml
MVN_CMD = docker compose exec maven mvn --batch-mode --no-transfer-progress

up:
	@docker compose up -d

clean:
	@while read -r generated; do rm -f "$$generated" || true; done < .openapi-generator/FILES
	@rm -rf target

sync:
	@curl -sSL $(SPEC_REMOTE) > $(SPEC)

generate: up
	@$(eval VERSION=$(shell $(MVN_CMD) help:evaluate -Dexpression=project.version -q -DforceStdout))
	@$(GENERATOR_CMD) $(GENERATE_ARGS) --additional-properties=artifactVersion=$(VERSION)

build: up
	@$(MVN_CMD) clean test-compile

test: build
	@$(MVN_CMD) test

bump: up sync
ifndef VERSION
	$(error VERSION is not set)
endif
	@$(MVN_CMD) versions:set -DnewVersion=$(VERSION) versions:commit
	@$(GENERATOR_CMD) $(GENERATE_ARGS) --additional-properties=artifactVersion=$(VERSION)

publish: up
ifndef MVN_CENTRAL_USERNAME
	$(error MVN_CENTRAL_USERNAME is not set)
endif
ifndef MVN_CENTRAL_PASSWORD
	$(error MVN_CENTRAL_PASSWORD is not set)
endif
ifndef GPG_PRIVATE_KEY
	$(error GPG_PRIVATE_KEY is not set)
endif
ifndef GPG_PASSPHRASE
	$(error GPG_PASSPHRASE is not set)
endif
	@docker compose exec maven mkdir -p /root/.m2
	@docker compose exec maven sh -c 'echo "<settings><servers><server><id>central</id><username>$(MVN_CENTRAL_USERNAME)</username><password>$(MVN_CENTRAL_PASSWORD)</password></server></servers></settings>" > /root/.m2/settings.xml'
	@docker compose exec maven sh -c 'echo -n "$(GPG_PRIVATE_KEY)" | base64 --decode > /root/api-sdks.asc'
	@docker compose exec maven gpg --batch --import --pinentry-mode loopback --passphrase "$(GPG_PASSPHRASE)" /root/api-sdks.asc
	@$(MVN_CMD) deploy -DskipTests -Dgpg.keyname=api-sdks -Dgpg.passphrase=$(GPG_PASSPHRASE)