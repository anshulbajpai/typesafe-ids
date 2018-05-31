package core

trait IdValueGenerator[A <: IdType] {
  def generate: A#IdValue
}

object IdValueGenerator {
  def apply[A <: IdType](implicit f: () => A#IdValue): IdValueGenerator[A] = new IdValueGenerator[A] {
    override def generate: A#IdValue = f()
  }
}

