#FROM debian:stretch-slim
#RUN apt-get update
#RUN apt-get -y install apache2 php7.0 php7.0-cli php7.0-common php7.0-json php7.0-opcache php7.0-mysql php7.0-zip php7.0-fpm php7.0-mbstring
#RUN DEBIAN_FRONTEND=noninteractive apt-get install -y mariadb-server
#EXPOSE 80 
#WORKDIR /var/www/html/
#ADD --chown=www-data:www-data DevOps_Practice_Repository.tar /var/www/html/
#CMD /bin/bash
#CMD mysql --user="root" --password="" < db_sistema_mas_datos.sql

FROM caiocaliman/lampp
WORKDIR /var/www/html/
ADD --chown=www-data:www-data DevOps_Practice_Repository.tar /var/www/html/
EXPOSE 80
CMD /bin/bash
