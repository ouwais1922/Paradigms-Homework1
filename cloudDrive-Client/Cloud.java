import java.net.*;
import java.io.*;
import java.util.Scanner; 
import java.util.StringTokenizer;

public class Cloud {

	private static void getListOfFilesCloud(){
		
		  File directoryPath = new File("ServerShare/");
            //List of all files and directories
            String content[] = directoryPath.list();
            System.out.println("All the content in the ServerShare:");
            for(int i=0; i<content.length; i++) {
				
                    System.out.println(content[i]);
            }
	}

	public static void main(String[] args) throws Exception {

		getListOfFilesCloud();
		try (ServerSocket ss = new ServerSocket(880)) {
			while (true) {
				System.out.println("Waiting for a connection...");
				Socket connectionFromClient = ss.accept();
				System.out.println(
						"Server got a connection from the client: " + connectionFromClient.getPort());
				
				try {
					InputStream in = connectionFromClient.getInputStream();
					OutputStream out = connectionFromClient.getOutputStream();

					String errorMessage = "NOT FOUND\n";

					BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
					BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));

					DataInputStream dataIn = new DataInputStream(in);
					DataOutputStream dataOut = new DataOutputStream(out);

					String header = headerReader.readLine();
					StringTokenizer strk = new StringTokenizer(header, " ");

					String cloudCommand = strk.nextToken();

					
					if (cloudCommand.equals("download")) {
						try {
							String fileName = strk.nextToken();
							FileInputStream fileIn = new FileInputStream("ServerShare/" + fileName);
							int fileSize = fileIn.available();
							header = "OK " + fileSize + "\n";

							headerWriter.write(header, 0, header.length());
							headerWriter.flush();

							byte[] bytes = new byte[fileSize];
							fileIn.read(bytes);

							fileIn.close();

							dataOut.write(bytes, 0, fileSize);
							System.out.println("the file: "+fileName+" is downloaded successfully");

						} catch (Exception ex) {
							headerWriter.write(errorMessage, 0, errorMessage.length());
							headerWriter.flush();

						} finally {
							connectionFromClient.close();
						}
					}  else if (cloudCommand.equals("upload")) {
							try {
							String fileName = strk.nextToken();
							String sizefile = strk.nextToken();
							int fileSize = Integer.parseInt(sizefile);
	
							byte[] space = new byte[fileSize];
	
							dataIn.readFully(space);
	
							FileOutputStream fileOut = new FileOutputStream("ServerShare/" + fileName);
							fileOut.write(space, 0, fileSize);
							fileOut.close();
							System.out.println("The content in the Cloud after uploading:");
							getListOfFilesCloud();
							
						} catch (Exception ex) {
							headerWriter.write(errorMessage, 0, errorMessage.length());
							headerWriter.flush();
	
						} finally {
							connectionFromClient.close();
						}
						

					}else if(cloudCommand.equals("delete"))
					{
						try {
							String fileName = strk.nextToken();
							FileInputStream fileIn = new FileInputStream("ServerShare/" + fileName);
							int fileSize = fileIn.available();
							header = "toBin " + fileSize + "\n";

							headerWriter.write(header, 0, header.length());
							headerWriter.flush();

							byte[] bytes = new byte[fileSize];
							fileIn.read(bytes);

							fileIn.close();

							dataOut.write(bytes, 0, fileSize);
							System.out.println("the file: "+fileName+" is deleted successfully");
						} catch (Exception ex) {
							headerWriter.write(errorMessage, 0, errorMessage.length());
							headerWriter.flush();

						} finally {
							connectionFromClient.close();
						}
					}else if (cloudCommand.equals("backup"))
					{
						//get the number of files from the client:

						header =  headerReader.readLine();
						strk = new StringTokenizer(header, " ");
						int numberOfFiles = Integer.parseInt(strk.nextToken());

						//recieving command, filename,fileSiize of each file:
						for(int i=0;i<numberOfFiles;i++)
						{
							header = headerReader.readLine();
							//strk = new StringTokenizer(header, " ");
							String command = strk.nextToken();
							String nameFile = strk.nextToken();
							String size = strk.nextToken();
							int acutalSize =  Integer.parseInt(size);

							byte[] space = new byte[acutalSize];
							dataIn.readFully(space);
							FileOutputStream fileOut = new FileOutputStream("ServerShare"+ nameFile);
							fileOut.write(space, 0, acutalSize);
							fileOut.close();

						}
					}else if(cloudCommand.equals("rename"))
					{
						String fileName = strk.nextToken();
						
						InputStreamReader  input = new InputStreamReader(client.getInputStream());
						BufferedReader buffer = new BufferedReader(input);
						String newFileName = buffer.readLine();

						File file = new File("ServerShare/"+fileName);
						File rename = new File("ServerShare/"+newFileName);
						boolean yesNo = file.renameTo(rename);

						 if (file.renameTo(rename)) {
           					 System.out.println("File Successfully Rename");
        					}
							 else {
            					System.out.println("Operation Failed");
       						 }
					}

					 else {

						System.out.println("Connection got from an incompatible client");

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}