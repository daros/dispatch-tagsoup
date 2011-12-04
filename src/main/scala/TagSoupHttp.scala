package dispatch.tagsoup

import xml.parsing.NoBindingFactoryAdapter
import java.io.InputStreamReader
import dispatch.{HandlerVerbs, Request}
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import org.xml.sax.InputSource

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-12-01 02:34 
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

trait ImplicitTagSoupHandlers {
  implicit def handlerToTagSoupHandlers(h: HandlerVerbs) = new TagSoupHandlers(h)
  implicit def requestToTagSoupHandlers(req: Request) = new TagSoupHandlers(req);
  implicit def stringToTagSoupHandlers(str: String) = new TagSoupHandlers(new Request(str));
}

object TagSoupHttp extends ImplicitTagSoupHandlers

class TagSoupHandlers(subject: HandlerVerbs) {
  val parserFactory = new SAXFactoryImpl
  /** Process response with TagSoup html processor in block */
  def tagsouped [T] (block: (xml.NodeSeq) => T) = subject >> { (stm, charset) =>
      block( new NoBindingFactoryAdapter().loadXML(new InputSource(new InputStreamReader(stm, charset)), parserFactory.newSAXParser()) )
  }
  /** Alias for verb tagsouped */
  def <\\> [T] (block: (xml.NodeSeq) => T) = tagsouped (block)
  /** Conveniences handler for retrieving a NodeSeq */
  def as_tagsouped = tagsouped {ns => ns}
}