import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class SSNP
{
	public static void main(String argv[]) throws Throwable{
		if (argv.length < 3)
		{
			System.out.println("Welcome to Super Simple Net Piercing!");
			System.out.println("\nUsage Example:");
			System.out.println("java SSNP FACE_HOST FACE_PORT BACK_HOST BACK_PORT\n");
			System.out.println("This super tool simply maps a port on publick subnetwork [FACE]");
			System.out.println("to private subnetwork [BACK], of the server where it runs.");
			System.out.println("When client connects to [FACE], SSNP opens connection to");
			System.out.println("whatever sits on [BACK] and transmits data between them.");
			System.out.println("\n...So if you wanna connect to device that hides behind Prod Server");
			System.out.println("from your desk - this tool will pierce a hole for you ^_^");
			return;
		}
		String faceHost = argv[0];
		int facePort = Integer.parseInt(argv[1]);
		int numberOfConnections = 1;
		String backHost = argv[2];
		int backPort = Integer.parseInt(argv[3]);

		System.out.println("[FACE] : " + faceHost + ":" + facePort);
		System.out.println("[BACK] : " + backHost + ":" + backPort);
		
		SSNP pm = new SSNP(faceHost, facePort, numberOfConnections, backHost, backPort);
		pm.run();

	}
	
	public String faceHost;
	public int facePort;
	public int numberOfConnections;
	public String backHost;
	public int backPort;
	
	public SSNP(String faceHost, int facePort, int numberOfConnections, String backHost, int backPort){
		this.faceHost = faceHost;
		this.facePort = facePort;
		this.numberOfConnections = numberOfConnections;
		this.backHost = backHost;
		this.backPort = backPort;
	}
	
    public void run(){
        try{
			InetAddress faceAddr = InetAddress.getByName(faceHost);
			ServerSocket faceServerSocket = new ServerSocket(facePort, numberOfConnections, faceAddr);
			int bytesFromFace = 0;
    		while(true)
    		{
    			Socket faceSocket = null;
    			BackRunnable backRunnable = null;
    			Thread backThread = null;
    			try{
    				System.out.println("\n[FACE] Waiting for connection on "  + faceHost + ":" + facePort);
    				faceSocket = faceServerSocket.accept();
    				DataInputStream inFromFace = new DataInputStream(faceSocket.getInputStream());
    				DataOutputStream outToFace =  new DataOutputStream(faceSocket.getOutputStream());
    				
    				boolean isBackConnected = false;
    				int data = inFromFace.read();
    				while(data != -1){
    					if (!isBackConnected){ // on demand
    	    				backRunnable = new BackRunnable(outToFace);
    	    				backThread = new Thread(backRunnable, "Back Subnetwork Thread");
    	    				backThread.start();
    						isBackConnected = true;
    					}
    					bytesFromFace ++;
    					backRunnable.write(data);
    					data = inFromFace.read();
    					Thread.sleep(10);
    				}
    				
    			}catch(Throwable t){
    				System.out.println("[FACE].run " + t);
    				t.printStackTrace();
    			}
    			try{
    				if (backRunnable!=null)
    					backRunnable.stop();
    				if (backThread != null)
    					backThread.join(3000);
    				if(faceSocket != null)
    					faceSocket.close();
    				System.out.println("[FACE] Connection closed.");
    			}catch(Throwable t){
    				System.out.println("[FACE].run " + t);
    				t.printStackTrace();
    			}
    			System.out.println("[FACE] " + bytesFromFace + " bytes received.");
    			Thread.sleep(10);
    		}
        }catch(InterruptedException ie){
        	System.out.println("[FACE].run interrupted! " + ie);
		}catch(Throwable t){
			System.out.println("[FACE].run " + t);
			t.printStackTrace();
		}
    }
    
	public class BackRunnable implements Runnable{
		private volatile Boolean isStopped=false;
		private Socket backSocket;
		private DataOutputStream outToServer;
		private DataInputStream inFromServer;
		private DataOutputStream outToClient;
		
		public void stop(){isStopped=true;}
		
		public void write(int data) throws IOException {
			outToServer.write(data);
			outToServer.flush();
		}
		
		public BackRunnable(DataOutputStream outToClient) throws UnknownHostException, IOException, 																			InterruptedException
		{
			System.out.println("  [BACK] Connecting to " + backHost + ":" + backPort);
			this.outToClient = outToClient;
			backSocket = new Socket(backHost, backPort);
			outToServer = new DataOutputStream(backSocket.getOutputStream());
			inFromServer = new DataInputStream(backSocket.getInputStream());
		}
		
		public void run(){
			int bytesFromBack = 0;
            try{
				int replyData = inFromServer.read();
				while(replyData != -1){
					bytesFromBack++;
					outToClient.write(replyData);
					outToClient.flush();
					replyData = inFromServer.read();
					Thread.sleep(10);
					if(isStopped)
						break;
				}
            }
            catch(InterruptedException ie){
                    System.out.println("  [BACK] Child thread interrupted! " + ie);
			}catch(Throwable t){
				System.out.println("  [BACK] " + t);
				t.printStackTrace();
			}
    		try{
    			if(backSocket != null)
    				backSocket.close();
    			System.out.println("  [BACK] Connection closed.");
    		}catch(Throwable t){
    			System.out.println("  [BACK] " + t);
    			t.printStackTrace();
    		}
    		System.out.println("  [BACK] " + bytesFromBack + " bytes received.");
        }
	}
}