import com.example.materialapp.ui.view.utils.SearchRequestValidator
import org.junit.Assert
import org.junit.Test

class SearchRequestValidatorTests {
    @Test
    fun emptyRequest_returnsFalse() {
        Assert.assertFalse(SearchRequestValidator.isValid(""))
    }

    @Test
    fun lowercaseRequest_returnsTrue() {
        Assert.assertTrue(SearchRequestValidator.isValid("hello"))
    }

    @Test
    fun uppercaseRequest_returnsTrue() {
        Assert.assertTrue(SearchRequestValidator.isValid("HELLO"))
    }

    @Test
    fun russianRequest_returnsTrue() {
        Assert.assertTrue(SearchRequestValidator.isValid("Привет"))
    }

    @Test
    fun specialSymbolsRequest_returnsTrue() {
        Assert.assertTrue(SearchRequestValidator.isValid("Hello! My name is ... I like cars, music. What do you like?"))
    }

    @Test
    fun emailRequest_returnsFalse() {
        Assert.assertFalse(SearchRequestValidator.isValid("someEmail@mail.ru"))
    }

    @Test
    fun bracesRequest_returnsTrue() {
        Assert.assertTrue(SearchRequestValidator.isValid("\"Hello world!\""))
    }

    @Test
    fun longRequest_returnsTrue() {
        Assert.assertFalse(SearchRequestValidator.isValid("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"))
    }

    @Test
    fun specialSymbols2Request_returnsTrue() {
        Assert.assertTrue(SearchRequestValidator.isValid(":;_"))
        Assert.assertFalse(SearchRequestValidator.isValid("\\//\\"))
    }
}