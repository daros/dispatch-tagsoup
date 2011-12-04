package dispatch.jsoup

import dispatch._
import org.jsoup.nodes.Document
import org.jsoup.Jsoup

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-12-03 21:27 
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

trait ImplicitJSoupHandlers {
  implicit def requestToJSoupHandlers(req: Request) = new JSoupHandlers(req);
  implicit def stringToJSoupHandlers(str: String) = new JSoupHandlers(new Request(str));
}

object JSoupHttp extends ImplicitJSoupHandlers

class JSoupHandlers(request: Request) {
  def jsouped [T] (block: (Document) => T) = request >> { (stm, charset) =>
    block(Jsoup.parse(stm, charset, request.to_uri.toString))
  }
  def <\\ [T] (block: (Document) => T) = jsouped (block)
  def as_jsouped = jsouped {dom => dom}
}