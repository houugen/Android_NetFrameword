import web 
from web.wsgiserver import CherryPyWSGIServer

CherryPyWSGIServer.ssl_certificate = "../cer/server1.crt"
CherryPyWSGIServer.ssl_private_key = "../cer/server_private.key"

urls = ("/.*", "hello")
app = web.application(urls, globals())

class hello:
	def GET(self):
		return 'Hello, world!'

if __name__ == "__main__":
	app.run()