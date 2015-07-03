# Tagged Gallery on WordPress 3.3.3
FROM ubuntu:lucid
MAINTAINER Yuta Maezawa <maezawa@nii.ac.jp>

ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && \
	apt-get -yq install \
		wget unzip \
		apache2 \
		php5 libapache2-mod-php5 php5-mysql \
		&& \
	rm -rf /var/lib/apt/lists/*

## Apache
EXPOSE 80
RUN echo "ServerName localhost" >> /etc/apache2/apache2.conf && \
	a2enmod rewrite

## WordPress 3.3.3
RUN wget https://wordpress.org/wordpress-3.3.3.zip --no-check-certificate && \
	unzip wordpress-3.3.3.zip && \
	cp -r wordpress/* /var/www/ && \
	rm -r wordpress wordpress-3.3.3.zip && \
	cp /var/www/wp-config-sample.php /var/www/wp-config.php && \
	sed -i \
		-e 's/database_name_here/wp/g' \
		-e 's/username_here/admin/g' \
		-e 's/password_here/admin/g' \
		-e 's/localhost/192.168.59.103/g' \
		/var/www/wp-config.php
	
ADD run.sh /run.sh
RUN chmod 755 /*.sh
CMD ["/run.sh"]
