echo '============================'
echo 'Skapar server side nyckelpar'
echo '============================'

#skapa ett nyckelpar
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias bob -dname "CN=Bob" -storepass password -keypass doctorbob1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias alice -dname "CN=Alice" -storepass password -keypass doctoralice1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias klas -dname "CN=Klas" -storepass password -keypass nurseklas1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias urban -dname "CN=Urban" -storepass password -keypass nurseurban1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias bengt -dname "CN=Bengt" -storepass password -keypass gabengt1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias anna -dname "CN=Anna" -storepass password -keypass patientanna1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias fredrik -dname "CN=Fredrik" -storepass password -keypass patientfredrik1
keytool -genkeypair -keyalg RSA -keystore serverkeystore -alias johan -dname "CN=Johan" -storepass password -keypass patientjohan1

#skapa en CSR för keystore
keytool -certreq -keystore serverkeystore -file bob.csr -alias bob -storepass password
keytool -certreq -keystore serverkeystore -file alice.csr -alias alice -storepass password
keytool -certreq -keystore serverkeystore -file klas.csr -alias klas -storepass password
keytool -certreq -keystore serverkeystore -file urban.csr -alias urban -storepass password
keytool -certreq -keystore serverkeystore -file bengt.csr -alias bengt -storepass password
keytool -certreq -keystore serverkeystore -file anna.csr -alias anna -storepass password
keytool -certreq -keystore serverkeystore -file fredrik.csr -alias fredrik -storepass password
keytool -certreq -keystore serverkeystore -file johan.csr -alias johan -storepass password

echo '=============================='
echo 'Signerar server side nyckelpar'
echo '   Vad vänlig ange lösenord   '
echo '=============================='

#signera keystore med CA
openssl x509 -req -in bob.csr -CA cacert.pem -CAkey cakey.pem  -out bob.crt -CAcreateserial
openssl x509 -req -in alice.csr -CA cacert.pem -CAkey cakey.pem  -out alice.crt -CAcreateserial
openssl x509 -req -in klas.csr -CA cacert.pem -CAkey cakey.pem  -out klas.crt -CAcreateserial
openssl x509 -req -in urban.csr -CA cacert.pem -CAkey cakey.pem  -out urban.crt -CAcreateserial
openssl x509 -req -in bengt.csr -CA cacert.pem -CAkey cakey.pem  -out bengt.crt -CAcreateserial
openssl x509 -req -in anna.csr -CA cacert.pem -CAkey cakey.pem  -out anna.crt -CAcreateserial
openssl x509 -req -in fredrik.csr -CA cacert.pem -CAkey cakey.pem  -out fredrik.crt -CAcreateserial
openssl x509 -req -in johan.csr -CA cacert.pem -CAkey cakey.pem  -out johan.crt -CAcreateserial

keytool -importcert -file cacert.pem -alias ca -keystore serverkeystore -storepass password

#importera sitt eget cert
keytool -importcert -file bob.crt -alias bob -keystore serverkeystore -storepass password
keytool -importcert -file alice.crt -alias alice -keystore serverkeystore -storepass password
keytool -importcert -file klas.crt -alias klas -keystore serverkeystore -storepass password
keytool -importcert -file urban.crt -alias urban -keystore serverkeystore -storepass password
keytool -importcert -file bengt.crt -alias bengt -keystore serverkeystore -storepass password
keytool -importcert -file anna.crt -alias anna -keystore serverkeystore -storepass password
keytool -importcert -file fredrik.crt -alias fredrik -keystore serverkeystore -storepass password
keytool -importcert -file johan.crt -alias johan -keystore serverkeystore -storepass password

#check
keytool -list -v -keystore serverkeystore -storepass password 
