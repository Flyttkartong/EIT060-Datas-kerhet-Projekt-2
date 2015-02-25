echo '============================'
echo 'Skapar Client side nyckelpar'
echo '============================'

#skapa nyckelpar och lägg dem i rätt keystore
keytool -genkeypair -keyalg RSA -keystore bobkeystore -alias bob -dname "CN=Bob" -storepass doctorbob1 -keypass doctorbob1
keytool -genkeypair -keyalg RSA -keystore alicekeystore -alias alice -dname "CN=Alice" -storepass doctoralice1 -keypass doctoralice1
keytool -genkeypair -keyalg RSA -keystore klaskeystore -alias klas -dname "CN=Klas" -storepass nurseklas1 -keypass nurseklas1
keytool -genkeypair -keyalg RSA -keystore urbankeystore -alias urban -dname "CN=Urban" -storepass nurseurban1 -keypass nurseurban1
keytool -genkeypair -keyalg RSA -keystore bengtkeystore -alias bengt -dname "CN=Bengt" -storepass gabengt1 -keypass gabengt1
keytool -genkeypair -keyalg RSA -keystore annakeystore -alias anna -dname "CN=Anna" -storepass patientanna1 -keypass patientanna1
keytool -genkeypair -keyalg RSA -keystore fredrikkeystore -alias fredrik -dname "CN=Fredrik" -storepass patientfredrik1 -keypass patientfredrik1
keytool -genkeypair -keyalg RSA -keystore johankeystore -alias johan -dname "CN=Johan" -storepass patientjohan1 -keypass patientjohan1

#skapa en CSR för alla keystores
keytool -certreq -keystore bobkeystore -file bob.csr -alias bob -storepass doctorbob1
keytool -certreq -keystore alicekeystore -file alice.csr -alias alice -storepass doctoralice1
keytool -certreq -keystore klaskeystore -file klas.csr -alias klas -storepass nurseklas1
keytool -certreq -keystore urbankeystore -file urban.csr -alias urban -storepass nurseurban1
keytool -certreq -keystore bengtkeystore -file bengt.csr -alias bengt -storepass gabengt1
keytool -certreq -keystore annakeystore -file anna.csr -alias anna -storepass patientanna1
keytool -certreq -keystore fredrikkeystore -file fredrik.csr -alias fredrik -storepass patientfredrik1
keytool -certreq -keystore johankeystore -file johan.csr -alias johan -storepass patientjohan1

echo '=============================='
echo 'Signerar Client side nyckelpar'
echo '   Vad vänlig ange lösenord   '
echo '=============================='

#signera alla keystores med CA
openssl x509 -req -in bob.csr -CA cacert.pem -CAkey cakey.pem  -out bob.crt -CAcreateserial
openssl x509 -req -in alice.csr -CA cacert.pem -CAkey cakey.pem  -out alice.crt -CAcreateserial
openssl x509 -req -in klas.csr -CA cacert.pem -CAkey cakey.pem  -out klas.crt -CAcreateserial
openssl x509 -req -in urban.csr -CA cacert.pem -CAkey cakey.pem  -out urban.crt -CAcreateserial
openssl x509 -req -in bengt.csr -CA cacert.pem -CAkey cakey.pem  -out bengt.crt -CAcreateserial
openssl x509 -req -in anna.csr -CA cacert.pem -CAkey cakey.pem  -out anna.crt -CAcreateserial
openssl x509 -req -in fredrik.csr -CA cacert.pem -CAkey cakey.pem  -out fredrik.crt -CAcreateserial
openssl x509 -req -in johan.csr -CA cacert.pem -CAkey cakey.pem  -out johan.crt -CAcreateserial

keytool -importcert -file cacert.pem -alias ca -keystore bobkeystore -storepass doctorbob1
keytool -importcert -file cacert.pem -alias ca -keystore alicekeystore -storepass doctoralice1
keytool -importcert -file cacert.pem -alias ca -keystore klaskeystore -storepass nurseklas1
keytool -importcert -file cacert.pem -alias ca -keystore urbankeystore -storepass nurseurban1
keytool -importcert -file cacert.pem -alias ca -keystore bengtkeystore -storepass gabengt1
keytool -importcert -file cacert.pem -alias ca -keystore annakeystore -storepass patientanna1
keytool -importcert -file cacert.pem -alias ca -keystore fredrikkeystore -storepass patientfredrik1
keytool -importcert -file cacert.pem -alias ca -keystore johankeystore -storepass patientjohan1

#importera alla cert
keytool -importcert -file bob.crt -alias bob -keystore bobkeystore -storepass doctorbob1
keytool -importcert -file alice.crt -alias alice -keystore alicekeystore -storepass doctoralice1
keytool -importcert -file klas.crt -alias klas -keystore klaskeystore -storepass nurseklas1
keytool -importcert -file urban.crt -alias urban -keystore urbankeystore -storepass nurseurban1
keytool -importcert -file bengt.crt -alias bengt -keystore bengtkeystore -storepass gabengt1
keytool -importcert -file anna.crt -alias anna -keystore annakeystore -storepass patientanna1
keytool -importcert -file fredrik.crt -alias fredrik -keystore fredrikkeystore -storepass patientfredrik1
keytool -importcert -file johan.crt -alias johan -keystore johankeystore -storepass patientjohan1
