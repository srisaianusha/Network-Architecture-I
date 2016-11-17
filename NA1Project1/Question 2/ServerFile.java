import java.net.*;
import java.io.*;
public class ServerFile {

	public static void main (String [] args ) throws IOException {
		int filesize=2000000; 
		int bytesRead=0;
		int currentTot = 0;
		String line = null;
		ServerSocket serverSocket = new ServerSocket(12345);
		Socket socket = serverSocket.accept();
		System.out.println("Accepted connection from the Client : " + socket);

		byte [] bytearray  = new byte [filesize];
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream("NewFile.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesRead = is.read(bytearray,0,bytearray.length);
		currentTot = bytesRead;

		/*do {
					bytesRead =
							is.read(bytearray, currentTot, (bytearray.length-currentTot));
					if(bytesRead >= 0) currentTot += bytesRead;
				} while(bytesRead > -1);*/

		bos.write(bytearray, 0 , currentTot);
		bos.flush();
		bos.close();
		System.out.println("***** File is successfully Received  from client *****");

		BufferedReader br = new BufferedReader(new FileReader("NewFile.txt")); 
		
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		FileWriter fw = new FileWriter("NewFile.txt" ,true); 
		fw.append("\n\n This is an added line from the server");
		fw.close();

		File transferFile = new File ("NewFile.txt");
		//append(transferFile, "\n\n This is an	added line from a server");
		bytearray  = new byte [(int)transferFile.length()];
		FileInputStream fin = new FileInputStream(transferFile);
		BufferedInputStream bin = new BufferedInputStream(fin);
		bin.read(bytearray,0,bytearray.length);
		OutputStream os = socket.getOutputStream();
		System.out.println("Sending Files...");
		os.write(bytearray,0,bytearray.length);
		os.flush();
		socket.close();
		System.out.println("File transfer complete");
	}
	public static void append(File aFile, String content) {
		FileWriter fw;
		try {
			fw = new FileWriter(aFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			fw.append(content);
			fw.close();

			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
