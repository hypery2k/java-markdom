package io.markdom.handler.html.w3c;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.matchers.CompareMatcher;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import io.markdom.util.XmlHelper;
import lombok.SneakyThrows;

public class XhtmlDocumentMarkdomHandlerTests {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = MarkdomExample.getExampleDocument(factory);

		Document xhtmlDocument = document.handle(new XhtmlDocumentMarkdomHandler(TestHelper.getDocumentBuilder())).asDocument();
		String xhtml = XmlHelper.asText(xhtmlDocument, false);

		assertThat(xhtml, CompareMatcher.isIdenticalTo(TestHelper.readExampleXhtml()).ignoreWhitespace());

	}

}
