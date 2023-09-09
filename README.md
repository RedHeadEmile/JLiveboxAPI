# Livebox API

This is a little (and easy expandable) interface to interact with your Livebox (the internet interface of the French ISP Orange).
My Livebox software is under version 4.66.0, feel free to fork this project to support other firmware version.

The Livebox 3 and 4 protocol is pretty simple, all the request are send with a POST request on `http://192.168.1.1/ws` with a service, a method and the arguments of this method.
Then it returns an object which depends on the request.

I only implemented requests I needed for now:
- Login
- List port forwarding rules
- Update port forwarding rule
- Delete port forwarding rule

Tell me if you need help (by creating an issue) to implement other requests. 
