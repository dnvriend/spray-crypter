package com.example

import spray.routing.SimpleRoutingApp
import akka.actor._
import org.apache.shiro.crypto.AesCipherService
import org.apache.shiro.codec.{Base64, CodecSupport}
import scala.util.Try
import org.apache.shiro.util.ByteSource
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Failure
import scala.Some
import scala.util.Success

trait Security {
  def encrypt(plainText: String): Try[String]
  def decrypt(encrypted: String): Try[String]
}

trait AESSecurity extends Security {
  object AES {
    val passPhrase = "abcdefghijklmnop"
    val cipher = new AesCipherService
  }

  override def encrypt(plainText: String): Try[String] =
    Try {
      AES.cipher.encrypt(plainText.getBytes, AES.passPhrase.getBytes).toBase64
    }

  override def decrypt(base64Encrypted: String): Try[String] =
    Try {
      val byteSource: ByteSource = ByteSource.Util.bytes(base64Encrypted)
      val decryptedToken = AES.cipher.decrypt(Base64.decode(byteSource.getBytes), AES.passPhrase.getBytes)
      CodecSupport.toString(decryptedToken.getBytes)
    }
}

object SecurityService {
  case class EncryptRequest(text: Option[String])
  case class EncryptResponse(encrypted: Option[String], error: Option[String])
  case class DecryptRequest(text: Option[String])
  case class DecryptResponse(decrypted: Option[String], error: Option[String])

  object JsonMarshaller extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val encryptRequestFormat = jsonFormat1(SecurityService.EncryptRequest)
    implicit val encryptResponseFormat = jsonFormat2(SecurityService.EncryptResponse)
    implicit val decryptRequestFormat = jsonFormat1(SecurityService.DecryptRequest)
    implicit val decryptResponseFormat = jsonFormat2(SecurityService.DecryptResponse)
  }
}

class SecurityService extends Actor with ActorLogging with AESSecurity {
  import SecurityService._

  override def receive: Receive = {
    case EncryptRequest(Some(text)) =>
      encrypt(text) match {
        case Success(encr) => sender ! EncryptResponse(Some(encr), None)
        case Failure(cause) => sender ! EncryptResponse(None, Some(cause.getMessage))
      }

    case DecryptRequest(Some(text)) =>
      decrypt(text) match {
        case Success(decr) => sender ! DecryptResponse(Some(decr), None)
        case Failure(cause) => sender ! DecryptResponse(None, Some(cause.getMessage))
      }
  }
}

object Main extends App with SimpleRoutingApp {
  import SecurityService.JsonMarshaller._
  implicit val system = ActorSystem("my-system")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(60 seconds)

  val securityService = system.actorOf(Props(new SecurityService))

  startServer(interface = "localhost", port = 8080) {
    pathPrefix("web") {
      getFromResourceDirectory("web")
//      getFromDirectory("/Users/dennis/projects/spray-crypter/src/main/resources/web")
    } ~
    pathPrefix("api") {
      path("encrypt") {
        post {
          entity(as[SecurityService.EncryptRequest]) { req =>
            complete {
              (securityService ? req).mapTo[SecurityService.EncryptResponse]
            }
          }
        }
      } ~
      path("decrypt") {
        post {
          entity(as[SecurityService.DecryptRequest]) { req =>
            complete {
              (securityService ? req).mapTo[SecurityService.DecryptResponse]
            }
          }
        }
      }
    }
  }

  // try to open the app in the default browser
  Try {
    println("Please wait, the application is launching...")
    // sleeping for my old Macbook@2009, it needs some time to get things up and running...
    Thread.sleep((5 seconds).toMillis)
    val url = "http://localhost:8080/web/index.html"
    java.awt.Desktop.getDesktop.browse(java.net.URI.create(url))
  } match {
    case Failure(cause) => println("Could not open browser: " + cause.getMessage)
    case _ =>
  }

}
