# Gmedia Gallery on WordPress 3.3.3
FROM ubuntu:lucid
MAINTAINER Yuta Maezawa <maezawa@nii.ac.jp>

ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && \
	apt-get -yq install \
		wget unzip git-core vim \
		apache2 \
		php5 libapache2-mod-php5 php5-mysql \
		openssh-server && \
		rm -rf /var/lib/apt/lists/*

## SSH
RUN mkdir /var/run/sshd && \
	sed -i "s/UsePrivilegeSeparation.*/UsePrivilegeSeparation no/g" /etc/ssh/sshd_config && \
	sed -i "s/UsePAM.*/UsePAM no/g" /etc/ssh/sshd_config && \
	sed -i "s/PermitRootLogin.*/PermitRootLogin yes/g" /etc/ssh/sshd_config
RUN echo "root:root" | chpasswd
#RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config
EXPOSE 22

## Apache
EXPOSE 80
RUN echo "ServerName localhost" >> /etc/apache2/apache2.conf && \
	sed -i -e "s/AllowOverride None/AllowOverride All/" /etc/apache2/sites-available/default && \
	a2enmod rewrite
ADD htaccess /var/www/.htaccess

## WordPress 3.6
RUN wget http://66.155.40.250/wordpress-3.6.zip --no-check-certificate && \
	unzip wordpress-3.6.zip && \
	cp -r wordpress/* /var/www/ && \
	rm -r wordpress wordpress-3.6.zip /var/www/index.html && \
	cp /var/www/wp-config-sample.php /var/www/wp-config.php && \
	sed -i \
		-e 's/database_name_here/wp/g' \
		-e 's/username_here/admin/g' \
		-e 's/password_here/mypass/g' \
		-e 's/localhost/192.168.59.103/g' \
		/var/www/wp-config.php
WORKDIR /var/www/wp-content
RUN mkdir -p grand-media/image && chmod -R 777 grand-media && \
	mkdir grand-media/image/original && chmod 777 grand-media/image/original  && \
	mkdir grand-media/image/thumb && chmod 777 grand-media/image/thumb
ADD icse2016-logo.jpg /var/www/wp-content/grand-media/image/icse2016-logo.jpg
ADD icse2016-logo.jpg /var/www/wp-content/grand-media/image/original/icse2016-logo.jpg
ADD icse2016-logo.jpg /var/www/wp-content/grand-media/image/thumb/icse2016-logo.jpg
RUN chmod 644 /var/www/wp-content/grand-media/image/icse2016-logo.jpg
RUN chmod 644 /var/www/wp-content/grand-media/image/original/icse2016-logo.jpg
RUN chmod 644 /var/www/wp-content/grand-media/image/thumb/icse2016-logo.jpg

## Plugins
WORKDIR /var/www/wp-content/plugins
RUN rm -r akismet hello.php
RUN git clone https://github.com/wp-plugins/grand-media /var/www/wp-content/plugins/grand-media
	
ADD run.sh /run.sh
RUN chmod 755 /*.sh
CMD ["/run.sh"]
