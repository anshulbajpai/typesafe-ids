package core

import java.util.UUID

import org.scalatest.{Matchers, WordSpecLike}

import scala.collection.immutable

class IdSpecs extends WordSpecLike with Matchers {

  "an id" should {
    "have the same value with which it was created" in new Setup {
      val userIdValue = "userid-1"
      val userId: Id[UserId] = Id[UserId](userIdValue)
      userId.value shouldBe userIdValue
    }

    "have the value created by generator" in new Setup {
      val numberOfIds = 10

      val uuids: immutable.IndexedSeq[String] = 1 to numberOfIds map (_ => UUID.randomUUID().toString)
      val uuidsIterator = uuids toIterator

      override implicit val userIdValueGenerator = IdValueGenerator[UserId](() => uuidsIterator.next())

      val userIds: immutable.IndexedSeq[String] = (1 to numberOfIds) map (_ => Id[UserId].value)

      userIds should contain theSameElementsInOrderAs uuids
    }

    "not be allowed to use incorrectly" in new Setup {
      trait ItemId extends IdType {
        override type IdValue = String
      }

      def buyItem(userId: Id[UserId], itemId: Id[ItemId]): Unit = {}

      """buyItem(Id[ItemId], Id[UserId])""" shouldNot typeCheck
    }
  }


  trait Setup {

    implicit val userIdValueGenerator = IdValueGenerator[UserId](() => UUID.randomUUID().toString)

    trait UserId extends IdType {
      override type IdValue = String
    }
  }

}
