# spray-crypter
This basic application uses Spray and AngularJS to create a basic encrypter and decrypter using the Advanced Encryption
Standard (AES). 

# Starting
This project uses the Typesafe Activator Launcher, only a Java 6 or higher must be installed on your computer and 
the activator-laucher will do the rest:

    $ ./activator 'run-main com.example.Main'

# Docker
## Run the image
When you have Docker installed, you can launch a [containerized version](https://registry.hub.docker.com/u/dnvriend/spray-crypter/) using the following command:

    $ sudo docker run -d -P dnvriend/spray-crypter
    
Then check which local port has been mapped to the VM
    
    $ sudo docker ps
    
And note the entries in the PORTS column eg:

    CONTAINER ID        IMAGE                     COMMAND                CREATED             STATUS              PORTS                     NAMES
    ade95dac9e4e        dnvriend/spray-crypter:latest   /bin/sh -c java -jar   5 minutes ago       Up 5 minutes        0.0.0.0:49154->8080/tcp   sick_darwin

In this example, the local port of my Vagrant VM has been mapped to port 49154 to the port of the crypter, and that is 8080. 
Point the browser to the following url (change the port to your mapped port):

    http://192.168.99.99:49154/web/index.html

## Creating the image
Inside Vagrant navigate to
 
    $ cd /spray-crypter

Then type

	$ sudo docker build --rm -t dnvriend/spray-crypter .

This image is more advanced, try giving it arguments:

    $ sudo docker run -d -P --env PASS_PHRASE=abcdefghijklmnop --name mycrypt dnvriend/spray-crypter

## Pusing the image to [docker hub](https://hub.docker.com/)
This is just an example:
	
	$ sudo docker push dnvriend/crypter

# Creating one jar
For distribution of our Spray applications, we can use the one-jar plugin, just type:

    $ ./activator 'one-jar'
    
The resulting jar will be placed in:
     
    $ target/scala-2.10/spray-crypter_2.10-0.1-SNAPSHOT-one-jar.jar
    
Launch it with the following command:
    
    $ java -jar spray-crypter_2.10-0.1-SNAPSHOT-one-jar.jar
    
Stop the application with:
 
    CTRL+C    

# Usage
The application will automatically launch your default browser with the application in it, but if it doesn't:

    http://localhost:8080/web/index.html
    
# Conclusion
Spray with AngularJS and the one-jar plugin is a great way to package our applications and distribute it. 

I'm going to fiddle around with it a bit more :-)
    
Have fun!