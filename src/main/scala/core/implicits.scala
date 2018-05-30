package core

import java.util.UUID

object implicits {

  implicit val uuidGenerator: () => UUID = () => UUID.randomUUID()

}
