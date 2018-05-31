package core

import java.util.UUID
import java.util.UUID.randomUUID

object idtypes {
  trait UUIDBasedIdType extends IdType {
    type IdValue = UUID
  }

  object UUIDBasedIdType {
    implicit def apply[T <: UUIDBasedIdType](implicit gen: () => UUID = () => randomUUID()): IdValueGenerator[T] = IdValueGenerator[T]
  }
}
