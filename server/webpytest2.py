#coding=utf-8
import web
from web import form
urls = (
   '/', 'index',
   '/login','login'
)

render = web.template.render('./')

class index:
   def GET(self):
       return u'Welcome'

loginform = form.Form(
   form.Textbox('username'),
   form.Password('password'),
   form.Button('Login'),
)

class login:
   def GET(self):
       return render.login(loginform)

   def POST(self):
       i = web.input()
       if i.username == 'admin' and i.password == 'admin':
           web.redirect('/')
       else:
           return render.login(loginform)

if __name__ == "__main__":
   app = web.application(urls, globals(),True)
   app.run()