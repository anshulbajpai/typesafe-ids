package com.github.anshulbajpai.typsafeids.core

case class Id[A <: IdType](value: A#IdValue) extends AnyVal

object Id {
  def apply[T <: IdType](implicit generator: IdValueGenerator[T]): Id[T] = apply(generator.generate)
}