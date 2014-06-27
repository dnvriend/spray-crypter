# spray-crypter
This basic application uses Spray and AngularJS to create a basic encrypter and decrypter using the Advanced Encryption
Standard (AES). 

# Starting
This project uses the Typesafe Activator Launcher, only a Java 6 or higher must be installed on your computer and 
the activator-laucher will do the rest:

    $ ./activator 'run-main com.example.Main'

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