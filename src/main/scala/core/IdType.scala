package core

import java.util.UUID
import java.util.UUID.randomUUID

trait IdType {
  type IdValue
}

trait UUIDBasedIdType extends IdType {
  type IdValue = UUID
}

object UUIDBasedIdType {
  implicit def generator[T <: UUIDBasedIdType](implicit gen: () => UUID = () => randomUUID()): IdValueGenerator[T] = IdValueGenerator[T]
}
