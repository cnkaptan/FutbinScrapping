import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

object Application {
    val driver: WebDriver = ChromeDriver()

    @JvmStatic
    fun main(args: Array<String>) {
        System.setProperty("webdriver.chrome.driver", "${System.getProperty("user.dir")}/chromedriver");
        val url = "https://www.futbin.com/players?page=1&version=gold_all"
        driver.get(url)
        val doc = Jsoup.parse(driver.pageSource)
        val lastPageNum = PlayerDataExtractManager.extractLastPageNum(doc)
        val playerElements = PlayerDataExtractManager.extractPlayerListElements(doc)
        playerElements.forEach {
            PlayerDataExtractManager.extractSinglePlayerData(element = it)
        }
    }
}