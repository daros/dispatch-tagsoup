package dispatch.tagsoup

import dispatch.Request
import xml.parsing.NoBindingFactoryAdapter
import java.io.InputStreamReader

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-12-01 02:34 
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

object ImplicitTagSoup extends ImplicitTagSoupHandlerVerbs

trait ImplicitTagSoupHandlerVerbs {
  implicit def toTagSoupHandlerVerbs(req: Request) = new TagSoupHandlerVerbs(req);
  implicit def stringToTagSoupHandlerVerbs(str: String) = new TagSoupHandlerVerbs(new Request(str));
}

class TagSoupHandlerVerbs(request: Request) {
  val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

  def tagsouped [T] (block: xml.NodeSeq => T) = request >> { (is, enc) =>
      block(
        new NoBindingFactoryAdapter().loadXML(
            new org.xml.sax.InputSource(new InputStreamReader(is, enc)),
            parserFactory.newSAXParser()
        )
      )
  }

  def <\\> [T] (block: xml.NodeSeq => T) = tagsouped (block)

  def as_tagsouped = <\\> {ns => ns}
}