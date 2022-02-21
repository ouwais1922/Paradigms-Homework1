In this homework I came up with two folders:

> cloudDrive-Client where we are dealing with just one client and using all the basics of socket api to make the basics of cloud drive : upload, download, delete, rename, and backup.

> cloudDrive: after I took a long time to understand how we can deal with multiple client. I rely in this folder on MultiThrading in java inspiring from **stackoverflow && docs oracle**. Well, in this folder we can have a cloud with multiple clients, so all the features that we have in cloudDrive-Client(single client) we can use here in cloudDrive, but I got just to manupilate relation between a Server and multiple clients.

## Delete:

As google drive is our first point of inspiration, When a client wants to delete a file from his/her Drive, the file will be uploaded to the Bin and deleted from the client drive. (please check: Homework1-Cloud-Drive/cloud-delete.jpg)
