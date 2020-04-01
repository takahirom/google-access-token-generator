import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import java.io.FileInputStream
import java.io.IOException
import java.util.*

fun getAccessToken(scopes: List<String>, jsonPath: String): String? {
    val googleCredential: GoogleCredential = GoogleCredential
        .fromStream(FileInputStream(jsonPath))
        .createScoped(scopes)
    googleCredential.refreshToken()
    return googleCredential.getAccessToken()
}

class Choice() : ArgType<List<String>>(true) {
    override val description: kotlin.String
        get() = "{ Values }"

    override fun convert(value: kotlin.String, name: kotlin.String): List<kotlin.String> =
        value.split(",")
}

fun main(args: Array<String>) {
    val parser = ArgParser("generator")
    val servicesJsonPath by parser.argument(ArgType.String, description = "Services json")
    val scopes by parser.argument(
        Choice(),
        description = "Scopes"
    )
    parser.parse(args)
    println(getAccessToken(scopes, servicesJsonPath))
}
