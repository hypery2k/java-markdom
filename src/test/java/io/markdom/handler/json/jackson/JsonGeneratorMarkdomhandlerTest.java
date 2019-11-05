package io.markdom.handler.json.jackson;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JsonGeneratorMarkdomhandlerTest {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		JsonGenerator generator = new JsonFactory().createGenerator(buffer, JsonEncoding.UTF8);

		document.handle(new JsonGeneratorMarkdomHandler(generator));
		String json = new String(buffer.toByteArray(), Charset.forName("UTF-8"));

		JSONAssert.assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}