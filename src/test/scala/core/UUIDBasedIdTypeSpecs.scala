package core

import java.util.UUID
import java.util.UUID.randomUUID

import core.idtypes.UUIDBasedIdType
import org.scalatest.{Matchers, WordSpecLike}

class UUIDBasedIdTypeSpecs extends  WordSpecLike with Matchers {

  "an Id of UUIDBasedIdType" should {

    "be created from uuid value" in new Setup {
      val uuid = randomUUID
      val userId = Id[UserId](uuid)
      userId.value shouldBe uuid
    }

    "be generated using default uuid generator" in new Setup {
      val userId = Id[UserId]
      UUID.fromString(userId.value.toString) shouldBe userId.value
    }

    "be generated distinctly using default uuid generator" in new Setup {
      (1 to 10).map(_ => Id[UserId].value).toSet should have size 10
    }

    "be generated using provided uuid generator" in new Setup {
      val numberOfIds = 10
      val uuids = (1 to numberOfIds) map (_ => randomUUID())
      val uuidsIterator = uuids.toIterator
      implicit val uuidGenerator = () => uuidsIterator.next()

      val userIds = (1 to numberOfIds) map (_ => Id[UserId].value)

      userIds should contain theSameElementsInOrderAs uuids
    }
  }

  trait Setup {
    trait UserId extends UUIDBasedIdType
  }

}
