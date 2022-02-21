# Homework 1 - Cloud Drive

# Cloud Design:

This file (Cloud-Design) will give general description about how the Cloud Drive allows the user to have his/her files automatically backed up on the cloud and some other feautres that already existed in Google Drive which is our first point of inspiration. Besides, it will provide us with the protocol between the server and client. I will put all refrences,links, and explanation (developing side) in the README file.

# 1- General descirption:

> Please Check the README file

-> Upload files to the cloud.
-> Download files from the cloud.
-> Backup all the files to the cloud.
-> Delete files from the cloud.
-> Rename of a file.

# Run the application:

1- Open two terminals in the root project, and use the following commands to run the project:

    1-1- Server side: javac Cloud.java
    1-2- Client side: javac Client.java

# 2- Cloud-Drive-Protocol:

Before the cloud get a connetion from a client, all the content in the cloud (ServerShare) will be listed.

Once a client opens connection with the server, the client _inform_ the server if he/she wants to _download_, _upload_, _delete_, or _backup_ using a header.

## Download:

If a client wants to download a file from the cloud, the header will be as following:

> **download[one space][file_name][one space][line_feed]**

Upon receiving this header, the server searches for the specified file.

If the file is not found, then the server shall reply with a header as the following:

> **NOT[one space]FOUND[Line Feed]**

If the file is found, then the server shall reply with a header as the following:

> **OK[one space][file size][Line Feed]**
> followed by the bytes of the file

Once the download done successfully a message : "the file: +fileName+ is downloaded successfully" will apear in cloud side.

### Upload:

If the client wants to upload a file, then the header will be as the following:

> **upload[one space][file name][one space][file size][Line Feed]**

After sending the header, the client shall send the bytes of the file.

Once the upload done successfully a message : "the file: "+fileName+" is uploaded successfully" will apear in client side.

### Delete:

please check: **README.md** / **##Delete**

If the client wants to delete a file from his/her cloud, then the header will be as the following:

> **delete[one space][file name][one space][line feed]**

upon recieving this header, the cloud searches for this specified file,Then this file will be uploaded to Bin folder and delete from the cloud.
Once the delete done successfully a message : "the file: "+fileName+" is deleted successfully" will apear in cloud side.

### Backup:

Before make a backup in the client side, we have to know first how many files that we have in our ClientShare and send this number of files to our cloud, so it can recieve file by file from the client side.

If the client wants to backup all the files from the ClientShare to the Cloud, then the header will be as the following:

> **backup[one space][linefeed]**
> after this command all the files in the clientshare will be in the cloud.

### Rename:

If a clien wants to rename a file in his/her cloud, then the header will be as following:

-> **rename[one space][file name][one space][line feed]**

after sending this header the client have to choose a new file name.
This new file name will send to the cloud(serverSide).

## Refrences:

> https://stackoverflow.com/questions/471342/java-socket-programming
> https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html
> https://www.w3schools.com
> https://www.youtube.com/watch?v=ZIzoesrHHQo&ab_channel=DavidDobervich
> Please Check the README file
