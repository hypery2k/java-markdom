package io.markdom.handler.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;

public final class MarkdomDeserializer extends JsonDeserializer<MarkdomDocument> {

	private final MarkdomFactory factory;

	public MarkdomDeserializer() {
		this(new BasicMarkdomFactory());
	}

	public MarkdomDeserializer(MarkdomFactory factory) {
		if (null == factory) {
			throw new IllegalArgumentException("The given Markdom factory is null");
		}
		this.factory = factory;
	}

	@Override
	public MarkdomDocument deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		return new JsonParserMarkdomDispatcher(parser).handle(new MarkdomDocumentMarkdomHandler(factory));
	}

}
