package mathilde

import zio.ZIOAppDefault
import zio.http.*
import zio.json.*
import java.util.UUID
import zio.ZIO

  enum Errors:
    case JsonPayloadError
    case IdFormatError
    case NotFoundError

object Main extends ZIOAppDefault:

  case class ToDo(id: UUID, title: String, description: String)

  case class CreateToDoRequest(title: String, description: String)

  val database = List(
  ToDo(UUID.fromString("58e0a7d7-eebc-11d8-9669-0800200c9a61"), "Laver mon appart", "Salle de bain, vaisselle, sol, table"),
  ToDo(UUID.fromString("58e0a7d7-eebc-11d8-9669-0800200c9a62"), "Faire des machines", "blancs et couleurs séparé"),
  ToDo(UUID.fromString("58e0a7d7-eebc-11d8-9669-0800200c9a63"), "Arroser plantes", "si la terre est seche")
  ).map(todo => todo.id -> todo).to(collection.mutable.Map)

  given JsonEncoder[ToDo]              = DeriveJsonEncoder.gen[ToDo]
  given JsonDecoder[CreateToDoRequest] = DeriveJsonDecoder.gen[CreateToDoRequest]

  val app =
    Routes(
      Method.GET / "todo" -> handler(Response.json(database.values.toJsonPretty)),
      Method.POST / "todo" -> handler { (req: Request) =>
      for {
        // Ici req.body.asString peut prendre trop de place (out of memory error), 
        //donc .orDie permet de mettre cette erreur en "500 : Intenal Server Error".
        body   <- req.body.asString.orDie
        //La partie "Left" de Etheir (de body.formJson) va dans la chanel des erreurs.
        entity <- ZIO.fromEither(body.fromJson[CreateToDoRequest]).mapError(_ => Errors.JsonPayloadError)
        todo   = ToDo(UUID.randomUUID(), entity.title, entity.description)
        _      = database += (todo.id -> todo)
      } yield Response.json(todo.toJson)
      },     
      Method.GET / "todo" / string("id") -> handler { (id: String, _: Request) =>
        for {
          uuid <- ZIO.attempt(UUID.fromString(id))
                    .refineToOrDie[IllegalArgumentException]
                    .mapError(_ => Errors.IdFormatError)
          todo <- ZIO.fromOption(database.get(uuid))
                    .mapError(_ => Errors.NotFoundError)
        } yield Response.json(todo.toJson)
      },
      Method.DELETE / "todo" / string("id") -> handler { (id: String, _: Request) =>
      val uuid = UUID.fromString(id)
      database -= uuid
      Response.ok
    } 
    ).handleError(_ match
    case Errors.NotFoundError    => Response.notFound
    case Errors.JsonPayloadError => Response.badRequest("Wrong JSON payload")
    case Errors.IdFormatError    => Response.badRequest("Wrong id format (should be UUID)")).toHttpApp

  def run = Server.serve(app).provide(Server.defaultWithPort(8090))