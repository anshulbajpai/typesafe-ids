package com.github.anshulbajpai.typsafeids.json.play

import java.util.UUID

import com.github.anshulbajpai.typsafeids.core.Id
import com.github.anshulbajpai.typsafeids.core.idtypes.UUIDBasedIdType
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsString, JsSuccess, Json}

class PlayJsonSpecs extends WordSpec with Matchers {

  import com.github.anshulbajpai.typsafeids.json.play.implicits._

  "play json" should {

    "serialize " in new Setup {
      Json.toJson(userId) shouldBe JsString(userId.value.toString)
    }

    "deserialize " in new Setup {
      val uuid = UUID.randomUUID()
      Json.fromJson[Id[UserId]](JsString(userId.value.toString)) shouldBe JsSuccess(userId)
    }
  }

  trait Setup {
    trait UserId extends UUIDBasedIdType
    val userId = Id[UserId]
  }

}
