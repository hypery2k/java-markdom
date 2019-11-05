package io.markdom.handler.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.markdom.model.MarkdomDocument;

public final class MarkdomSerializer extends JsonSerializer<MarkdomDocument> {

	private final boolean generateSchema;

	public MarkdomSerializer() {
		this(false);
	}

	public MarkdomSerializer(boolean generateSchema) {
		this.generateSchema = generateSchema;
	}

	@Override
	public void serialize(MarkdomDocument document, JsonGenerator generator, SerializerProvider serializers) throws IOException {
		document.handle(new JsonGeneratorMarkdomHandler(generator, generateSchema));
		generator.flush();
	}
}
