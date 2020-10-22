import data.model.Player
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.openqa.selenium.WebDriver

object PlayerDataExtractManager {
    fun extractLastPageNum(doc: Document): Int{
        return doc.getElementsByClass("page-link  waves-effect waves-effect")
            .mapNotNull { it.text().toIntOrNull() }
            .last()
    }

    fun extractPlayerListElements(doc: Document): List<Element>{
        return doc.getElementById("repTb").select("tbody > tr")
    }

    fun extractSinglePlayerData(element: Element){
        // Name
        val name = element.getElementsByClass("player_name_players_table").first().text()
        // Price
        val price = element.getElementsByClass("ps4_color font-weight-bold").first().text()
        println("Name = $name - Price = $price")
    }

    fun getPlayerData(driver: WebDriver, playerUrl: String): Player? {
        if (!playerUrl.isNullOrBlank()) {
            val fullUrl = "https://www.futbin.com$playerUrl"
            driver.get(fullUrl)
            val singlePlayerDoc = Jsoup.parse(driver.pageSource)
            val name = extractName(singlePlayerDoc)
            val price = extractPrice(singlePlayerDoc)
            return Player(name, price)
        }
        return null
    }

    private fun extractName(doc: Document): String {
        return doc.select("td.table-row-text").first().text()
    }

    private fun extractPrice(doc: Document): String {
        return doc.select("span.price_big_right").first().text()
    }
}

