package dispatch.tagsoup

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import dispatch._

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-12-01 23:20
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

class TagSoupHandlerVerbsSpec extends FlatSpec with ShouldMatchers {

  "Extending implicit TagSoup" should "make BadHtml parsable" in {
    val request = BadHtml_with_ImplicitTagSoupHandlerVerbs
    val title = Http(request tagsouped { nodes =>
                  (nodes \\ "title").text
                })

    title should be("The Leviathan by Thomas Hobbes")
  }

  "Extending implicit TagSoup" should "make BadHtmlClass parsable, though this is ugly" in {
    var request = new BadHtmlClass() with ImplicitTagSoupHandlerVerbs
    val title = Http(request.toTagSoupHandlerVerbs(request) tagsouped { nodes =>
                  (nodes \\ "title").text
                })

    title should be("The Leviathan by Thomas Hobbes")
  }

  "Implicit TagSoup converters in scope" should "make Request parsable" in {
    import ImplicitTagSoup._
    val request = :/("oregonstate.edu") / "instruct" / "phl302" / "texts" / "hobbes" / "leviathan-c.html"
    val title = Http(request tagsouped { nodes =>
                  (nodes \\ "title").text
                })

    title should be("The Leviathan by Thomas Hobbes")
  }

  "Implicit TagSoup converters in scope" should "make BadHtml parsable" in {
    import ImplicitTagSoup._
    val request = BadHtml
    val title = Http(request tagsouped { nodes =>
                  (nodes \\ "title").text
                })

    title should be("The Leviathan by Thomas Hobbes")
  }

  "Implicit TagSoup converters in scope" should "make BadHtmlClass parsable" in {
    import ImplicitTagSoup._
    val request = new BadHtmlClass
    val title = Http(request tagsouped { nodes =>
                  (nodes \\ "title").text
                })

    title should be("The Leviathan by Thomas Hobbes")
  }

  """Using the verb <\\>""" should "do the same thing as the verb tagsouped" in {
    val request = BadHtml_with_ImplicitTagSoupHandlerVerbs
    val title1 = Http(request <\\> { nodes =>
                  (nodes \\ "title").text
                })
    val title2 = Http(request tagsouped { nodes =>
                  (nodes \\ "title").text
                })

    title1 should be(title2)

  }

  "Using the verb as_tagsouped" should "return the nodes" in {
    val request = BadHtml_with_ImplicitTagSoupHandlerVerbs
    val ns = Http(request as_tagsouped)

    (ns \\ "title").text should be("The Leviathan by Thomas Hobbes")
  }
}


object BadHtml_with_ImplicitTagSoupHandlerVerbs
  extends Request(:/("oregonstate.edu") / "instruct" / "phl302" / "texts" / "hobbes" / "leviathan-c.html")
  with ImplicitTagSoupHandlerVerbs

object BadHtml
  extends Request(:/("oregonstate.edu") / "instruct" / "phl302" / "texts" / "hobbes" / "leviathan-c.html")

class BadHtmlClass(request: Request = (:/("oregonstate.edu") / "instruct" / "phl302" / "texts" / "hobbes" / "leviathan-c.html"))
  extends Request(request: Request)
