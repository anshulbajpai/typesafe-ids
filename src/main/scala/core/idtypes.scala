package core

import java.util.UUID

object idtypes {
  trait UUIDBasedIdType extends IdType {
    type IdValue = UUID
  }
}
