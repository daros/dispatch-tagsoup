import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.specs._
import dispatch._
import dispatch.jsoup._


/**
 * Created by IntelliJ IDEA.
 * Created: 2011-12-03 22:13 
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

object JSoupSpec extends Specification {
  object JSoupHtml_Implicit
    extends Request(:/("en.wikipedia.org"))
    with ImplicitJSoupHandlers

  "Extending implicit JSoup" should {
    "make JSoup parsable" in {
      val doc = Http(JSoupHtml_Implicit as_jsouped)
      val newsHeadlines: Elements = doc.select("#mp-itn b a")

      import scala.collection.JavaConversions._
      val l = newsHeadlines.iterator

      l.zipWithIndex.foreach(t => {println(t._2 + ": " + t._1.text + ": " + t._1.attr("abs:href")) })

      newsHeadlines.size must be_>(2)
      val lastElm = newsHeadlines.last()
      lastElm.html() must be_==("More&nbsp;current&nbsp;events...")

      val req = newsHeadlines.first.attr("href").split("/").foldLeft(JSoupHtml_Implicit: Request)(_ / _)
      import JSoupHttp._
      val doc2 = Http(req as_jsouped)
      println(doc2.html)
    }
  }
}