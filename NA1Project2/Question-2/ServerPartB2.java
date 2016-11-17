
import java.io.*; 
import java.net.*; 

class ServerPartB2 { 


	public static void main(String argv[]) throws Exception 
	{ 
		int flag=0;
		BufferedReader bin = 
				new BufferedReader(new InputStreamReader(System.in)); 
		String clientInput=""; 
		String response=""; 
		System.out.println("Server Started Running");
		System.out.println("************************************");
		
		while(true){
			String clientSentence;
			ServerSocket socketConn = new ServerSocket(1234);
			while(true&flag==0) { 
				Socket connSocket = socketConn.accept();
				BufferedReader binClient = 
						new BufferedReader(new
								InputStreamReader(connSocket.getInputStream()));
				DataOutputStream  clientOutMsg = 
						new DataOutputStream(connSocket.getOutputStream()); 

				clientInput = binClient.readLine(); 
				System.out.println("Message Received From Client : "+clientInput);
				
				if(clientInput.equals("/exit")){
					clientSentence="/exit";
					response = clientSentence+ '\n'; 
					System.out.println("Waiting for client");
					clientOutMsg.writeBytes(response);
					clientOutMsg.close();
					connSocket.close(); 
					socketConn.close();
					flag=1;
				}
				else{
					clientOutMsg.close();
				}
			} 
			flag=0;
		} 
	}
} 





