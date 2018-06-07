package com.github.anshulbajpai.typsafeids.json.play

import com.github.anshulbajpai.typsafeids.core.{Id, IdType}
import play.api.libs.json._

object implicits {

  implicit def reads[T <: IdType](implicit idValueReads: Reads[T#IdValue]): Reads[Id[T]] =
    (json: JsValue) => idValueReads.reads(json).map(Id[T])

  implicit def writes[T <: IdType](implicit idValueWrites: Writes[T#IdValue]): Writes[Id[T]] =
    (o: Id[T]) => idValueWrites.writes(o.value)

  implicit def format[T <: IdType](implicit idValueReads: Reads[T#IdValue], idValueWrites: Writes[T#IdValue]): Format[Id[T]] =
    Format(reads, writes)

}
