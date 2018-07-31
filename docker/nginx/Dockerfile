# Set nginx base image
FROM nginx

# File Author / Maintainer
MAINTAINER Travis Cummings

# Copy custom configuration file from the current directory
COPY nginx.conf /etc/nginx
RUN mkdir /etc/nginx/sites-available
RUN chmod 777 /etc/nginx/sites-available
RUN mkdir /etc/nginx/sites-enabled

# auth
COPY auth.conf /etc/nginx/sites-available
RUN ln -sf /etc/nginx/sites-available/auth.conf /etc/nginx/sites-enabled/auth.conf
EXPOSE 8060

# auth-debug
COPY auth-debug.conf /etc/nginx/sites-available
RUN ln -sf /etc/nginx/sites-available/auth-debug.conf /etc/nginx/sites-enabled/auth-debug.conf
EXPOSE 5060
