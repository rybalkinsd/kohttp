import kotlin.test.assertEquals


/**
 * Created by Gokul on 03/01/2019.
 */

fun assertResponses(actual: LinkedHashMap<String, Any>, expected: HashMap<String, String>) {
    expected.forEach { t, u ->
        assertEquals(u, actual[t])
    }
}