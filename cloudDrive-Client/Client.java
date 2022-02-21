import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import java.io.File;

public class Client {
   
	public static void main(String[] args) throws Exception {
		String ourCommand = args[0];
		String fileName = args[1];

        int PORT = 880;
		try (Socket connectionToServer = new Socket("localhost", PORT)) {

			// Readme : I/O
			InputStream in = connectionToServer.getInputStream();
			OutputStream out = connectionToServer.getOutputStream();

			BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
			BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));
			DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);

           
            if (ourCommand.equals("d")) {
				String header = "download " + fileName + "\n";
				headerWriter.write(header, 0, header.length());
				headerWriter.flush();

				header = headerReader.readLine();

				if (header.equals("NOT FOUND")) {
					System.out.println("The file that you want to download is not in your cloud... Try again");
				} else {
					StringTokenizer strk = new StringTokenizer(header, " ");

					String status = strk.nextToken();

					if (status.equals("OK")) {

						String temp = strk.nextToken();

						int size = Integer.parseInt(temp);

						byte[] space = new byte[size];

						dataIn.readFully(space);

						try (FileOutputStream fileOut = new FileOutputStream("ClientShare/" + fileName)) {
							fileOut.write(space, 0, size);
                           
						}

					} else {
						System.out.println("Try to connect to the right server");
					}

				}

			}else if (ourCommand.equals("u")) {
				try {
					FileInputStream fileIn = new FileInputStream("ClientShare/" + fileName);
					int fileSize = fileIn.available();
	
					String header = "upload " + fileName + " " + fileSize + "\n";
					headerWriter.write(header, 0, header.length());
					headerWriter.flush();
	
					byte[] bytes = new byte[fileSize];
					fileIn.read(bytes);
	
					fileIn.close();
	
					dataOut.write(bytes, 0, fileSize);
                    System.out.println("the file: "+fileName+" is uploaded successfully");
				} catch (Exception e) {
					System.err.println("The file " + fileName + " not found.");
				}
			}else if(ourCommand.equals("del"))
            {
               String header = "delete " + fileName + "\n";
				headerWriter.write(header, 0, header.length());
				headerWriter.flush();

				header = headerReader.readLine();

				if (header.equals("NOT FOUND")) {
					System.out.println("The file: "+fileName+" is not in the cloud to delete... Try again");
				} else {
					StringTokenizer strToken = new StringTokenizer(header, " ");
					String ourStatus = strToken.nextToken();

					if (ourStatus.equals("toBin")) {

						String bin = strToken.nextToken();

						int size = Integer.parseInt(bin);

						byte[] space = new byte[size];

						dataIn.readFully(space);

						FileOutputStream fileOut = new FileOutputStream("Bin/" + fileName);
						fileOut.write(space, 0, size);
                        File f = new File("ServerShare/"+fileName);
                        f.delete();
                        System.out.println("The file: "+fileName+" deleted successfully" );
                        

					} else {
						System.out.println("You are not connected to the server ... Try again");
					}

				}
            } else if(ourCommand.equals("b")){

                String header = "backup"+" "+"\n";
				headerWriter.write(header, 0, header.length());
				headerWriter.flush();
				//send nummber of files in ClienShare to the cloud:
				File directory = new File("ClientShare/");
				int filesNumber = directory.list().length;
				String filesNumberS = Integer.toString(filesNumber);
				headerWriter.write(filesNumberS, 0, filesNumberS.length());
				headerWriter.flush();

				//make a loop to send each file in the ClientShare to the server:
				File contents[] = directory.listFiles();

				for(int i = 0;i<contents.length;i++)
				{
					FileInputStream fileIn = new FileInputStream("ClientShare/" + contents[i].getName());
					int fileSize = fileIn.available();
	
					header = "backup " + contents[i].getName() + " " + fileSize + "\n";
					headerWriter.write(header, 0, header.length());
					headerWriter.flush();
	
					byte[] bytes = new byte[fileSize];
					fileIn.read(bytes);
	
					fileIn.close();
	
					dataOut.write(bytes, 0, fileSize);
				}

            }else if(ourCommand.equals("r"))
			{
				
				String header = "rename"+fileName+"\n";
				headerWriter.write(header, 0, header.length());
				headerWriter.flush();
				Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    			System.out.println("Enter your new file name: ");
    			String newFileName = myObj.nextLine();
				PrintWriter clientPr = new PrintWriter(client.getOutputStream());
				clientPr.println(newFileName);
				clientPr.flush();

			}
			else
			{
				System.err.println("Argument is not known . Enter 'd' or 'u' or 'b' to download or upload or backup of files.");
			}
		}
	}
}