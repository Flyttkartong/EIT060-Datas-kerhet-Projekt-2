package secure;

import java.io.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String host = null;
        int port = 8001;
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }
//        if (args.length < 2) {
//            System.out.println("USAGE: java client host port");
//            System.exit(-1);
//        }
        try { /* get input parameters */
//            host = args[0];
//            port = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }

        try { /* set up a key manager for client authentication */
            SSLSocketFactory factory = null;
            try {
            	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            	System.out.print("Username: ");
            	String user = br.readLine();
            	System.out.print("Password: ");
            	String password = br.readLine();
            	
                char[] passwordArray = password.toCharArray();
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                SSLContext ctx = SSLContext.getInstance("TLS");
                ks.load(new FileInputStream("./keystores/" + user.toLowerCase() + "keystore"), passwordArray);  // keystore password (storepass)
				ts.load(new FileInputStream("./keystores/clienttruststore"), "password".toCharArray()); // truststore password (storepass);
				kmf.init(ks, passwordArray); // user password (keypass)
				tmf.init(ts); // keystore can be used as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                factory = ctx.getSocketFactory();
            } catch (Exception e) {
            	e.printStackTrace();
                throw new IOException(e.getMessage());
            }
            SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
            System.out.println("\nsocket before handshake:\n" + socket + "\n");

            /*
             * send http request
             *
             * See SSLSocketClient.java for more information about why
             * there is a forced handshake here when using PrintWriters.
             */
            socket.startHandshake();

            SSLSession session = socket.getSession();
            X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
            String subject = cert.getSubjectDN().getName();
            System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
            System.out.println("socket after handshake:\n" + socket + "\n");
            System.out.println("secure connection established\n\n");

            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
			String response[];
			for (;;) {
				System.out.print(">");
				msg = read.readLine();
				if (msg.equalsIgnoreCase("quit")) {
					break;
				}
				out.println(msg);
				out.flush();
				response = in.readLine().split("-"); // "-" defined as newline in responses from server
				for(String s: response) {
					System.out.println(s);
				}
			}
			in.close();
			out.close();
			read.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
