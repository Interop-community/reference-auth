#!/usr/bin/env bash

jq ".family = \"$PROJECT_FULL_NAME\"" ${TEMPLATE_FILE} > tmp.json && mv tmp.json ${TEMPLATE_FILE}
jq ".containerDefinitions[0].name = \"$PROJECT_FULL_NAME\"" ${TEMPLATE_FILE} > tmp.json && mv tmp.json ${TEMPLATE_FILE}
jq ".containerDefinitions[0].image = \"$DOCKER_IMAGE_COORDINATES\"" ${TEMPLATE_FILE} > tmp.json && mv tmp.json ${TEMPLATE_FILE}
jq ".containerDefinitions[0].logConfiguration.options.\"awslogs-group\" = \"/ecs/$PROJECT_FULL_NAME\"" ${TEMPLATE_FILE} > tmp.json && mv tmp.json ${TEMPLATE_FILE}
jq "(.containerDefinitions[0].environment[] | select(.name == \"JASYPT_ENCRYPTOR_PASSWORD\") | .value) |= \""${ENC_PW_TEST}"\"" ${TEMPLATE_FILE} > tmp.json && mv tmp.json ${TEMPLATE_FILE}
jq "(.containerDefinitions[0].environment[] | select(.name == \"SPRING_PROFILES_ACTIVE\") | .value) |= \"${SPRING_PROFILES_ACTIVE}\"" ${TEMPLATE_FILE} > tmp.json && mv tmp.json ${TEMPLATE_FILE}

echo "outside: ${ENC_PW_TEST}"

cat ${TEMPLATE_FILE}