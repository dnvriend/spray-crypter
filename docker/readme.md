# Create an image

	sudo docker build --rm -t dnvriend/crypter .

# run the image

	sudo docker run -d -P dnvriend/crypter

# pusing the image to [docker hub](https://hub.docker.com/)
	
	sudo docker push dnvriend/crypter
