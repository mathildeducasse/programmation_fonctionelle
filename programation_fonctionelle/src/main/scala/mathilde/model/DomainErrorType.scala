package mathilde.model

import zio.json.{DeriveJsonCodec, JsonCodec}
import zio.schema.annotation.description
import zio.schema.{DeriveSchema, Schema}


object DomainErrorType{
    final case class Contributor(
    @description("Contributor's repository") repo: String,
    @description("Contributor's name") contributor: String,
    @description("Contributor's number of contributions") contributions: Int
    )

    object Contributor {
    implicit val jsonCodec: JsonCodec[Contributor] = 
        DeriveJsonCodec.gen[Contributor]
    implicit val schema: Schema[Contributor] = 
        DeriveSchema.gen[Contributor]
    }

    sealed trait ErrorTypeH

    object ErrorTypeH {
        final case class OrganizationNotFound() extends ErrorTypeH
        final case class LimitExceeded() extends ErrorTypeH
        final case class UnexpectedError() extends ErrorTypeH

        implicit val organizationNotFoundSchema: Schema[ErrorTypeH.OrganizationNotFound] =
            DeriveSchema.gen[ErrorTypeH.OrganizationNotFound]
        implicit val limitExceededSchema: Schema[ErrorTypeH.LimitExceeded] =
            DeriveSchema.gen[ErrorTypeH.LimitExceeded]
        implicit val unexpectedErrorSchema: Schema[ErrorTypeH.UnexpectedError] =
            DeriveSchema.gen[ErrorTypeH.UnexpectedError]
    }
}
    