error id: file://<WORKSPACE>/programation_fonctionelle/src/main/scala/mathilde/Main.scala:`<none>`.
file://<WORKSPACE>/programation_fonctionelle/src/main/scala/mathilde/Main.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -zio/http/Method.
	 -Method.
	 -scala/Predef.Method.
offset: 196
uri: file://<WORKSPACE>/programation_fonctionelle/src/main/scala/mathilde/Main.scala
text:
```scala
package mathilde

import zio.ZIOAppDefault
import zio.http.*

object Main extends ZIOAppDefault:

  val app =
    Routes(
      Method.GET / "todo" -> Handler.text("Welcome to JSON APIs!"),
      @@Method.GET / "todo" / string("id") ->
      handler { (id: String, req: Request) =>
        Response.text(s"This will show a TODO item with id: $id")
      } 
    ).toHttpApp

  def run = Server.serve(app).provide(Server.defaultWithPort(8090))
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.