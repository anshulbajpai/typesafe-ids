# typesafe-ids
A scala library to create typesafe ids for different domain concerns.

[![Build Status](https://travis-ci.org/anshulbajpai/typesafe-ids.svg?branch=master)](https://travis-ci.org/anshulbajpai/typesafe-ids) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.anshulbajpai/typesafe-ids/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.anshulbajpai/typesafe-ids)
  

## What problem are we trying to solve?

Consider a method which tries to add an item to a user's basket.

```scala
def addItem(userId: UUID, basketId: UUID, itemId: UUID): Unit
```

We know the above method definition is quite fragile. All the three parameter types which are ids have the same type i.e. UUID.

At the call site, we can endup calling the above method by passing wrong arguments as shown below.

```scala
addItem(basketId, itemId, userId)
```

If this method is invoked as shown above, we can get undesirable results.

To overcome the above problem, an obvious solution is to have dedicated types for each id type.


## Using value classes

We can solve the above problem by having dedicated [value classes](https://docs.scala-lang.org/overviews/core/value-classes.html)
 
```scala
class UserId(val underlying: UUID) extends AnyVal
class BasketId(val underlying: UUID) extends AnyVal
class ItemId(val underlying: UUID) extends AnyVal

def addItem(userId: UserId, basketId: BasketId, itemId: ItemId): Unit
```

Now, we can guarantee that it is not possible to pass a wrong argument type to the above method.

But, we can obviously see structural duplication among the three value classes we created which is not desirable.

## Introducing typesafe-ids  

With typesafe-ids, we get to save on structural duplication while being completely typesafe.

Here is an example of how they can be used.

```scala

  import core.IdType
  import core.Id
    
  trait UUIDBasedIdType extends IdType {
    type IdValue = UUID
  }
  
  trait UserId extends UUIDBasedIdType
  trait BasketId extends UUIDBasedIdType
  trait ItemId extends UUIDBasedIdType
  
  def addItem(userId: Id[UserId], basketId: Id[BasketId], itemId: Id[ItemId]): Unit
  
  val userId: Id[UserId] = Id[UserId](UUID.randomUUID())
  val basketId: Id[BasketId] = Id[BasketId](UUID.randomUUID())
  val itemId: Id[ItemId] = Id[ItemId](UUID.randomUUID())
  
  addItem(userId, basketId, itemId)

```

In the above example, we just defined the structure of our Id once i.e. `UUIDBasedIdType` and then extended three other types from it.

The types `UserId`, `BasketId` and `ItemId` are [phantom types](https://blog.codecentric.de/en/2016/02/phantom-types-scala/). They only encode type constraints but are never instantiated.

## How to use typesafe-ids?

#### Provide a value 

As shown in above example, we can create an instance of `Id[UserId]` by passing an argument of type `UUID` to the companion object method `Id.apply`. 
This type of instantiation is helpful when we already know the id of a domain, e.g. we obtained the id from database or from an external service.

#### Generate a value

We can also let the library generate a value of an Id. This is useful when your service or component is responsible for creating unique ids.
```scala
  import core.IdType
  import core.Id
    
  trait UUIDBasedIdType extends IdType {
    type IdValue = UUID
  }
  
  object UUIDBasedIdType {
    implicit def apply[T <: UUIDBasedIdType](implicit gen: () => UUID = () => randomUUID()): IdValueGenerator[T] = IdValueGenerator[T]
  }

  val userId: Id[UserId] = Id[UserId]
```
    
The above code will create `userId` variable with type `Id[UserId]` with a random UUID value.

There is a default uuid generator provided in the companion object method `UUIDBasedIdType.apply`. An explicit generator can also be passed if required to this method.


#### Accessing the internal value
Till now, we have only learnt how to create the `Id` objects but we didn't see how to access the internal value wrapped inside those objects.

The wrapped value can be accessed simply by accessing the `value` field of the objects.

```scala
val userId1: Id[UserId] = Id[UserId]
val value1: UUID = userId1.value
println(value1)

val userId2: Id[UserId] = Id[UserId](UUID.randomUUID())
val value2: UUID = userId2.value
println(value2)
```

#### Library defaults

`UUIDBasedIdType` is already implemented in the library.

To access it, you just need to import `core.idtypes.UUIDBasedIdType`.


#### Custom types and contributing
You can also implement your own custom `IdType`s like how `UUIDBasedIdType` is implemented. 

If you'd like to contribute the new type to the library then open an issue and a PR.
