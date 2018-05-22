package core

trait Id[A <: IdType] {
  def value: A#IdValue
}

object Id {

  def apply[T <: IdType](implicit generator: IdValueGenerator[T]): Id[T] = apply(generator.generate)

  def apply[T <: IdType](newValue: T#IdValue): Id[T] = new Id[T] {
    override val value: T#IdValue = newValue
  }
}