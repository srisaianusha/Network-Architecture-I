import java.net.*;
import java.io.*;
class ClientFile {

	public static void main (String [] args ) throws IOException {
		int filesize=2000000; 
		int bytesRead;
		String line = null;
		int currentTot = 0;
		//Socket socket = new Socket("10.1.1.2",12345);
		Socket socket = new Socket("Server.sgr43.ch-geni-net.instageni.umkc.edu",12345);
		File filetobesent = new File("file.txt");
		byte [] bytearray  = new byte [(int)filetobesent.length()];
		FileInputStream fin = new FileInputStream(filetobesent);
		BufferedInputStream bin = new BufferedInputStream(fin);
		bin.read(bytearray,0,bytearray.length);
		OutputStream os = socket.getOutputStream();
		System.out.println("***** Sending Files from client side *****");
		os.write(bytearray,0,bytearray.length);
		os.flush();
		System.out.println("***** File is successfully sent to Server *****");
		bytearray  = new byte [filesize];
		bytearray  = new byte [filesize];

		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream("file.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesRead = is.read(bytearray,0,bytearray.length);
		currentTot = bytesRead;

		do {
			bytesRead =
					is.read(bytearray, currentTot, (bytearray.length-currentTot));
			if(bytesRead >= 0) currentTot += bytesRead;
		} while(bytesRead > -1);

		bos.write(bytearray, 0 , currentTot);
		bos.flush();
		bos.close();
		socket.close();
		BufferedReader br = new BufferedReader(new FileReader("file.txt")); 

		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		System.out.println("***** File is successfully received from Server *****");
	}
}
