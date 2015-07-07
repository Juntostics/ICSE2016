#!/bin/bash
exec /usr/sbin/sshd -D &
source /etc/apache2/envvars
exec /usr/sbin/apache2 -D FOREGROUND
