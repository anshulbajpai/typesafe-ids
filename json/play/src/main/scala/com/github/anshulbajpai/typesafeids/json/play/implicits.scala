package com.github.anshulbajpai.typesafeids.json.play

import com.github.anshulbajpai.typsafeids.core.{Id, IdType}
import play.api.libs.json._

object implicits {

  implicit def reads[T <: IdType](implicit idValueReads: Reads[T#IdValue]): Reads[Id[T]] = new Reads[Id[T]] {
    override def reads(json: JsValue): JsResult[Id[T]] = idValueReads.reads(json).map((value: T#IdValue) => Id[T](value))
  }

  implicit def writes[T <: IdType](implicit idValueWrites: Writes[T#IdValue]): Writes[Id[T]] = new Writes[Id[T]] {
    override def writes(o: Id[T]): JsValue = idValueWrites.writes(o.value)
  }

  implicit def format[T <: IdType](implicit idValueReads: Reads[T#IdValue], idValueWrites: Writes[T#IdValue]): Format[Id[T]] =
    Format(reads, writes)

}
