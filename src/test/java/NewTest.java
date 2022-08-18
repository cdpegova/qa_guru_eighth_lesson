import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class NewTest {

    @BeforeEach
    void openSite(){
        open("https://github.com/");
        element("[name = \"q\"]").click();
    }

    @ValueSource(strings = {"Selenide", "JUnit", "Kotlin"})
    @ParameterizedTest(name = "Поиск от {0} до {2}")
    void testSearch(String testData){
        element("[name = \"q\"]").setValue(testData).pressEnter();
        elements("li.repo-list-item").get(0).shouldHave(Condition.text(testData));
    }

    @CsvSource(value = {
            "Selenide, Concise UI Tests with Java!",
            "JUnit, A programmer-oriented testing framework for Java.",
            "Kotlin, The Kotlin Programming Language.",
    })
    @ParameterizedTest(name = "Поиск от {0} до {2} с описанием")
    void testSearchWithDescription(String testData, String expectedResult){
        element("[name = \"q\"]").setValue(testData).pressEnter();
        elements("li.repo-list-item").get(0).shouldHave(Condition.text(expectedResult));
    }

    static Stream<Arguments> searchKotlinTest(){
        return Stream.of(
                Arguments.of("Котлин", List.of("Sign up","Kotlin-Polytech/KotlinAsFirst2019", "Kotlin-Polytech/KotlinAsFirst", "JohnnySC/Lectures", "paracelst/dev-intensive-2019", "SergTaranenko/KotlinAplication", "kaldorey/first_kotlin", "DenisPolulyakh/KotlinLessons", "asim313/TGU_RMP_7_androidLern", "OlgaEEova/Summary", "jershell/kotlin_slow_start")),
                Arguments.of("Kotlin", List.of("Sign up", "JetBrains/kotlin", "hussien89aa/KotlinUdemy", "TheAlgorithms/Kotlin", "git-xuhao/KotlinMvp", "Kotlin/kotlin-koans Public archive", "Kotlin/kotlinx.coroutines", "KotlinBy/awesome-kotlin", "google-developer-training/android-kotlin-fundamentals-apps", "exercism/kotlin", "JetBrains/kotlin-web-site"))

        );
    }
    @MethodSource()
    @ParameterizedTest(name = "Поиск {0} и сверка с {1}")
    void searchKotlinTest(String testData, List<String> outputText) {
        element("[name = \"q\"]").setValue(testData).pressEnter();
        $$(".f4").shouldHave(CollectionCondition.texts(outputText));
    }

}
