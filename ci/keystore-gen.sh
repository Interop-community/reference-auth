#!/usr/bin/env bash
keytool -genkeypair -noprompt \
 -alias interopio \
  -keyalg RSA -keysize 2048 -storetype PKCS12 \
  -validity 365 \
 -dname "CN=interop.community, OU=logicahealth, O=logicahealth, L=Salt Lake City, S=UT, C=US" \
 -keystore hspconsortium.p12 \
 -storepass changeit \
 -keypass changeit \
 -v
