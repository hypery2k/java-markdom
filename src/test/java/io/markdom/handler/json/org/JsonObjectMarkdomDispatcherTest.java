package io.markdom.handler.json.org;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import io.markdom.TestHelper;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JsonObjectMarkdomDispatcherTest {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		JSONObject jsonObject = new JSONObject(new JSONTokener(TestHelper.openExampleJson()));

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new JsonObjectMarkdomDispatcher(jsonObject);
		MarkdomHandler<MarkdomDocument> handler = new MarkdomDocumentMarkdomHandler(factory);

		MarkdomDocument document = dispatcher.handle(handler);

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

}
